from flask import Flask, jsonify
from flask_cors import CORS
import threading
import psutil
import re
import time

app = Flask(__name__)
CORS(app, resources={r"/api/*": {"origins": "http://localhost:3000"}})

monitoring_data = []
is_monitoring = False

def find_process_by_pattern(pattern):
    processes = []
    for process in psutil.process_iter(['pid', 'name']):
        if re.search(pattern, process.info['name']):
            processes.append(process)
    return processes

def monitor_process(pattern, interval):
    global monitoring_data, is_monitoring
    monitoring_data = []
    is_monitoring = True

    while is_monitoring:
        found_processes = find_process_by_pattern(pattern)
        for process in found_processes:
            pid = process.pid
            name = process.info['name']
            cpu_usage = process.cpu_percent(interval=interval)
            rss = process.memory_info().rss
            print({'name': name, 'pid': pid, 'cpu': cpu_usage, 'rss': rss})
            monitoring_data.append({'name': name, 'pid': pid, 'cpu': cpu_usage, 'rss': rss})
        time.sleep(interval)

@app.route('/api/start_monitoring', methods=['GET'])
def start_monitoring():
    pattern = "ollama-runner"
    monitoring_thread = threading.Thread(target=monitor_process, args=(pattern, 0.1))
    monitoring_thread.start()
    return jsonify({"status": "Monitoring started"})

@app.route('/api/stop_monitoring', methods=['GET'])
def stop_monitoring():
    global is_monitoring
    is_monitoring = False
    if len(monitoring_data) > 0:
        mean_cpu = sum(item['cpu'] for item in monitoring_data) / len(monitoring_data)
        mean_rss_bytes = sum(item['rss'] for item in monitoring_data) / len(monitoring_data)
        mean_rss_mb = mean_rss_bytes / (1024 * 1024)  # Convert bytes to MB
        print("####### ", mean_cpu, mean_rss_mb)
        return jsonify({"status": "Monitoring stopped", "mean_cpu": mean_cpu, "mean_rss_mb": mean_rss_mb})
    else:
        return jsonify({"status": "No data collected"})

if __name__ == '__main__':
    app.run(debug=True)
