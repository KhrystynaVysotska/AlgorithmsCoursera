import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int numberOfTrials;
    private int sizeOfGrid;
    Percolation percolation;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Size of grid and amount of trials must be positive values");
        }
        this.sizeOfGrid = n;
        this.numberOfTrials = trials;
    }

    // sample mean of percolation threshold
    public double mean() {
        int numberOfAllSites = sizeOfGrid * sizeOfGrid;
        int row;
        int col;
        double generalNumberOfThresholds = 0;
        for (int i = 0; i < numberOfTrials; i++) {
            this.percolation = new Percolation(sizeOfGrid);
            while (!percolation.percolates()) {
                row = StdRandom.uniform(sizeOfGrid);
                col = StdRandom.uniform(sizeOfGrid);
                percolation.open(row, col);
            }
            generalNumberOfThresholds += (double) percolation.numberOfOpenSites() / numberOfAllSites;
        }
        return generalNumberOfThresholds / numberOfTrials;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        int numberOfAllSites = sizeOfGrid * sizeOfGrid;
        double mean = mean();
        int row;
        int col;
        double[] valuesToCalculateStddev = new double[numberOfTrials];
        for (int i = 0; i < numberOfTrials; i++) {
            this.percolation = new Percolation(sizeOfGrid);
            while (!percolation.percolates()) {
                row = StdRandom.uniform(sizeOfGrid);
                col = StdRandom.uniform(sizeOfGrid);
                percolation.open(row, col);
            }
            valuesToCalculateStddev[i] = (double) percolation.numberOfOpenSites() / numberOfAllSites - mean;
        }
        return StdStats.stddev(valuesToCalculateStddev);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96*stddev())/Math.sqrt(numberOfTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96*stddev())/Math.sqrt(numberOfTrials));
    }

    // test client (see below)
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