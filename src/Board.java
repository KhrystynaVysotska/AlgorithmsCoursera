import java.util.Iterator;
import java.util.NoSuchElementException;

public final class Board {
    private final int[][] board;
    private final int dimension;
    private int rowOfBlankTile;
    private int coloumnOfBlankTile;

    public Board(int[][] tiles) {
        dimension = tiles.length;
        board = new int[dimension][dimension];
        for (int row = 0; row < dimension; row++) {
            for (int coloumn = 0; coloumn < dimension; coloumn++) {
                board[row][coloumn] = tiles[row][coloumn];
                if (board[row][coloumn] == 0) {
                    rowOfBlankTile = row;
                    coloumnOfBlankTile = coloumn;
                }
            }
        }
    }

    public String toString() {
        StringBuilder lines = new StringBuilder();
        for (int row = 0; row < dimension; row++) {
            for (int coloumn = 0; coloumn < dimension; coloumn++) {
                lines.append(" " + board[row][coloumn] + " ");
            }
            lines.append("\n");
        }
        return dimension + "\n" + lines;
    }

    public int dimension() {
        return dimension;
    }

    public int hamming() {
        int numberOfTilesInWrongPosition = 0;
        for (int row = 0; row < dimension; row++) {
            for (int coloumn = 0; coloumn < dimension; coloumn++) {
                if (row == dimension - 1 && coloumn == dimension - 1) {
                    break;
                }
                if (board[row][coloumn] != row * dimension + (coloumn + 1)) {
                    numberOfTilesInWrongPosition++;
                }
            }
        }
        return numberOfTilesInWrongPosition;
    }

    public int manhattan() {
        int manhattanNumber = 0;
        for (int row = 0; row < dimension; row++) {
            for (int coloumn = 0; coloumn < dimension; coloumn++) {
                if (board[row][coloumn] != row * dimension + (coloumn + 1)) {
                    if (board[row][coloumn] == 0) {
                        continue;
                    }
                    int n = dimension;
                    while (board[row][coloumn] > n) {
                        n += dimension;
                    }
                    int actualRow = n / dimension - 1;
                    int actualColoumn = board[row][coloumn] - actualRow * dimension - 1;
                    manhattanNumber += Math.abs(actualRow - row) + Math.abs(actualColoumn - coloumn);
                }
            }
        }
        return manhattanNumber;
    }

    public boolean isGoal() {
        for (int row = 0; row < dimension; row++) {
            for (int coloumn = 0; coloumn < dimension; coloumn++) {
                if (row == dimension - 1 && coloumn == dimension - 1) {
                    break;
                }
                if (board[row][coloumn] != row * dimension + (coloumn + 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (!(y instanceof Board)) {
            return false;
        }
        Board that = (Board) y;
        if (that.dimension() != this.dimension()) {
            return false;
        }
        for (int row = 0; row < dimension; row++) {
            for (int coloumn = 0; coloumn < dimension; coloumn++) {
                if (this.board[row][coloumn] != that.board[row][coloumn]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void reset(int[][] copyBoard) {
        for (int row = 0; row < dimension; row++) {
            for (int coloumn = 0; coloumn < dimension; coloumn++) {
                copyBoard[row][coloumn] = board[row][coloumn];
            }
        }
    }

    private void exchange(int[][] boardToExchange, int firstRow, int firstColoumn, int secondRow, int secondColoumn) {
        int swap = boardToExchange[firstRow][firstColoumn];
        boardToExchange[firstRow][firstColoumn] = boardToExchange[secondRow][secondColoumn];
        boardToExchange[secondRow][secondColoumn] = swap;
    }

    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return new BoardIterator();
            }

            class BoardIterator implements Iterator<Board> {
                private final int[][] copyBoard = new int[dimension][dimension];
                private final int row = rowOfBlankTile;
                private final int coloumn = coloumnOfBlankTile;
                private int amountOfNeighbours = 0;
                private Board[] neighbourBoards;

                public BoardIterator() {
                    if ((rowOfBlankTile == 0 || rowOfBlankTile == dimension - 1)
                            && (coloumnOfBlankTile == 0 || coloumnOfBlankTile == dimension - 1)) {
                        amountOfNeighbours = 2;
                    } else if (((rowOfBlankTile == 0 || rowOfBlankTile == dimension - 1)
                            && (coloumnOfBlankTile != 0 && coloumnOfBlankTile != dimension - 1))
                            || ((coloumnOfBlankTile == 0 || coloumnOfBlankTile == dimension - 1)
                                    && (rowOfBlankTile != 0 && rowOfBlankTile != dimension - 1))) {
                        amountOfNeighbours = 3;
                    } else {
                        amountOfNeighbours = 4;
                    }
                    neighbourBoards = new Board[amountOfNeighbours];
                    int i = 0;

                    if (row != 0) {
                        reset(copyBoard);
                        exchange(copyBoard, row, coloumn, row - 1, coloumn);
                        neighbourBoards[i] = new Board(copyBoard);
                        i++;
                    }
                    if (coloumn != dimension - 1) {
                        reset(copyBoard);
                        exchange(copyBoard, row, coloumn, row, coloumn + 1);
                        neighbourBoards[i] = new Board(copyBoard);
                        i++;
                    }
                    if (row != dimension - 1) {
                        reset(copyBoard);
                        exchange(copyBoard, row, coloumn, row + 1, coloumn);
                        neighbourBoards[i] = new Board(copyBoard);
                        i++;
                    }
                    if (coloumn != 0) {
                        reset(copyBoard);
                        exchange(copyBoard, row, coloumn, row, coloumn - 1);
                        neighbourBoards[i] = new Board(copyBoard);
                        i++;
                    }
                }

                @Override
                public boolean hasNext() {
                    return amountOfNeighbours != 0;
                }

                @Override
                public Board next() {
                    if(amountOfNeighbours == 0) {
                        throw new NoSuchElementException();
                    }
                    return neighbourBoards[--amountOfNeighbours];
                }
            }
        };
    }

    public Board twin() {
        int[][] copyBoard = new int[dimension][dimension];
        reset(copyBoard);
        if (dimension - 1 == rowOfBlankTile
                && (dimension - 1 == coloumnOfBlankTile || dimension - 2 == coloumnOfBlankTile)) {
            exchange(copyBoard, dimension - 2, dimension - 2, dimension - 2, dimension - 1);
        } else {
            exchange(copyBoard, dimension - 1, dimension - 1, dimension - 1, dimension - 2);
        }
        return new Board(copyBoard);
    }

    public static void main(String[] args) {
        int[][] array = { { 1, 2, 3 }, { 4, 0, 6 }, { 7, 8, 5 } };
        int[][] secondArray = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        Board board = new Board(array);
        System.out.println(board.toString());
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        System.out.println(board.isGoal());
        System.out.println(board.equals(new Board(secondArray)));
        for (Board b : board.neighbors()) {
            System.out.println(b.toString());
        }
        System.out.println(board.twin());
        System.out.println(board);
    }
}
