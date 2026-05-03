package hexlet.code;

import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlatYamlComparisonTest {

    @Test
    void comparesTwoFlatYamlFiles() throws Exception {
        Map<String, Object> data1 = loadMap("flat1.yml");
        Map<String, Object> data2 = loadMap("flat2.yml");

        String actual = Differ.generate(data1, data2);

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
    void identicalFlatYamlProducesNoPlusMinusLines() throws Exception {
        Map<String, Object> data = loadMap("flat1.yml");
        String actual = Differ.generate(data, data);

        String expected = """
                {
                    follow: false
                    host: hexlet.io
                    proxy: 123.234.53.22
                    timeout: 50
                }""";

        assertEquals(expected, actual);
    }

    @Test
    void supportsYamlAndYmlExtensions() throws Exception {
        Map<String, Object> fromYml = loadMap("flat1.yml");
        Map<String, Object> fromYaml = loadMap("flat1.yaml");
        assertEquals(fromYml, fromYaml);
    }

    private static Map<String, Object> loadMap(String resourceName) throws Exception {
        URL url = FlatYamlComparisonTest.class.getClassLoader().getResource(resourceName);
        if (url == null) {
            throw new IllegalStateException("Missing test resource: " + resourceName);
        }
        String path = Path.of(url.toURI()).toString();
        return Parser.getData(Parser.readFile(path), resourceName);
    }
}
