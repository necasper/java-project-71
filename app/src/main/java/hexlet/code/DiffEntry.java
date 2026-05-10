package hexlet.code;

public final class DiffEntry {

    public enum Type {
        UNCHANGED,
        REMOVED,
        ADDED,
        CHANGED
    }

    private final Type type;
    private final String key;
    private final Object value;
    private final Object oldValue;
    private final Object newValue;

    private DiffEntry(Type type, String key, Object value, Object oldValue, Object newValue) {
        this.type = type;
        this.key = key;
        this.value = value;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public static DiffEntry unchanged(String key, Object value) {
        return new DiffEntry(Type.UNCHANGED, key, value, null, null);
    }

    public static DiffEntry removed(String key, Object value) {
        return new DiffEntry(Type.REMOVED, key, value, null, null);
    }

    public static DiffEntry added(String key, Object value) {
        return new DiffEntry(Type.ADDED, key, value, null, null);
    }

    public static DiffEntry changed(String key, Object oldValue, Object newValue) {
        return new DiffEntry(Type.CHANGED, key, null, oldValue, newValue);
    }

    public Type getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }
}
