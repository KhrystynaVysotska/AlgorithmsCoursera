import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int[][] matrix;
    private boolean[][] isOpen;
    private final WeightedQuickUnionUF weightedquickUnionUF;
    private int numberOfOpenSites;

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

    public void open(int row, int col) {
        validate(row, col);
        if (isOpen[row - 1][col - 1]) {
            return;
        } else {
            isOpen[row - 1][col - 1] = true;
            numberOfOpenSites++;
            if (matrix.length == 1) {
                weightedquickUnionUF.union(0, matrix.length * matrix.length + 1);
            }
            if (row - 1 == 0) {
                weightedquickUnionUF.union(0, matrix[row - 1][col - 1]);
            } else if (row - 1 == matrix.length - 1) {
                weightedquickUnionUF.union(matrix[row - 1][col - 1], (matrix.length * matrix.length) + 1);
            }
            if (col - 1 != 0 && isOpen(row, col - 1)) {
                weightedquickUnionUF.union(matrix[row - 1][col - 2], matrix[row - 1][col - 1]);
            }
            if (col - 1 != matrix.length - 1 && isOpen(row, col + 1)) {
                weightedquickUnionUF.union(matrix[row - 1][col], matrix[row - 1][col - 1]);
            }
            if (row - 1 != 0 && isOpen(row - 1, col)) {
                weightedquickUnionUF.union(matrix[row - 2][col - 1], matrix[row - 1][col - 1]);
            }
            if (row - 1 != matrix.length - 1 && isOpen(row + 1, col)) {
                weightedquickUnionUF.union(matrix[row][col - 1], matrix[row - 1][col - 1]);
            }
        }
    };

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return isOpen[row - 1][col - 1];
    };

    public boolean isFull(int row, int col) {
        validate(row, col);
        return weightedquickUnionUF.find(0) == weightedquickUnionUF.find(matrix[row - 1][col - 1]);
    };

    private void validate(int row, int col) {
        if (row - 1 < 0 || row - 1 >= matrix.length) {
            throw new IllegalArgumentException("row index " + row + " is not between 1 and " + matrix.length);
        } else if (col - 1 < 0 || col - 1 >= matrix.length) {
            throw new IllegalArgumentException("coloumn index " + col + " is not between 1 and " + matrix.length);
        }
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    };

    public boolean percolates() {
        return weightedquickUnionUF.find(0) == weightedquickUnionUF.find((matrix.length * matrix.length) + 1);
    };
}