package hexlet.code.formatters;

import hexlet.code.DiffEntry;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class PlainFormatter {

    private PlainFormatter() {
    }

    public static String format(List<DiffEntry> diff) {
        StringBuilder sb = new StringBuilder();
        for (DiffEntry entry : diff) {
            appendEntry(sb, entry);
        }
        if (!sb.isEmpty() && sb.charAt(sb.length() - 1) == '\n') {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    private static void appendEntry(StringBuilder sb, DiffEntry entry) {
        if (entry instanceof DiffEntry.Unchanged) {
            return;
        }
        if (entry instanceof DiffEntry.Removed(var key, var value)) {
            sb.append("Property '").append(key).append("' was removed\n");
        } else if (entry instanceof DiffEntry.Added(var key, var value)) {
            sb.append("Property '").append(key).append("' was added with value: ")
                    .append(formatPlainValue(value)).append('\n');
        } else if (entry instanceof DiffEntry.Changed(var key, var oldValue, var newValue)) {
            sb.append("Property '").append(key).append("' was updated. From ")
                    .append(formatPlainValue(oldValue)).append(" to ")
                    .append(formatPlainValue(newValue)).append('\n');
        }
    }

    private static String formatPlainValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof Map<?, ?> || value instanceof Collection<?>) {
            return "[complex value]";
        }
        if (value instanceof String s) {
            return "'" + s + "'";
        }
        if (value instanceof Boolean || value instanceof Number) {
            return value.toString();
        }
        return value.toString();
    }
}
