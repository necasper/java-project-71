package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DiffEntry;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class JsonFormatter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonFormatter() {
    }

    public static String format(List<DiffEntry> diff) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(toRecords(diff));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    private static List<Map<String, Object>> toRecords(List<DiffEntry> diff) {
        List<Map<String, Object>> records = new ArrayList<>();
        for (DiffEntry entry : diff) {
            records.add(toRecord(entry));
        }
        return records;
    }

    private static Map<String, Object> toRecord(DiffEntry entry) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("key", entry.getKey());
        if (entry.getType() == DiffEntry.Type.UNCHANGED) {
            m.put("type", "unchanged");
            m.put("value", entry.getValue());
        } else if (entry.getType() == DiffEntry.Type.REMOVED) {
            m.put("type", "removed");
            m.put("value", entry.getValue());
        } else if (entry.getType() == DiffEntry.Type.ADDED) {
            m.put("type", "added");
            m.put("value", entry.getValue());
        } else if (entry.getType() == DiffEntry.Type.CHANGED) {
            m.put("type", "changed");
            m.put("oldValue", entry.getOldValue());
            m.put("newValue", entry.getNewValue());
        } else {
            throw new IllegalStateException("Unknown type: " + entry.getType());
        }
        return m;
    }
}
