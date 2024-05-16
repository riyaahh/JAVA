package cac;

import org.apache.commons.math3.distribution.NormalDistribution;

public class ZTest {

    public static void main(String[] args) {
        // Sample CO2 emissions data for Country Afganistan in initial years and most recent years
        double[] initialYearsData = {1750, 1751, 1752, 1753, 1754, 1755, 1756, 1757, 1758, 1759, 1760, 1761, 1762, 1763, 1764, 1765, 1766, 1767, 1768, 1769, 1770, 1771, 1772, 1773, 1774, 1775, 1776, 1777, 1778, 1779, 1780};
        double[] recentYearsData = {1994, 1995, 1996, 1997, 1998, 1999, 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020};

        // Call the performZTestAndPrintResults method
        performZTestAndPrintResults(initialYearsData, recentYearsData, 0.05);
    }

    // Public function to perform Z-Test and print results
    public static void performZTestAndPrintResults(double[] initialYearsData, double[] recentYearsData, double alpha) {
        // Perform z-test
        double pValue = performZTest(initialYearsData, recentYearsData);

        // Print results
        System.out.println("--------------");
        System.out.println("ZTEST");
        System.out.println("--------------");
        System.out.println("Null Hypothesis (H0): The mean CO2 emissions for Country Afganistan in the initial years are equal to the mean CO2 emissions for Country Afganistan in the most recent years");
        System.out.println("Alternative Hypothesis (H1): The mean CO2 emissions for Country Afganistan in the initial years are not equal to the mean CO2 emissions for Country Afganistan in the most recent years.");
        System.out.println(" ");
        System.out.println("P-value: " + pValue);
        System.out.println("alpha: " + alpha);
        System.out.println(" ");

        // Check significance level
        if (pValue < alpha) {
            System.out.println(" Here pValue < alpha so,");
            System.out.println(" ");
            System.out.println("Reject the null hypothesis: There is a significant difference in CO2 emissions for Country Afganistan between the initial years and the most recent years.");
        } else {
            System.out.println("Here pValue > alpha so ");
            System.out.println(" ");
            System.out.println("Fail to reject the null hypothesis: There is no significant difference in CO2 emissions for Country Afganistan between the initial years and the most recent years.");
        }
    }

    // Method to perform z-test and calculate p-value
    public static double performZTest(double[] initialYearsData, double[] recentYearsData) {
        // Calculate mean and standard deviation for initial years
        double meanInitial = calculateMean(initialYearsData);
        double sdInitial = calculateStandardDeviation(initialYearsData);

        // Calculate mean and standard deviation for recent years
        double meanRecent = calculateMean(recentYearsData);
        double sdRecent = calculateStandardDeviation(recentYearsData);

        // Sample sizes
        int nInitial = initialYearsData.length;
        int nRecent = recentYearsData.length;

        // Calculate z-score
        double zScore = calculateZScore(meanInitial, meanRecent, sdInitial, sdRecent, nInitial, nRecent);

        // Calculate p-value
        return calculatePValue(zScore);
    }

    // Method to calculate mean
    public static double calculateMean(double[] data) {
        double sum = 0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.length;
    }

    // Method to calculate standard deviation
    public static double calculateStandardDeviation(double[] data) {
        double mean = calculateMean(data);
        double sum = 0;
        for (double value : data) {
            sum += Math.pow(value - mean, 2);
        }
        return Math.sqrt(sum / (data.length - 1));
    }

    // Method to calculate z-score
    public static double calculateZScore(double mean1, double mean2, double sd1, double sd2, int n1, int n2) {
        return (mean1 - mean2) / Math.sqrt((Math.pow(sd1, 2) / n1) + (Math.pow(sd2, 2) / n2));
    }

    // Method to calculate p-value
    public static double calculatePValue(double zScore) {
        NormalDistribution standardNormal = new NormalDistribution();
        return 2 * (1 - standardNormal.cumulativeProbability(Math.abs(zScore)));
    }
}
