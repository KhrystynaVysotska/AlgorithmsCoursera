package forth.week.lectures;

import edu.princeton.cs.algs4.Queue;

public class BinarySearchTree<Key extends Comparable<Key>, Value> {
    private Node root;

    private class Node {
        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private int count;

        public Node(Key key, Value value, int count) {
            this.key = key;
            this.value = value;
            this.count = count;
        }
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    public int size() {
        return size(root);
    }

    private int size(Node root) {
        if (root == null) {
            return 0;
        }
        return root.count;
    }

    private Node put(Node x, Key key, Value value) {
        if (x == null) {
            return new Node(key, value, 1);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, value);
        } else if (cmp > 0) {
            x.right = put(x.right, key, value);
        } else {
            x.value = value;
        }
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    public Value get(Key key) {
        Node searchNode = root;
        while (searchNode != null) {
            int cmp = key.compareTo(searchNode.key);
            if (cmp < 0) {
                searchNode = searchNode.left;
            } else if (cmp > 0) {
                searchNode = searchNode.right;
            } else {
                return searchNode.value;
            }
        }
        return null;
    }
    
    public int rank(Key key) {
        return rank(key, root);
    }
    
    private int rank(Key key, Node x) {
        if(x == null) {
            return 0;
        }
        int cmp = key.compareTo(x.key);
        if(cmp < 0) {
            return rank(key, x.left);
        } else if(cmp > 0) {
            return 1 + size(x.left) + rank(key, x.right);
        } else {
            return size(x.left);
        }
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) {
            return null;
        }
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x;
        }
        if (cmp < 0) {
            return floor(x.left, key);
        }
        Node t = floor(x.right, key);
        if (t != null) {
            return t;
        } else {
            return x;
        }
    }

    public void delMin() {
        root = delMin(root);
    }
    private Node delMin(Node x) {
        if(x.left == null) {
            return x.right;
        }
        x.left = delMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }
    
    public void delete(Key key) {
        root = delete(root, key);
    }
    private Node delete(Node x, Key key) {
        if(x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if(cmp < 0) {
            x.left = delete(x.left, key);
        } else if(cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            if(x.right == null) {
                return x.left;
            }
            if(x.left == null) {
                return x.right;
            }
            
            Node temp = x;
            x = min(temp.right);
            x.right = delMin(temp.right);
            x.left = temp.left;
        }
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }
    
    public Node min(Node x) {
        if(x.left == null) {
            return x;
        }
        return min(x.left);
    }
    public Iterable<Key> iterator() {
         Queue<Key> queue = new Queue<>();
         inorder(root, queue);
         return queue;
    }
    private void inorder(Node x, Queue<Key> q) {
        if(x == null) {
            return;
        }
        inorder(x.left, q);
        q.enqueue(x.key);
        inorder(x.right, q);
    }
}
