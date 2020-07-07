import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;

public class Percolation {
    private int[][] matrix;
    private boolean[][] isOpen;
    private WeightedQuickUnionUF weightedquickUnionUF;
    private int numberOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        matrix = new int[n][n];
        isOpen = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                isOpen[i][j] = false;
                matrix[i][j] = i * n + (j + 1);
            }
        }
        weightedquickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        numberOfOpenSites = 0;
    };

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

    };

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return isOpen[row][col];
    };

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return true;
    };

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    };

    // does the system percolate?
    public boolean percolates() {
        return true;
    };

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(4);
//        System.out.println(percolation.isOpen(1, 1));
    };
}
