package com.example.JobData.controller;

import com.example.JobData.model.DataPoint;
import com.example.JobData.model.JobData;
import com.example.JobData.service.JobDataService;
import com.opencsv.exceptions.CsvException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class JobDataController {

    private final JobDataService jobDataService;

    public JobDataController(JobDataService jobDataService) {
        this.jobDataService = jobDataService;
    }

    // Endpoint for fetching job data
    @GetMapping("/jobdata")
    public List<JobData> getJobData() throws IOException, CsvException {
        return jobDataService.processCsv();
    }

    // Endpoint for fetching job titles by year
    @GetMapping("/job-titles")
    public Map<String, Integer> getJobTitlesByYear(@RequestParam int year) throws IOException, CsvException {
        return jobDataService.getJobTitlesByYear(year);
    }

    // Endpoint for fetching data trend
    @GetMapping("/data-trend")
    public List<DataPoint> getDataTrend() throws IOException, CsvException {
        return jobDataService.getDataTrend();
    }
    // If you need a method for rendering a Thymeleaf template:
    @GetMapping("/line-graph")
    public ModelAndView getLineGraph() throws IOException, CsvException {
        List<DataPoint> dataTrend = jobDataService.getDataTrend();
        ModelAndView modelAndView = new ModelAndView("line-graph");
        modelAndView.addObject("dataTrend", dataTrend);
        return modelAndView;
    }
}

