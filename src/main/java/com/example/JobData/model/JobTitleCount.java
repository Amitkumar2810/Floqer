package com.example.JobData.model;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobTitleCount {
    private String jobTitle;
    private int count;
}