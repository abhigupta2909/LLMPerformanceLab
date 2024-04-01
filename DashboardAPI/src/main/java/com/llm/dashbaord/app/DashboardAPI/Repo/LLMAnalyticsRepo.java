package com.llm.dashbaord.app.DashboardAPI.Repo;

import com.llm.dashbaord.app.DashboardAPI.Models.LLMAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface LLMAnalyticsRepo extends JpaRepository<LLMAnalytics, Long> {
    List<LLMAnalytics> findByModelName(String modelName);

    @Query("SELECT a FROM LLMAnalytics a WHERE a.executionId IN " +
            "(SELECT MAX(b.executionId) FROM LLMAnalytics b GROUP BY b.modelName) " +
            "AND a.executionTime_avg_plus_sd <> 0")
    List<LLMAnalytics> findLatestExecutionTimeForAllModels();

    @Query("SELECT a FROM LLMAnalytics a WHERE a.executionId IN " +
            "(SELECT MAX(b.executionId) FROM LLMAnalytics b GROUP BY b.modelName) " +
            "AND a.cpuUsage_avg_plus_sd <> 0")
    List<LLMAnalytics> findLatestCpuUsageForAllModels();

    @Query("SELECT a FROM LLMAnalytics a WHERE a.executionId IN " +
            "(SELECT MAX(b.executionId) FROM LLMAnalytics b GROUP BY b.modelName) " +
            "AND a.gpuUsage_avg_plus_sd <> 0")
    List<LLMAnalytics> findLatestGpuUsageForAllModels();

    @Query("SELECT a FROM LLMAnalytics a WHERE a.executionId IN " +
            "(SELECT MAX(b.executionId) FROM LLMAnalytics b GROUP BY b.modelName) " +
            "AND a.energyUsage_avg_sd <> 0")
    List<LLMAnalytics> findLatestEnergyUsageForAllModels();
}
