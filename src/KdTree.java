import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static final boolean VERTICALLINE = true;
    private static final boolean HORIZONTALLINE = false;
    private Node root;

    private class Node {
        private double key;
        private final Point2D point;
        private Node left;
        private Node right;
        private final boolean colorOfLine;
        private int size;
        private final RectHV rectangle;

        public Node(Point2D point, boolean colorOfLine, int size, RectHV rect) {
            if (colorOfLine) {
                this.key = point.x();
            } else {
                this.key = point.y();
            }
            this.point = point;
            this.colorOfLine = colorOfLine;
            this.size = size;
            this.rectangle = rect;
        }
    }

    public KdTree() {

    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        root = root == null ? new Node(p, VERTICALLINE, 1, new RectHV(0, 0, 1, 1))
                : insert(root, root.colorOfLine, p, root.rectangle);
    }

    private Node insert(Node x, boolean colorOfPreviousNode, Point2D p, RectHV rectangleOfParent) {
        if (x == null) {
            return new Node(p, !colorOfPreviousNode, 1, rectangleOfParent);
        }
        double compare = x.colorOfLine ? p.x() - x.key : p.y() - x.key;
        if (compare < 0) {
            if (x.colorOfLine) {
                x.left = insert(x.left, x.colorOfLine, p, new RectHV(rectangleOfParent.xmin(), rectangleOfParent.ymin(),
                        x.point.x(), rectangleOfParent.ymax()));
            } else {
                x.left = insert(x.left, x.colorOfLine, p, new RectHV(rectangleOfParent.xmin(), rectangleOfParent.ymin(),
                        rectangleOfParent.xmax(), x.point.y()));
            }
        } else if (compare > 0) {
            if (x.colorOfLine) {
                x.right = insert(x.right, x.colorOfLine, p, new RectHV(x.point.x(), rectangleOfParent.ymin(),
                        rectangleOfParent.xmax(), rectangleOfParent.ymax()));
            } else {
                x.right = insert(x.right, x.colorOfLine, p, new RectHV(rectangleOfParent.xmin(), x.point.y(),
                        rectangleOfParent.xmax(), rectangleOfParent.ymax()));
            }
        } else {
            if (x.colorOfLine) {
                if (p.y() - x.point.y() != 0) {
                    x.right = insert(x.right, x.colorOfLine, p, new RectHV(x.point.x(), rectangleOfParent.ymin(),
                            rectangleOfParent.xmax(), rectangleOfParent.ymax()));
                }
            } else {
                if (p.x() - x.point.x() != 0) {
                    x.right = insert(x.right, x.colorOfLine, p, new RectHV(rectangleOfParent.xmin(), x.point.y(),
                            rectangleOfParent.xmax(), rectangleOfParent.ymax()));
                }
            }
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.size;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return contains(root, p);
    }

    private boolean contains(Node x, Point2D point) {
        if (x == null) {
            return false;
        }
        double compare = x.colorOfLine ? point.x() - x.key : point.y() - x.key;
        if (compare < 0) {
            return contains(x.left, point);
        } else if (compare > 0) {
            return contains(x.right, point);
        } else {
            if (x.colorOfLine) {
                if (point.y() - x.point.y() == 0) {
                    return true;
                } else {
                    return contains(x.right, point);
                }
            } else {
                if (point.x() - x.point.x() == 0) {
                    return true;
                } else {
                    return contains(x.right, point);
                }
            }
        }
    }

    public void draw() {
        if (!isEmpty()) {
            draw(root);
        }
    }

    private void draw(Node x) {
        if (x == null) {
            return;
        }
        StdDraw.setPenRadius(0.01);
        if (x.colorOfLine) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.point.x(), x.rectangle.ymin(), x.point.x(), x.rectangle.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.rectangle.xmin(), x.point.y(), x.rectangle.xmax(), x.point.y());
        }
        StdDraw.setPenRadius(0.015);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(x.point.x(), x.point.y());
        draw(x.left);
        draw(x.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        Queue<Point2D> queue = new Queue<>();
        searchRange(rect, root, queue);
        return queue;
    }

    private void searchRange(RectHV rect, Node x, Queue<Point2D> q) {
        if (x == null) {
            return;
        }
        if (rect.contains(x.point)) {
            q.enqueue(x.point);
        }
        if ((x.colorOfLine && rect.xmax() < x.point.x()) || (!x.colorOfLine && rect.ymax() < x.point.y())) {
            searchRange(rect, x.left, q);
        } else if ((x.colorOfLine && rect.xmin() > x.point.x()) || (!x.colorOfLine && rect.ymin() > x.point.y())) {
            searchRange(rect, x.right, q);
        } else {
            searchRange(rect, x.left, q);
            searchRange(rect, x.right, q);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            return null;
        }
        Point2D champion = root.point;
        return nearest(p, root, champion);
    }

    private Point2D nearest(Point2D p, Node x, Point2D champion) {
        Point2D nearest = champion;
        if (x == null) {
            return nearest;
        }
        if (p.distanceSquaredTo(x.point) < p.distanceSquaredTo(nearest)) {
            nearest = x.point;
        }
        if ((x.colorOfLine && p.x() <= x.point.x()) || (!x.colorOfLine && p.y() <= x.point.y())) {
            nearest = nearest(p, x.left, nearest);
            if (x.right != null && x.right.rectangle.distanceSquaredTo(p) < p.distanceSquaredTo(nearest)) {
                nearest = nearest(p, x.right, nearest);
            }
        }
        if ((x.colorOfLine && p.x() > x.point.x()) || (!x.colorOfLine && p.y() > x.point.y())) {
            nearest = nearest(p, x.right, nearest);
            if (x.left != null && x.left.rectangle.distanceSquaredTo(p) < p.distanceSquaredTo(nearest)) {
                nearest = nearest(p, x.left, nearest);
            }
        }
        return nearest;
    }
}
