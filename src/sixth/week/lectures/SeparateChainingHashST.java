package sixth.week.lectures;

public class SeparateChainingHashST<Key, Value> {
    private int numberOfChains = 97;
    private Node[] array = new Node[numberOfChains];

    private static class Node {
        private Object key;
        private Object value;
        private Node next;

        public Node(Object key, Object value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % numberOfChains;
    }

    public Value get(Key key) {
        int hash = hash(key);
        for (Node x = array[hash]; x != null; x = x.next) {
            if (x.key.equals(key)) {
                return (Value) x.value;
            }
        }
        return null;
    }

    public void put(Key key, Value value) {
        int hash = hash(key);
        for (Node x = array[hash]; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.value = value;
                return;
            }
        }
        array[hash] = new Node(key, value, array[hash]);
    }
}
