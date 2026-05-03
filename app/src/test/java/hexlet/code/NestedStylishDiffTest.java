package hexlet.code;

import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class NestedStylishDiffTest {

    @Test
    void stylishOutputMatchesSpecForNestedValues() throws Exception {
        Map<String, Object> data1 = loadMap("nested_file1.json");
        Map<String, Object> data2 = loadMap("nested_file2.json");

        String actual = Differ.generate(data1, data2, "stylish");

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
    void diffBuilderDescribesEachKeyOnce() throws Exception {
        Map<String, Object> data1 = loadMap("nested_file1.json");
        Map<String, Object> data2 = loadMap("nested_file2.json");

        List<DiffEntry> entries = DiffBuilder.build(data1, data2);

        assertInstanceOf(DiffEntry.Unchanged.class, entries.stream()
                .filter(e -> e instanceof DiffEntry.Unchanged u && "chars1".equals(u.key()))
                .findFirst()
                .orElseThrow());
        assertInstanceOf(DiffEntry.Changed.class, entries.stream()
                .filter(e -> e instanceof DiffEntry.Changed c && "setting1".equals(c.key()))
                .findFirst()
                .orElseThrow());
        assertInstanceOf(DiffEntry.Removed.class, entries.stream()
                .filter(e -> e instanceof DiffEntry.Removed r && "key1".equals(r.key()))
                .findFirst()
                .orElseThrow());
        assertInstanceOf(DiffEntry.Added.class, entries.stream()
                .filter(e -> e instanceof DiffEntry.Added a && "obj1".equals(a.key()))
                .findFirst()
                .orElseThrow());
    }

    private static Map<String, Object> loadMap(String resourceName) throws Exception {
        URL url = NestedStylishDiffTest.class.getClassLoader().getResource(resourceName);
        if (url == null) {
            throw new IllegalStateException("Missing test resource: " + resourceName);
        }
        String path = Path.of(url.toURI()).toString();
        return Parser.getData(Parser.readFile(path), resourceName);
    }
}
