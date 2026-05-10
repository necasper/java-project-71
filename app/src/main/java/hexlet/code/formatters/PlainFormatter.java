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
        if (entry.getType() == DiffEntry.Type.UNCHANGED) {
            return;
        }
        String key = entry.getKey();
        if (entry.getType() == DiffEntry.Type.REMOVED) {
            sb.append("Property '").append(key).append("' was removed\n");
        } else if (entry.getType() == DiffEntry.Type.ADDED) {
            sb.append("Property '").append(key).append("' was added with value: ")
                    .append(formatPlainValue(entry.getValue())).append('\n');
        } else if (entry.getType() == DiffEntry.Type.CHANGED) {
            sb.append("Property '").append(key).append("' was updated. From ")
                    .append(formatPlainValue(entry.getOldValue())).append(" to ")
                    .append(formatPlainValue(entry.getNewValue())).append('\n');
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
