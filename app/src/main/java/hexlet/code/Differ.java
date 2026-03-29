package hexlet.code;

import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

public final class Differ {

    private Differ() {
    }

    public static String generate(Map<String, Object> data1, Map<String, Object> data2) {
        TreeSet<String> keys = new TreeSet<>();
        keys.addAll(data1.keySet());
        keys.addAll(data2.keySet());

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for (String key : keys) {
            boolean in1 = data1.containsKey(key);
            boolean in2 = data2.containsKey(key);
            if (in1 && in2) {
                Object v1 = data1.get(key);
                Object v2 = data2.get(key);
                if (Objects.equals(v1, v2)) {
                    sb.append("    ").append(key).append(": ").append(formatValue(v1)).append('\n');
                } else {
                    sb.append("  - ").append(key).append(": ").append(formatValue(v1)).append('\n');
                    sb.append("  + ").append(key).append(": ").append(formatValue(v2)).append('\n');
                }
            } else if (in1) {
                sb.append("  - ").append(key).append(": ").append(formatValue(data1.get(key))).append('\n');
            } else {
                sb.append("  + ").append(key).append(": ").append(formatValue(data2.get(key))).append('\n');
            }
        }
        sb.append('}');
        return sb.toString();
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return (String) value;
        }
        return value.toString();
    }
}
