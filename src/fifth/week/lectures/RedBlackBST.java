package fifth.week.lectures;

public class RedBlackBST<Key extends Comparable<Key>, Value> {

    private Node root;
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private boolean color;

        public Node(Key key, Value value, boolean color) {
            this.key = key;
            this.value = value;
            this.color = color;
        }
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
        root.color = BLACK;
    }

    private Node put(Node h, Key key, Value value) {
        if(h == null) {
            return new Node(key, value, RED);
        }
        int cmp = key.compareTo(h.key);
        if(cmp < 0) {
            h.left = put(h.left, key, value);
        } else if(cmp > 0) {
            h.right = put(h.right, key, value);
        } else {
            h.value = value;
        }
        if(isRed(h.right) && !isRed(h.left)) {
            h = rotateLeft(h);
        }
        if(isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if(isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }
        
        return h;
    }

    private boolean isRed(Node x) {
        if (x == null) {
            return false;
        }
        return x.color == RED;
    }

    private Node rotateLeft(Node h) {
        assert isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private Node rotateRight(Node h) {
        assert isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private void flipColors(Node h) {
        assert !isRed(h);
        assert isRed(h.left);
        assert isRed(h.right);
        h.left.color = BLACK;
        h.right.color = BLACK;
        h.color = RED;
    }

    public Value get(Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else {
                return x.value;
            }
        }
        return null;
    }
}
