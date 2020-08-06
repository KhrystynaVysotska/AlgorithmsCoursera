import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    private static final boolean VerticalLine = true;
    private static final boolean HorizontalLine = false;
    private Node root;

    private class Node {
        private double key;
        private Point2D point;
        private Node left;
        private Node right;
        private boolean colorOfLine;
        private int size;
        private RectHV rectangle;

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
        if (!contains(p)) {
            root = root == null ? new Node(p, VerticalLine, 1, new RectHV(0, 0, 1, 1))
                    : insert(root, root.colorOfLine, p, root.rectangle);
        }
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
        } else {
            if(x.colorOfLine) {
                x.right = insert(x.right, x.colorOfLine, p, new RectHV(x.point.x(), rectangleOfParent.ymin(), rectangleOfParent.xmax(), rectangleOfParent.ymax()));
            } else {
                x.right = insert(x.right, x.colorOfLine, p, new RectHV(rectangleOfParent.xmin(), x.point.y(), rectangleOfParent.xmax(), rectangleOfParent.ymax()));
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

    public void draw() { // draw all points to standard draw

    }

    public Iterable<Point2D> range(RectHV rect) {
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
        nearest(p, root, champion);
        return champion;
    }

    private void nearest(Point2D p, Node x, Point2D champion) {
        if (x == null) {
            return;
        }
        if (p.distanceTo(x.point) < p.distanceTo(champion)) {
            champion = x.point;
        }
        if ((x.colorOfLine && p.x() <= x.point.x()) || (!x.colorOfLine && p.y() <= x.point.y())) {
            nearest(p, x.left, champion);
            if (x.right != null && x.right.rectangle.distanceTo(p) < p.distanceTo(champion)) {
                nearest(p, x.right, champion);
            }
        }
        if ((x.colorOfLine && p.x() > x.point.x()) || (!x.colorOfLine && p.y() > x.point.y())) {
            nearest(p, x.right, champion);
            if (x.left != null && x.left.rectangle.distanceTo(p) < p.distanceTo(champion)) {
                nearest(p, x.left, champion);
            }
        }
    }

    public static void main(String[] args) {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.7, 0.2));
        tree.insert(new Point2D(0.5, 0.4));
        tree.insert(new Point2D(0.2, 0.3));
        tree.insert(new Point2D(0.4, 0.7));
        tree.insert(new Point2D(0.9, 0.6));
        RectHV rect = new RectHV(0.3, 0.1, 0.9, 0.8);
        System.out.println(tree.nearest(new Point2D(0.1, 0.2)).x() + " " + tree.nearest(new Point2D(0.1, 0.2)).y());
    }
}
