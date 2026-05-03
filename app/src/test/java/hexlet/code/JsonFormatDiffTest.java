package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonFormatDiffTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void flatDiffProducesExpectedJsonTree() throws Exception {
        Map<String, Object> data1 = loadMap("flat1.json");
        Map<String, Object> data2 = loadMap("flat2.json");

        JsonNode actual = MAPPER.readTree(Differ.generate(data1, data2, "json"));

        ArrayNode expected = MAPPER.createArrayNode();
        expected.add(record(MAPPER, "removed", "follow", false, null, null));
        expected.add(record(MAPPER, "unchanged", "host", "hexlet.io", null, null));
        expected.add(record(MAPPER, "removed", "proxy", "123.234.53.22", null, null));
        expected.add(record(MAPPER, "changed", "timeout", null, 50, 20));
        expected.add(record(MAPPER, "added", "verbose", true, null, null));

        assertEquals(expected, actual);
    }

    @Test
    void nestedJsonIncludesAllEntriesWithCorrectShape() throws Exception {
        Map<String, Object> data1 = loadMap("nested_file1.json");
        Map<String, Object> data2 = loadMap("nested_file2.json");
        List<DiffEntry> diff = DiffBuilder.build(data1, data2);

        JsonNode root = MAPPER.readTree(Differ.generate(data1, data2, "json"));

        assertTrue(root.isArray());
        assertEquals(diff.size(), root.size());
        for (JsonNode node : root) {
            assertTrue(node.has("type"));
            assertTrue(node.has("key"));
            String type = node.get("type").asText();
            switch (type) {
                case "unchanged", "removed", "added" -> assertTrue(node.has("value"));
                case "changed" -> {
                    assertTrue(node.has("oldValue"));
                    assertTrue(node.has("newValue"));
                }
                default -> throw new AssertionError("Unexpected type: " + type);
            }
        }
    }

    @Test
    void generateWithJsonFormatViaFilePaths() throws Exception {
        URL url1 = JsonFormatDiffTest.class.getClassLoader().getResource("flat1.json");
        URL url2 = JsonFormatDiffTest.class.getClassLoader().getResource("flat2.json");
        if (url1 == null || url2 == null) {
            throw new IllegalStateException("Missing flat fixtures");
        }
        String path1 = Path.of(url1.toURI()).toString();
        String path2 = Path.of(url2.toURI()).toString();

        JsonNode fromPaths = MAPPER.readTree(Differ.generate(path1, path2, "json"));
        JsonNode fromMaps = MAPPER.readTree(Differ.generate(loadMap("flat1.json"), loadMap("flat2.json"), "json"));

        assertEquals(fromMaps, fromPaths);
    }

    private static ObjectNode record(ObjectMapper mapper, String type, String key, Object value,
            Object oldValue, Object newValue) {
        ObjectNode node = mapper.createObjectNode();
        node.put("type", type);
        node.put("key", key);
        if ("changed".equals(type)) {
            node.set("oldValue", mapper.valueToTree(oldValue));
            node.set("newValue", mapper.valueToTree(newValue));
        } else {
            node.set("value", mapper.valueToTree(value));
        }
        return node;
    }

    private static Map<String, Object> loadMap(String resourceName) throws Exception {
        URL url = JsonFormatDiffTest.class.getClassLoader().getResource(resourceName);
        if (url == null) {
            throw new IllegalStateException("Missing test resource: " + resourceName);
        }
        String path = Path.of(url.toURI()).toString();
        return Parser.getData(Parser.readFile(path), resourceName);
    }
}
