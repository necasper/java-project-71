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
        String key = entry.getKey();
        if (entry.getType() == DiffEntry.Type.UNCHANGED) {
            sb.append("    ").append(key).append(": ").append(formatValue(entry.getValue())).append('\n');
        } else if (entry.getType() == DiffEntry.Type.REMOVED) {
            sb.append("  - ").append(key).append(": ").append(formatValue(entry.getValue())).append('\n');
        } else if (entry.getType() == DiffEntry.Type.ADDED) {
            sb.append("  + ").append(key).append(": ").append(formatValue(entry.getValue())).append('\n');
        } else if (entry.getType() == DiffEntry.Type.CHANGED) {
            sb.append("  - ").append(key).append(": ").append(formatValue(entry.getOldValue())).append('\n');
            sb.append("  + ").append(key).append(": ").append(formatValue(entry.getNewValue())).append('\n');
        }
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        return value.toString();
    }
}
