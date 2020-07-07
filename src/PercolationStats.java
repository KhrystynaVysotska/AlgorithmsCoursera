import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_CONSTANT = 1.96;
    private final int numberOfTrials;
    private final int sizeOfGrid;
    private Percolation percolation;
    private double mean;
    private double stddev;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Size of grid and amount of trials must be positive values");
        }
        this.sizeOfGrid = n;
        this.numberOfTrials = trials;
    }

    public double mean() {
        int numberOfAllSites = sizeOfGrid * sizeOfGrid;
        int row;
        int col;
        double[] generalNumberOfThresholds = new double[numberOfTrials];
        for (int i = 0; i < numberOfTrials; i++) {
            this.percolation = new Percolation(sizeOfGrid);
            while (!percolation.percolates()) {
                row = StdRandom.uniform(1, sizeOfGrid + 1);
                col = StdRandom.uniform(1, sizeOfGrid + 1);
                percolation.open(row, col);
            }
            generalNumberOfThresholds[i] = (double) percolation.numberOfOpenSites() / numberOfAllSites;
        }
        this.mean = StdStats.mean(generalNumberOfThresholds);
        return this.mean;
    }

    public double stddev() {
        int numberOfAllSites = sizeOfGrid * sizeOfGrid;
        int row;
        int col;
        double[] valuesToCalculateStddev = new double[numberOfTrials];
        for (int i = 0; i < numberOfTrials; i++) {
            this.percolation = new Percolation(sizeOfGrid);
            while (!percolation.percolates()) {
                row = StdRandom.uniform(1, sizeOfGrid + 1);
                col = StdRandom.uniform(1, sizeOfGrid + 1);
                percolation.open(row, col);
            }
            valuesToCalculateStddev[i] = (double) percolation.numberOfOpenSites() / numberOfAllSites - this.mean;
        }
        this.stddev = StdStats.stddev(valuesToCalculateStddev);
        return this.stddev;
    }

    public double confidenceLo() {
        return this.mean - ((CONFIDENCE_CONSTANT * this.stddev) / Math.sqrt(numberOfTrials));
    }

    public double confidenceHi() {
        return this.mean + ((CONFIDENCE_CONSTANT * this.stddev) / Math.sqrt(numberOfTrials));
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        int trials = StdIn.readInt();
        PercolationStats percollationStats = new PercolationStats(n, trials);
        double mean = percollationStats.mean();
        double stddev = percollationStats.stddev();
        String confidenceInterval = "95% confidence interval         = [" + percollationStats.confidenceLo() + ", "
                + percollationStats.confidenceHi() + "]";
        String output = "mean                            = " + mean + "\n" + "stddev                          = "
                + stddev + "\n" + confidenceInterval;
        System.out.println(output);
    }

}