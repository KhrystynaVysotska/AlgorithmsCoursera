import java.util.Arrays;

public class BruteCollinearPoints {
    private int numberOfSegments = 0;
    private LineSegment[] lineSegments = new LineSegment[0];

    public BruteCollinearPoints(Point[] points) {
        Point[] pointsToDraw = new Point[4];
        validateInput(points);
        int N = points.length;
        for (int firstPoint = 0; firstPoint < N; firstPoint++) {
            for (int secondPoint = firstPoint + 1; secondPoint < N; secondPoint++) {
                for (int thirdPoint = secondPoint + 1; thirdPoint < N; thirdPoint++) {
                    if (points[firstPoint].slopeTo(points[secondPoint]) == points[firstPoint]
                            .slopeTo(points[thirdPoint])) {
                        for (int forthPoint = thirdPoint + 1; forthPoint < N; forthPoint++) {
                            if (points[firstPoint].slopeTo(points[thirdPoint]) == points[firstPoint]
                                    .slopeTo(points[forthPoint])) {
                                if (numberOfSegments == 0) {
                                    lineSegments = new LineSegment[1];
                                }
                                if (numberOfSegments >= lineSegments.length) {
                                    resize(lineSegments.length + 1);
                                }
                                pointsToDraw[0] = points[firstPoint];
                                pointsToDraw[1] = points[secondPoint];
                                pointsToDraw[2] = points[thirdPoint];
                                pointsToDraw[3] = points[forthPoint];
                                Arrays.sort(pointsToDraw);
                                lineSegments[numberOfSegments++] = new LineSegment(pointsToDraw[0], pointsToDraw[3]);
                            }
                        }
                    }
                }
            }
        }
    }

    private void validateInput(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        int N = points.length;
        for (int i = 0; i < N; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < N; j++) {
                if(points[j] == null) {
                    throw new IllegalArgumentException();
                }
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    private void resize(int capacity) {
        LineSegment[] copy = new LineSegment[capacity];
        for (int i = 0; i < lineSegments.length; i++) {
            copy[i] = lineSegments[i];
        }
        lineSegments = copy;
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return lineSegments;
    }
}
