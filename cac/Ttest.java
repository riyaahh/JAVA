package cac;

import org.apache.commons.math3.stat.inference.TTest;

public class Ttest {

    // Function to perform the t-test
    public static void performTTest(double[] co2Albania, double[] co2India) {
        // Perform two-sample t-test
        TTest tTest = new TTest();
        double pValue = tTest.tTest(co2Albania, co2India);

        // Print results
        System.out.println("T TEST");
        System.out.println("-------------------");
        System.out.println("Null Hypothesis(H0): The mean CO2 emissions for Country Albania are significantly different from the mean CO2 emissions for Country India");
        System.out.println("Alternate Hypothesis(H0)H1: The mean CO2 emissions for Country Albania are not significantly different from the mean CO2 emissions for Country India");
        System.out.println("-------------------");
        System.out.println("P-value: " + pValue);
        System.out.println("alpha: 0.05");
        System.out.println(" ");

        // Check significance level
        double alpha = 0.05;
        if (pValue < alpha) {
            System.out.println("Here pValue < alpha so");
            System.out.println("Reject the null hypothesis: The mean CO2 emissions for Country Albania are significantly different from the mean CO2 emissions for Country India.");
        } else {
            System.out.println("Here pValue > alpha so");
            System.out.println("Fail to reject the null hypothesis: There is no significant difference in the mean CO2 emissions between Country Albania and Country India.");
        }
    }

    public static void main(String[] args) {
        // Sample CO2 emissions data for Country A and Country B
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

        // Perform the t-test using the function
        performTTest(co2Albania, co2India);
    }
}
