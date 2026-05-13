package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Locale;
import java.util.Map;

public final class Parser {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    private Parser() {
    }

    public static Map<String, Object> parse(String content, String extension) throws Exception {
        String ext = extension.toLowerCase(Locale.ROOT);
        return switch (ext) {
            case "yml", "yaml" -> YAML_MAPPER.readValue(
                    content, new TypeReference<Map<String, Object>>() { });
            default -> JSON_MAPPER.readValue(
                    content, new TypeReference<Map<String, Object>>() { });
        };
    }
}
