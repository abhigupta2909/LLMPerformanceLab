package com.llm.dashbaord.app.DashboardAPI.Controller;

import com.llm.dashbaord.app.DashboardAPI.Models.LLMAnalytics;
import com.llm.dashbaord.app.DashboardAPI.Repo.LLMAnalyticsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {

    @Autowired
    private LLMAnalyticsRepo llmAnalyticsRepo;


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get_data")
    public List<LLMAnalytics> getAnalytics() {
        return llmAnalyticsRepo.findAll();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/save_data")
    public String saveExecutionData(@RequestBody LLMAnalytics executionData) {
        llmAnalyticsRepo.save(executionData);
        return "data saved..";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get_latest_execution_times")
    public List<LLMAnalytics> getLatestExecutionTimes() {
        return llmAnalyticsRepo.findLatestExecutionTimeForAllModels();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get_latest_cpu_usage")
    public List<LLMAnalytics> getLatestCpuUsage() {
        return llmAnalyticsRepo.findLatestCpuUsageForAllModels();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get_latest_gpu_usage")
    public List<LLMAnalytics> getLatestGpuUsage() {
        return llmAnalyticsRepo.findLatestGpuUsageForAllModels();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get_latest_energy_usage")
    public List<LLMAnalytics> getLatestEnergyUsage() {
        return llmAnalyticsRepo.findLatestEnergyUsageForAllModels();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get_model_execution/{modelName}")
    public List<LLMAnalytics> getModelExecution(@PathVariable String modelName) {
        return llmAnalyticsRepo.findByModelName(modelName);
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/delete_data/{executionId}")
    public String deleteExecutionData(@PathVariable long executionId) {
        if (llmAnalyticsRepo.existsById(executionId)) {
            llmAnalyticsRepo.deleteById(executionId);
            return "Data with executionId " + executionId + " deleted successfully.";
        } else {
            return "Data with executionId " + executionId + " not found.";
        }
    }
}
