package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
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
        String inputFormat1 = extractExtension(path1);
        String inputFormat2 = extractExtension(path2);

        // parse
        Map<String, Object> data1 = Parser.parse(text1, inputFormat1);
        Map<String, Object> data2 = Parser.parse(text2, inputFormat2);

        // build diff
        var diff = DiffBuilder.build(data1, data2);

        // format
        return Formatter.format(diff, formatName);
    }

    static String extractExtension(String path) {
        int slash = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));
        String name = slash >= 0 ? path.substring(slash + 1) : path;
        int dot = name.lastIndexOf('.');
        if (dot <= 0 || dot == name.length() - 1) {
            return "";
        }
        return name.substring(dot + 1).toLowerCase(Locale.ROOT);
    }
}
