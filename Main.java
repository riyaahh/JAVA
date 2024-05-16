import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import cac.DescriptiveStatistics;
import cac.LoadingCleaning;
import cac.Ttest;
import cac.CorrelationCalculator;
import java.util.Scanner;
import cac.ZTest;

public class Main {
    public static void main(String[] args) {

        double[] initialYearsData = { 1750, 1751, 1752, 1753, 1754, 1755, 1756, 1757, 1758, 1759, 1760, 1761, 1762,
                1763, 1764, 1765, 1766, 1767, 1768, 1769, 1770, 1771, 1772, 1773, 1774, 1775, 1776, 1777, 1778, 1779,
                1780 };
        double[] recentYearsData = { 1994, 1995, 1996, 1997, 1998, 1999, 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007,
                2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020 };
        double alpha = 0.05;

        double[] co2Albania = {
            0, 0, 0, 0, 0, 0, 7328, 14656, 32976, 161216, 458000, 806080, 1238432,
            1930928, 2557472, 3301264, 3762928, 3916816, 4037728, 4521376, 5448368,
            6151856, 7166784, 7463568, 7866608, 8240336, 8654368, 9156336, 9819520,
            10658576
        };
        double[] co2India = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 395050, 1032441, 1676472,
            2174434, 2725512, 3336346, 3913982, 4481659, 5122370, 5122370, 5122370,
            5122370, 5122370, 5122370, 5122370, 5122370, 5122370, 5122370, 5122370,
            6891795
        };
        // File path
        String filePath = "cleaned_data.csv";

        // List to store data
        List<List<String>> data = new ArrayList<>();

        // Read data from CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean skipHeader = true; // Flag to skip the first line (headers)
            while ((line = br.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue; // Skip the first line
                }
                String[] fields = line.split(","); // Assuming CSV fields are separated by commas
                List<String> row = new ArrayList<>();
                for (String field : fields) {
                    row.add(field);
                }
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return; // Exit if there's an error reading the file
        }

        // Convert data to List<Double>
        List<Double> doubleData = convertData(data);

        // Perform analysis on the data
        if (!doubleData.isEmpty()) {
            boolean exit = false;
            Scanner scanner = new Scanner(System.in);
            DescriptiveStatistics descriptiveStats = new DescriptiveStatistics();
            ZTest testz = new ZTest();

            while (!exit) {
                System.out.println("Choose what you want to see...!!!!!");
                System.out.println("1. view cleaned data");
                System.out.println("2. Perform Descriptive Statistics");
                System.out.println("3. Perform ZTest");
                System.out.println("4. Perform TTest");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {

                    case 1:
                        Object[] cleaningResult = LoadingCleaning.cleanData("data.csv");
                        if (cleaningResult != null) {
                            String cleanedFilePath = (String) cleaningResult[0];
                            int[] nullCountsAfterCleaning = (int[]) cleaningResult[1];
                            try {
                                File cleanedFile = new File(cleanedFilePath);
                                Scanner fileReader = new Scanner(cleanedFile);
                                System.out.println("Cleaned Data:");
                                while (fileReader.hasNextLine()) {
                                    System.out.println(fileReader.nextLine());
                                }
                                fileReader.close();

                                System.out.println("Null value count after cleaning:");
                                String[] headers = new Scanner(new File("data.csv")).nextLine().split(",");
                                for (int i = 0; i < headers.length; i++) {
                                    System.out.println("Column " + headers[i] + ": " + nullCountsAfterCleaning[i]
                                            + " null values");
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case 2:
                        System.out.println("The statistics of the variable CO2 emission");
                        double standardDeviation = descriptiveStats.calculateStandardDeviation(doubleData);
                        System.out.println("Standard Deviation: " + standardDeviation);
                        double variance = descriptiveStats.calculateVariance(doubleData);
                        System.out.println("Variance: " + variance);
                        double range = descriptiveStats.calculateRange(doubleData);
                        System.out.println("Range: " + range);
                        double mean = descriptiveStats.calculateMean(doubleData);
                        System.out.println("Meane: " + mean);
                        double median = descriptiveStats.calculateMedian(doubleData);
                        System.out.println("Median: " + median);
                        double mode = descriptiveStats.calculateMode(doubleData);
                        System.out.println("Mode: " + mode);

                        break;
                    case 3:
                        ZTest.performZTestAndPrintResults(initialYearsData, recentYearsData, alpha);

                        break;
                    case 4:
                        Ttest.performTTest(co2Albania, co2India);
                        break;
                    case 5:
                    System.out.println("Thank youuuuu!!!!!!!!!!");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
            scanner.close();
        } else {
            System.out.println("No data found in the CSV file.");
        }
    }

    // Method to convert List<List<String>> to List<Double>
    private static List<Double> convertData(List<List<String>> data) {
        List<Double> doubleData = new ArrayList<>();
        for (List<String> row : data) {
            for (String value : row) {
                try {
                    doubleData.add(Double.parseDouble(value));
                } catch (NumberFormatException e) {
                    // Handle non-numeric values here, if necessary
                    // For example, you could log a warning or skip the value
                    // System.err.println("Non-numeric value found: " + value);
                }
            }
        }
        return doubleData;
    }
}
