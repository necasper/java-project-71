package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public final class Differ {

    private Differ() {
    }

    public static String generate(String path1, String path2) throws Exception {
        return generate(path1, path2, "stylish");
    }

    public static String generate(String path1, String path2, String formatName) throws Exception {
        // read
        String text1 = Files.readString(Path.of(path1));
        String text2 = Files.readString(Path.of(path2));
        String inputFormat1 = detectFormatName(path1);
        String inputFormat2 = detectFormatName(path2);

        // parse
        Map<String, Object> data1 = Parser.parse(text1, inputFormat1);
        Map<String, Object> data2 = Parser.parse(text2, inputFormat2);

        // build diff
        var diff = DiffBuilder.build(data1, data2);

        // format
        return Formatter.format(diff, formatName);
    }

    private static String detectFormatName(String path) {
        String lower = path.toLowerCase();
        if (lower.endsWith(".yml") || lower.endsWith(".yaml")) {
            return "yaml";
        }
        return "json";
    }
}
