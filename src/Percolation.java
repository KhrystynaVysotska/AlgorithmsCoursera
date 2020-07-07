import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] matrix;
    private boolean[][] isOpen;
    private WeightedQuickUnionUF weightedquickUnionUF;
    private int numberOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("size of grid must be a positive value");
        }
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
        validate(row, col);
        if (isOpen[row][col]) {
            return;
        } else {
            isOpen[row][col] = true;
            numberOfOpenSites++;
            if (row == 0) {
                weightedquickUnionUF.union(0, matrix[row][col]);
            } else if (row == matrix.length - 1) {
                weightedquickUnionUF.union(matrix[row][col], (matrix.length * matrix.length) + 1);
            }
            if (col != 0 && isOpen(row, col - 1)) {
                weightedquickUnionUF.union(matrix[row][col - 1], matrix[row][col]);
            }
            if (col != matrix.length - 1 && isOpen(row, col + 1)) {
                weightedquickUnionUF.union(matrix[row][col + 1], matrix[row][col]);
            }
            if (row != 0 && isOpen(row - 1, col)) {
                weightedquickUnionUF.union(matrix[row - 1][col], matrix[row][col]);
            }
            if (row != matrix.length - 1 && isOpen(row + 1, col)) {
                weightedquickUnionUF.union(matrix[row + 1][col], matrix[row][col]);
            }
        }
    };

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return isOpen[row][col];
    };

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return weightedquickUnionUF.find(0) == weightedquickUnionUF.find(matrix[row][col]);
    };
    
    private void validate(int row, int col) {
        if(row < 0 || row >= matrix.length) {
            throw new IllegalArgumentException("row index " + row + " is not between 0 and " + (matrix.length-1));
        } else if(col < 0 || col >= matrix.length) {
            throw new IllegalArgumentException("coloumn index " + col + " is not between 0 and " + (matrix.length-1));
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    };

    // does the system percolate?
    public boolean percolates() {
        return weightedquickUnionUF.find(0) == weightedquickUnionUF.find((matrix.length * matrix.length) + 1);
    };

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(4);
        percolation.open(0, 2);
        percolation.open(1, 2);
        percolation.open(1, 3);
        percolation.open(2, 3);
        percolation.open(3, 3);
        System.out.println(percolation.percolates());
        System.out.println(percolation.numberOfOpenSites());
    };
}
