package hexlet.code;

import java.util.Map;

public final class Differ {

    private Differ() {
    }

    public static String generate(Map<String, Object> data1, Map<String, Object> data2, String format) {
        var diff = DiffBuilder.build(data1, data2);
        return switch (format) {
            case "stylish" -> StylishFormatter.format(diff);
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }

    public static String generate(Map<String, Object> data1, Map<String, Object> data2) {
        return generate(data1, data2, "stylish");
    }
}
