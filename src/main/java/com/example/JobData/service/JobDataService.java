package com.example.JobData.service;

import com.example.JobData.model.DataPoint;
import com.example.JobData.model.JobData;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobDataService {

    @Value("${jobData.filepath}")
    private String filePath;
   // private com.example.JobData.repository.jobDataRepository jobDataRepository;

    public List<JobData> processCsv() throws IOException, CsvException {
        Map<Integer, List<Double>> yearToSalariesMap = new HashMap<>();
        Map<Integer, Integer> yearToJobCountMap = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                if (row[0].equals("work_year")) continue; // Skip header
                int year = Integer.parseInt(row[0]);
                double salaryInUsd = Double.parseDouble(row[6]);

                yearToSalariesMap.computeIfAbsent(year, k -> new ArrayList<>()).add(salaryInUsd);
                yearToJobCountMap.put(year, yearToJobCountMap.getOrDefault(year, 0) + 1);
            }
        }

        return yearToSalariesMap.entrySet().stream()
                .map(entry -> new JobData(
                        entry.getKey(),
                        yearToJobCountMap.get(entry.getKey()),
                        entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0)
                ))
                .sorted(Comparator.comparingInt(JobData::getYear))
                .collect(Collectors.toList());
    }

    public Map<String, Integer> getJobTitlesByYear(int year) throws IOException, CsvException {
        Map<String, Integer> jobTitleCountMap = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                if (row[0].equals("work_year")) continue; // Skip header
                int rowYear = Integer.parseInt(row[0]);
                if (rowYear != year) continue;

                String jobTitle = row[3];
                jobTitleCountMap.put(jobTitle, jobTitleCountMap.getOrDefault(jobTitle, 0) + 1);
            }
        }

        return jobTitleCountMap;
    }

    public List<DataPoint> getDataTrend() throws IOException, CsvException {
        Map<Integer, List<Double>> yearToSalariesMap = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                if (row[0].equals("work_year")) continue; // Skip header
                int year = Integer.parseInt(row[0]);
                double salaryInUsd = Double.parseDouble(row[6]);

                yearToSalariesMap.computeIfAbsent(year, k -> new ArrayList<>()).add(salaryInUsd);
            }
        }

        return yearToSalariesMap.entrySet().stream()
                .map(entry -> new DataPoint(
                        entry.getKey(),
                        entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0)
                ))
                .sorted(Comparator.comparingInt(DataPoint::getYear))
                .collect(Collectors.toList());
    }
}
