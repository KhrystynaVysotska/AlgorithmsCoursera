import java.util.Arrays;

public class FastCollinearPoints {
    private int numberOfSegments = 0;
    private LineSegment[] lineSegments = new LineSegment[0];
    private Point[] points = new Point[2];
    private int counterOfPointsInLineSegments = 0;

    public FastCollinearPoints(Point[] points) {
        validateInput(points);
        int N = points.length;
        for (int i = 0; i < N; i++) {
            Arrays.sort(points);
            Point point = points[i];
            Arrays.sort(points, point.slopeOrder());
            int collinearPoints = 2;
            for (int j = 0; j < N; j += collinearPoints - 1) {
                collinearPoints = 2;
                int counter = j;
                double slope;
                while (counter < N - 1) {
                    if (point.compareTo(points[counter]) != 0) {
                        slope = point.slopeTo(points[counter]);
                        if (point.compareTo(points[counter + 1]) == 0) {
                            counter++;
                            if (counter + 1 >= N) {
                                break;
                            }
                        }
                        if (point.slopeTo(points[counter + 1]) == slope) {
                            collinearPoints++;
                            counter++;
                        } else {
                            break;
                        }
                    } else {
                        counter++;
                    }
                }

                if (collinearPoints >= 4) {
                    int position = j;
                    if (points[j].compareTo(point) == 0) {
                        position++;
                    }
                    Point firstEnd = points[position].compareTo(point) < 0 ? points[position] : point;
                    if (points[counter].compareTo(point) == 0) {
                        counter--;
                    }
                    Point anotherEnd = points[counter].compareTo(point) > 0 ? points[counter] : point;
                    boolean isNew = true;
                    if (numberOfSegments != 0) {
                        for (int k = 0; k < this.points.length; k += 2) {
                            if (firstEnd == this.points[k] && anotherEnd == this.points[k + 1]) {
                                isNew = false;
                            }
                        }
                        if (isNew) {
                            if (numberOfSegments >= lineSegments.length) {
                                resizeLineSegmentsLength(lineSegments.length + 1);
                            }
                            lineSegments[numberOfSegments++] = new LineSegment(firstEnd, anotherEnd);
                            resizePointsLength(2 * lineSegments.length);
                            this.points[counterOfPointsInLineSegments++] = firstEnd;
                            this.points[counterOfPointsInLineSegments++] = anotherEnd;
                        }
                    } else {
                        lineSegments = new LineSegment[1];
                        lineSegments[numberOfSegments++] = new LineSegment(firstEnd, anotherEnd);
                        this.points[counterOfPointsInLineSegments++] = firstEnd;
                        this.points[counterOfPointsInLineSegments++] = anotherEnd;
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

    private void resizePointsLength(int capacity) {
        Point[] copy = new Point[capacity];
        for (int i = 0; i < points.length; i++) {
            copy[i] = points[i];
        }
        points = copy;
    }

    private void resizeLineSegmentsLength(int capacity) {
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
