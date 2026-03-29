package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public final class Parser {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private Parser() {
    }

    public static String readFile(String path) throws Exception {
        return Files.readString(Path.of(path));
    }

    public static Map<String, Object> getData(String content) throws Exception {
        return MAPPER.readValue(content, new TypeReference<Map<String, Object>>() { });
    }
}
