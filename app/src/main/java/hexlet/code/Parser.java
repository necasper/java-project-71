package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public final class Parser {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    private Parser() {
    }

    public static String readFile(String path) throws Exception {
        return Files.readString(Path.of(path));
    }

    public static Map<String, Object> getData(String content, String filePath) throws Exception {
        if (isYamlPath(filePath)) {
            return YAML_MAPPER.readValue(content, new TypeReference<Map<String, Object>>() { });
        }
        return JSON_MAPPER.readValue(content, new TypeReference<Map<String, Object>>() { });
    }

    private static boolean isYamlPath(String filePath) {
        String lower = filePath.toLowerCase();
        return lower.endsWith(".yml") || lower.endsWith(".yaml");
    }
}
