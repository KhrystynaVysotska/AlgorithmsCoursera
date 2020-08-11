package sixth.week.lectures;

public class LinearProbingHashST<Key, Value> {
    private int arrayLength = 30001;
    private Key[] keys = (Key[]) new Object[arrayLength];
    private Value[] values = (Value[]) new Object[arrayLength];

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % arrayLength;
    }

    public void put(Key key, Value value) {
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % arrayLength) {
            if (key.equals(keys[i])) {
                break;
            }
        }
        keys[i] = key;
        values[i] = value;
    }

    public Value get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % arrayLength) {
            if (key.equals(keys[i])) {
                return values[i];
            }
        }
        return null;
    }
}
