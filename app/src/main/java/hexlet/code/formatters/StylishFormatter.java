package hexlet.code.formatters;

import hexlet.code.DiffEntry;

import java.util.List;

public final class StylishFormatter {

    private StylishFormatter() {
    }

    public static String format(List<DiffEntry> diff) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for (DiffEntry entry : diff) {
            appendEntry(sb, entry);
        }
        sb.append('}');
        return sb.toString();
    }

    private static void appendEntry(StringBuilder sb, DiffEntry entry) {
        if (entry instanceof DiffEntry.Unchanged(var key, var value)) {
            sb.append("    ").append(key).append(": ").append(formatValue(value)).append('\n');
        } else if (entry instanceof DiffEntry.Removed(var key, var value)) {
            sb.append("  - ").append(key).append(": ").append(formatValue(value)).append('\n');
        } else if (entry instanceof DiffEntry.Added(var key, var value)) {
            sb.append("  + ").append(key).append(": ").append(formatValue(value)).append('\n');
        } else if (entry instanceof DiffEntry.Changed(var key, var oldValue, var newValue)) {
            sb.append("  - ").append(key).append(": ").append(formatValue(oldValue)).append('\n');
            sb.append("  + ").append(key).append(": ").append(formatValue(newValue)).append('\n');
        }
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        return value.toString();
    }
}
