package hexlet.code;

import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlainNestedDiffTest {

    @Test
    void plainOutputMatchesSpecForNestedValues() throws Exception {
        Map<String, Object> data1 = loadMap("nested_file1.json");
        Map<String, Object> data2 = loadMap("nested_file2.json");

        String actual = Differ.generate(data1, data2, "plain");

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
    void generateWithFilePathsDelegatesToParser() throws Exception {
        URL url1 = PlainNestedDiffTest.class.getClassLoader().getResource("nested_file1.json");
        URL url2 = PlainNestedDiffTest.class.getClassLoader().getResource("nested_file2.json");
        if (url1 == null || url2 == null) {
            throw new IllegalStateException("Missing nested fixtures");
        }
        String path1 = Path.of(url1.toURI()).toString();
        String path2 = Path.of(url2.toURI()).toString();

        String fromPaths = Differ.generate(path1, path2, "plain");
        String fromMaps = Differ.generate(loadMap("nested_file1.json"), loadMap("nested_file2.json"), "plain");

        assertEquals(fromMaps, fromPaths);
    }

    private static Map<String, Object> loadMap(String resourceName) throws Exception {
        URL url = PlainNestedDiffTest.class.getClassLoader().getResource(resourceName);
        if (url == null) {
            throw new IllegalStateException("Missing test resource: " + resourceName);
        }
        String path = Path.of(url.toURI()).toString();
        return Parser.getData(Parser.readFile(path), resourceName);
    }
}
