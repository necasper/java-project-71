package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Locale;
import java.util.Map;

public final class Parser {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    private static final String SYNTAX_JSON = "json";
    private static final String SYNTAX_YAML = "yaml";
    private static final String SYNTAX_YAML_SHORT = "yml";

    private Parser() {
    }

    public static Map<String, Object> parse(String content, String syntax) throws Exception {
        String key = syntax.toLowerCase(Locale.ROOT);
        return switch (key) {
            case SYNTAX_YAML_SHORT, SYNTAX_YAML -> YAML_MAPPER.readValue(
                    content, new TypeReference<Map<String, Object>>() { });
            case SYNTAX_JSON -> JSON_MAPPER.readValue(
                    content, new TypeReference<Map<String, Object>>() { });
            default -> throw new RuntimeException("Unknown syntax: " + syntax);
        };
    }
}
