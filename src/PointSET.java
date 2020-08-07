import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> setOfPoints;

    public PointSET() {
        setOfPoints = new TreeSet<>();
    }

    public boolean isEmpty() {
        return setOfPoints.isEmpty();
    }

    public int size() {
        return setOfPoints.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        setOfPoints.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return setOfPoints.contains(p);
    }

    public void draw() {
        for (Point2D point : setOfPoints) {
            StdDraw.point(point.x(), point.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        Queue<Point2D> queueOfPoints = new Queue<>();
        for (Point2D point : setOfPoints) {
            if (rect.contains(point)) {
                queueOfPoints.enqueue(point);
            }
        }
        return queueOfPoints;
    }

    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (setOfPoints.isEmpty()) {
            return null;
        }
        Point2D champion = setOfPoints.first();
        for (Point2D point : setOfPoints) {
            if (point.distanceSquaredTo(p) < champion.distanceSquaredTo(p)) {
                champion = point;
            }
        }
        return champion;
    }
}
