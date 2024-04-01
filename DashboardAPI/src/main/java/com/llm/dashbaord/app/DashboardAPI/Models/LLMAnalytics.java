package com.llm.dashbaord.app.DashboardAPI.Models;

import jakarta.persistence.*;

@Entity
public class LLMAnalytics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long executionId;
    @Column
    private String modelName;

    @Column
    private long executionTime_avg_plus_sd;

    @Column
    private float cpuUsage_avg_plus_sd;
    @Column
    private float gpuUsage_avg_plus_sd;
    @Column
    private float energyUsage_avg_sd;

    public long getExecutionId() {
        return executionId;
    }

    public String getModelName() {
        return modelName;
    }

    public float getCpuUsage_avg_plus_sd() {
        return cpuUsage_avg_plus_sd;
    }

    public float getGpuUsage_avg_plus_sd() {
        return gpuUsage_avg_plus_sd;
    }

    public float getEnergyUsage_avg_sd() {
        return energyUsage_avg_sd;
    }

    public long getExecutionTime_avg_plus_sd() {
        return executionTime_avg_plus_sd;
    }

}
