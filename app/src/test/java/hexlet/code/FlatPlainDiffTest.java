package hexlet.code;

import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlatPlainDiffTest {

    @Test
    void plainSkipsUnchangedKeys() throws Exception {
        Map<String, Object> data1 = loadMap("flat1.json");
        Map<String, Object> data2 = loadMap("flat2.json");

        String actual = Differ.generate(data1, data2, "plain");

        String expected = """
                Property 'follow' was removed
                Property 'proxy' was removed
                Property 'timeout' was updated. From 50 to 20
                Property 'verbose' was added with value: true""";

        assertEquals(expected, actual);
    }

    private static Map<String, Object> loadMap(String resourceName) throws Exception {
        URL url = FlatPlainDiffTest.class.getClassLoader().getResource(resourceName);
        if (url == null) {
            throw new IllegalStateException("Missing test resource: " + resourceName);
        }
        String path = Path.of(url.toURI()).toString();
        return Parser.getData(Parser.readFile(path), resourceName);
    }
}
