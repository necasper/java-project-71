package hexlet.code;

public sealed interface DiffEntry {

    record Unchanged(String key, Object value) implements DiffEntry {
    }

    record Removed(String key, Object value) implements DiffEntry {
    }

    record Added(String key, Object value) implements DiffEntry {
    }

    record Changed(String key, Object oldValue, Object newValue) implements DiffEntry {
    }
}
