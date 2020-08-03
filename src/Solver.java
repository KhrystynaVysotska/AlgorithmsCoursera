import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public final class Solver {
    private final boolean solvable;
    private SearchNode searchNode;
    private SearchNode searchNodeTwin;

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode previousSearchNode;
        private final int priority;
        private final int moves;

        public SearchNode(Board board, SearchNode previousSearchNode, int moves) {
            this.board = board;
            this.previousSearchNode = previousSearchNode;
            this.moves = moves;
            this.priority = board.manhattan() + this.moves;
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
    }

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> pq_twin = new MinPQ<>();
        searchNode = new SearchNode(initial, null, 0);
        searchNodeTwin = new SearchNode(initial.twin(), null, 0);
        while (true) {
            if(searchNode.board.isGoal()) {
                solvable = true;
                break;
            }
            if(searchNodeTwin.board.isGoal()) {
                solvable = false;
                break;
            }
            for (Board neighborBoard : searchNode.board.neighbors()) {
                if (searchNode.previousSearchNode != null
                        && neighborBoard.equals(searchNode.previousSearchNode.board)) {
                    continue;
                }
                pq.insert(new SearchNode(neighborBoard, searchNode, searchNode.moves + 1));
            }
            searchNode = pq.delMin();
            
            for (Board neighborBoard : searchNodeTwin.board.neighbors()) {
                if (searchNodeTwin.previousSearchNode != null
                        && neighborBoard.equals(searchNodeTwin.previousSearchNode.board)) {
                    continue;
                }
                pq_twin.insert(new SearchNode(neighborBoard, searchNodeTwin, searchNodeTwin.moves + 1));
            }
            searchNodeTwin = pq_twin.delMin();
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return searchNode.moves;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        return new Iterable<Board>() {

            @Override
            public Iterator<Board> iterator() {
                return new SolverIterator();
            }

            class SolverIterator implements Iterator<Board> {
                int moves = moves();
                private SearchNode node = searchNode;
                private final Board[] solution = new Board[moves + 1];
                private int iterator = 0;

                public SolverIterator() {
                    int counter = moves;
                    while (node != null) {
                        solution[counter--] = node.board;
                        node = node.previousSearchNode;
                    }
                }

                @Override
                public boolean hasNext() {
                    return iterator < moves + 1;
                }

                @Override
                public Board next() {
                    if (iterator >= moves + 1) {
                        throw new NoSuchElementException();
                    }
                    return solution[iterator++];
                }

            }
        };

    }

    public static void main(String[] args) {
        int[][] tiles = { { 1, 2, 3}, { 4, 0, 6}, { 7, 8, 5}};
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}