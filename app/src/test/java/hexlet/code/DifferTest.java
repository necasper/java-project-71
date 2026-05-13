package hexlet.code;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 2 input formats (json, yml) * 4 output formats (default, stylish, plain, json)

    @Test
    void jsonInputDefaultFormatUsesStylish() throws Exception {
        String p1 = resourcePath("fixtures/flat1.json");
        String p2 = resourcePath("fixtures/flat2.json");

        String actual = Differ.generate(p1, p2);
        String expected = Differ.generate(p1, p2, "stylish");

        assertEquals(expected, actual);
    }

    @Test
    void jsonInputStylish() throws Exception {
        String p1 = resourcePath("fixtures/nested_file1.json");
        String p2 = resourcePath("fixtures/nested_file2.json");

        String actual = Differ.generate(p1, p2, "stylish");

        assertEquals(readExpected("expected/nested_stylish.txt"), actual);
    }

    @Test
    void jsonInputPlain() throws Exception {
        String p1 = resourcePath("fixtures/nested_file1.json");
        String p2 = resourcePath("fixtures/nested_file2.json");

        String actual = Differ.generate(p1, p2, "plain");

        assertEquals(readExpected("expected/nested_plain.txt"), actual);
    }

    @Test
    void jsonInputJsonOutput() throws Exception {
        String p1 = resourcePath("fixtures/flat1.json");
        String p2 = resourcePath("fixtures/flat2.json");

        JsonNode actual = MAPPER.readTree(Differ.generate(p1, p2, "json"));
        JsonNode expected = MAPPER.readTree(readExpected("expected/flat_json.json"));

        assertEquals(expected, actual);
    }

    @Test
    void ymlInputDefaultFormatUsesStylish() throws Exception {
        String p1 = resourcePath("fixtures/flat1.yml");
        String p2 = resourcePath("fixtures/flat2.yml");

        String actual = Differ.generate(p1, p2);
        String expected = Differ.generate(p1, p2, "stylish");

        assertEquals(expected, actual);
    }

    @Test
    void ymlInputStylish() throws Exception {
        String p1 = resourcePath("fixtures/flat1.yml");
        String p2 = resourcePath("fixtures/flat2.yml");

        String actual = Differ.generate(p1, p2, "stylish");

        assertEquals(readExpected("expected/flat_yml_stylish.txt"), actual);
    }

    @Test
    void ymlInputPlain() throws Exception {
        String p1 = resourcePath("fixtures/flat1.yml");
        String p2 = resourcePath("fixtures/flat2.yml");

        String actual = Differ.generate(p1, p2, "plain");

        assertEquals(readExpected("expected/flat_yml_plain.txt"), actual);
    }

    @Test
    void ymlInputJsonOutput() throws Exception {
        String p1 = resourcePath("fixtures/flat1.yml");
        String p2 = resourcePath("fixtures/flat2.yml");

        JsonNode actual = MAPPER.readTree(Differ.generate(p1, p2, "json"));
        JsonNode expected = MAPPER.readTree(readExpected("expected/flat_json.json"));

        assertEquals(expected, actual);
    }

    private static String readExpected(String classpathRelative) throws Exception {
        URL url = DifferTest.class.getClassLoader().getResource(classpathRelative);
        if (url == null) {
            throw new IllegalStateException("Missing expected resource: " + classpathRelative);
        }
        return Files.readString(Path.of(url.toURI()), StandardCharsets.UTF_8)
                .replace("\r\n", "\n")
                .stripTrailing();
    }

    private static String resourcePath(String classpathRelative) throws Exception {
        URL url = DifferTest.class.getClassLoader().getResource(classpathRelative);
        if (url == null) {
            throw new IllegalStateException("Missing test resource: " + classpathRelative);
        }
        return Path.of(url.toURI()).toString();
    }
}
