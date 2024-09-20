package com.example.JobData.model;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobData {
    private int year;
    private int totalJobs;
    private double averageSalary;
}