package cac;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class DescriptiveStatistics {

    // Method to calculate standard deviation
    public static double calculateStandardDeviation(List<Double> data) {
        double mean = calculateMean(data);
        double sum = 0;
        for (double value : data) {
            sum += Math.pow(value - mean, 2);
        }
        return Math.sqrt(sum / data.size());
    }

    public static double calculateVariance(List<Double> data) {
        // Calculate mean
        double mean = calculateMean(data);

        // Calculate sum of squared differences
        double sumSquaredDiff = 0;
        for (double value : data) {
            sumSquaredDiff += Math.pow(value - mean, 2);
        }

        // Calculate variance (average of squared differences)
        double variance = sumSquaredDiff / data.size();

        return variance;
    }
    
    // Method to calculate range
    public static double calculateRange(List<Double> data) {
        // Sort the data to find the minimum and maximum values
        Collections.sort(data);
        double min = data.get(0);
        double max = data.get(data.size() - 1);

        // Calculate range (difference between maximum and minimum values)
        double range = max - min;

        return range;
    }

    public static void main(String[] args) {
        try {
            // Read the CSV file
            FileReader file = new FileReader("cleaned_data.csv");
            BufferedReader br = new BufferedReader(file);

            // Variables for data processing
            String line;
            boolean headerSkipped = false; //skip the header
            List<Double> column4Data = new ArrayList<>();

            // Read each line of the CSV file
            while ((line = br.readLine()) != null) {
                // Skip the header line
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                // Split the line by comma to get individual values
                String[] values = line.split(",");

                // Process data for column 4
                try {
                    double value = Double.parseDouble(values[3].trim()); // Assuming column 4 is index 3
                    column4Data.add(value);
                } catch (NumberFormatException e) {
                    // Handle non-numeric values here (optional)
                    //System.out.println("Non-numeric value detected in column 4: " + values[3]);
                }
            }

            // Calculate and print the mean, median, and mode of column 4
            double meanColumn4 = calculateMean(column4Data);
            double medianColumn4 = calculateMedian(column4Data);
            double modeColumn4 = calculateMode(column4Data);
            double sdColumn4 = calculateStandardDeviation(column4Data);
            double varColumn4 = calculateVariance(column4Data);
            double rangeColumn4 = calculateRange(column4Data);
            
            System.out.println("DESCRIPTIVE ANALYSIS");
            System.out.println("---------------------------");
            System.out.println("CENTRAL TENDENCY...");
            System.out.printf("***************************** \n");
            System.out.println("Mean of CO2 emission : " + meanColumn4);
            System.out.println("Median of CO2 emission: " + medianColumn4);
            System.out.println("Mode of CO2 emission: " + modeColumn4);
            System.out.println("Measures of Variability:...");
            System.out.printf("***************************** \n");
            System.out.println("Standard Deviation: ...");
            System.out.println("sd of CO2 emission: " + sdColumn4);
            System.out.println("variance of CO2 emission: " + varColumn4);
            System.out.println("range of CO2 emission: " + rangeColumn4);

           

        //  Infer data types and print the result
        //  String[] columnTypes = new String[columnData.get(0).length];
        //  Arrays.fill(columnTypes, "Unknown");
        //  inferDataTypes(columnData, columnTypes);

            // Close the file reader
            br.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    // Method to calculate mean
    public static double calculateMean(List<Double> data) {
        double sum = 0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.size();
    }
    
    // Method to calculate median
    public static double calculateMedian(List<Double> data) {
        Collections.sort(data);
        int n = data.size();
        if (n % 2 == 0) {
            return (data.get(n / 2 - 1) + data.get(n / 2)) / 2.0;
        } else {
            return data.get(n / 2);
        }
    }

    // Method to calculate mode
    public static double calculateMode(List<Double> data) {
        Map<Double, Integer> frequencyMap = new HashMap<>();
        for (double value : data) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }
        double mode = 0;
        int maxFrequency = 0;
        for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                mode = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }
        return mode;
    }

    // Method to infer data types based on the first row
    public static void inferDataTypes(List<String[]> columnData, String[] columnTypes) {
        for (int i = 0; i < columnTypes.length; i++) {
            String columnType = columnTypes[i].trim(); // Clean up whitespace
            boolean isNumeric = true;
            for (String[] rowData : columnData) {
                try {
                    // Try parsing each value in the column as a double
                    Double.parseDouble(rowData[i].trim());
                } catch (NumberFormatException e) {
                    // If any value cannot be parsed as a double, it's not numeric
                    isNumeric = false;
                    break;
                }
            }
            if (isNumeric) {
                System.out.println("Column " + i + " is numeric.");
            } else {
                System.out.println("Column " + i + " is non-numeric (likely string).");
            }
        }
    }
}
