package hexlet.code;

import java.util.Map;

public final class Differ {

    private Differ() {
    }

    public static String generate(String filePath1, String filePath2, String formatName) throws Exception {
        Map<String, Object> data1 = Parser.getData(Parser.readFile(filePath1), filePath1);
        Map<String, Object> data2 = Parser.getData(Parser.readFile(filePath2), filePath2);
        return generate(data1, data2, formatName);
    }

    public static String generate(Map<String, Object> data1, Map<String, Object> data2, String format) {
        var diff = DiffBuilder.build(data1, data2);
        return Formatter.format(diff, format);
    }

    public static String generate(Map<String, Object> data1, Map<String, Object> data2) {
        return generate(data1, data2, "stylish");
    }
}
