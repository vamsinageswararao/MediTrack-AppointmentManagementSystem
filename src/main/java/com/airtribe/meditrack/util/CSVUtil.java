package com.airtribe.meditrack.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {
    
    public static List<String[]> readCSV(String filePath) throws IOException {
        List<String[]> records = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(values);
            }
        }
        
        return records;
    }

    public static void writeCSV(String filePath, List<String[]> records) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] record : records) {
                bw.write(String.join(",", record));
                bw.newLine();
            }
        }
    }

    public static void appendToCSV(String filePath, String[] record) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(String.join(",", record));
            bw.newLine();
        }
    }

    private CSVUtil() {
        // Prevent instantiation
    }
}
