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
        if (entry instanceof DiffEntry.Unchanged(var key, var value)) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("type", "unchanged");
            m.put("key", key);
            m.put("value", value);
            return m;
        }
        if (entry instanceof DiffEntry.Removed(var key, var value)) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("type", "removed");
            m.put("key", key);
            m.put("value", value);
            return m;
        }
        if (entry instanceof DiffEntry.Added(var key, var value)) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("type", "added");
            m.put("key", key);
            m.put("value", value);
            return m;
        }
        if (entry instanceof DiffEntry.Changed(var key, var oldValue, var newValue)) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("type", "changed");
            m.put("key", key);
            m.put("oldValue", oldValue);
            m.put("newValue", newValue);
            return m;
        }
        throw new IllegalStateException("Unexpected entry: " + entry);
    }
}
