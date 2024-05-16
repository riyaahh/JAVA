package cac;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class LoadingCleaning {

    public static Object[] cleanData(String filePath) {
        try {
            // Data loading and cleaning
            File file = new File(filePath);
            Scanner fileReader = new Scanner(file);

            // Assuming the first line contains column headers
            String[] headers = fileReader.nextLine().split(",");
            int numColumns = headers.length;
            int[] nullCounts = new int[numColumns];

            // Count null values in each column
            while (fileReader.hasNextLine()) {
                String readline = fileReader.nextLine();
                String[] line = readline.split(",");

                for (int i = 0; i < numColumns; i++) {
                    // Trim the element to handle cases like "  ", which are considered empty
                    if (line.length > i && (line[i] == null || line[i].trim().isEmpty())) {
                        nullCounts[i]++;
                    }
                }
            }

            fileReader.close();

            // Data cleaning and writing to a new file
            File inputFile = new File(filePath);
            File outputFile = new File("cleaned_" + file.getName());
            fileReader = new Scanner(inputFile);
            FileWriter fileWriter = new FileWriter(outputFile);

            // Assuming the first line contains column headers
            String headersLine = fileReader.nextLine();
            fileWriter.write(headersLine + "\n");

            while (fileReader.hasNextLine()) {
                String readLine = fileReader.nextLine();
                String[] line = readLine.split(",");

                boolean hasNull = false;
                for (String element : line) {
                    if (element == null || element.trim().isEmpty()) {
                        hasNull = true;
                        break;
                    }
                }

                if (!hasNull) {
                    fileWriter.write(readLine + "\n");
                }
            }

            fileReader.close();
            fileWriter.close();

            Object[] result = new Object[2];
            result[0] = outputFile.getPath();
            result[1] = nullCounts;
            return result;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Object[] cleaningResult = cleanData("data.csv");
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
                    System.out.println("Column " + headers[i] + ": " + nullCountsAfterCleaning[i] + " null values");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
