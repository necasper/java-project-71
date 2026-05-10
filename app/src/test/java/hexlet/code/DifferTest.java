package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DifferTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final int TIMEOUT_OLD = 50;
    private static final int TIMEOUT_NEW = 20;
    private static final int FLAT_JSON_DIFF_ENTRIES = 5;

    // 2 input formats (json, yml) * 4 output formats (default, stylish, plain, json)

    @Test
    void jsonInputDefaultFormatUsesStylish() throws Exception {
        String p1 = resourcePath("flat1.json");
        String p2 = resourcePath("flat2.json");

        String actual = Differ.generate(p1, p2);
        String expected = Differ.generate(p1, p2, "stylish");

        assertEquals(expected, actual);
    }

    @Test
    void jsonInputStylish() throws Exception {
        String p1 = resourcePath("nested_file1.json");
        String p2 = resourcePath("nested_file2.json");

        String actual = Differ.generate(p1, p2, "stylish");

        String expected = """
                {
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }""";

        assertEquals(expected, actual);
    }

    @Test
    void jsonInputPlain() throws Exception {
        String p1 = resourcePath("nested_file1.json");
        String p2 = resourcePath("nested_file2.json");

        String actual = Differ.generate(p1, p2, "plain");

        String expected = """
                Property 'chars2' was updated. From [complex value] to false
                Property 'checked' was updated. From false to true
                Property 'default' was updated. From null to [complex value]
                Property 'id' was updated. From 45 to null
                Property 'key1' was removed
                Property 'key2' was added with value: 'value2'
                Property 'numbers2' was updated. From [complex value] to [complex value]
                Property 'numbers3' was removed
                Property 'numbers4' was added with value: [complex value]
                Property 'obj1' was added with value: [complex value]
                Property 'setting1' was updated. From 'Some value' to 'Another value'
                Property 'setting2' was updated. From 200 to 300
                Property 'setting3' was updated. From true to 'none'""";

        assertEquals(expected, actual);
    }

    @Test
    void jsonInputJsonOutput() throws Exception {
        String p1 = resourcePath("flat1.json");
        String p2 = resourcePath("flat2.json");

        JsonNode actual = MAPPER.readTree(Differ.generate(p1, p2, "json"));

        ArrayNode expected = MAPPER.createArrayNode();
        expected.add(record("removed", "follow", false, null, null));
        expected.add(record("unchanged", "host", "hexlet.io", null, null));
        expected.add(record("removed", "proxy", "123.234.53.22", null, null));
        expected.add(record("changed", "timeout", null, TIMEOUT_OLD, TIMEOUT_NEW));
        expected.add(record("added", "verbose", true, null, null));

        assertEquals(expected, actual);
    }

    @Test
    void ymlInputDefaultFormatUsesStylish() throws Exception {
        String p1 = resourcePath("flat1.yml");
        String p2 = resourcePath("flat2.yml");

        String actual = Differ.generate(p1, p2);
        String expected = Differ.generate(p1, p2, "stylish");

        assertEquals(expected, actual);
    }

    @Test
    void ymlInputStylish() throws Exception {
        String p1 = resourcePath("flat1.yml");
        String p2 = resourcePath("flat2.yml");

        String actual = Differ.generate(p1, p2, "stylish");

        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        assertEquals(expected, actual);
    }

    @Test
    void ymlInputPlain() throws Exception {
        String p1 = resourcePath("flat1.yml");
        String p2 = resourcePath("flat2.yml");

        String actual = Differ.generate(p1, p2, "plain");

        String expected = """
                Property 'follow' was removed
                Property 'proxy' was removed
                Property 'timeout' was updated. From 50 to 20
                Property 'verbose' was added with value: true""";

        assertEquals(expected, actual);
    }

    @Test
    void ymlInputJsonOutput() throws Exception {
        String p1 = resourcePath("flat1.yml");
        String p2 = resourcePath("flat2.yml");

        JsonNode actual = MAPPER.readTree(Differ.generate(p1, p2, "json"));

        assertTrue(actual.isArray());
        assertEquals(FLAT_JSON_DIFF_ENTRIES, actual.size());
        assertEquals("host", actual.get(1).get("key").asText());
        assertEquals("unchanged", actual.get(1).get("type").asText());
    }

    private static ObjectNode record(String type, String key, Object value, Object oldValue, Object newValue) {
        ObjectNode node = MAPPER.createObjectNode();
        node.put("type", type);
        node.put("key", key);
        if ("changed".equals(type)) {
            node.set("oldValue", MAPPER.valueToTree(oldValue));
            node.set("newValue", MAPPER.valueToTree(newValue));
        } else {
            node.set("value", MAPPER.valueToTree(value));
        }
        return node;
    }

    private static String resourcePath(String name) throws Exception {
        URL url = DifferTest.class.getClassLoader().getResource(name);
        if (url == null) {
            throw new IllegalStateException("Missing test resource: " + name);
        }
        return Path.of(url.toURI()).toString();
    }
}

