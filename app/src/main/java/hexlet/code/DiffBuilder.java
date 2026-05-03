package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

public final class DiffBuilder {

    private DiffBuilder() {
    }

    public static List<DiffEntry> build(Map<String, Object> map1, Map<String, Object> map2) {
        TreeSet<String> keys = new TreeSet<>();
        keys.addAll(map1.keySet());
        keys.addAll(map2.keySet());

        List<DiffEntry> result = new ArrayList<>();
        for (String key : keys) {
            boolean in1 = map1.containsKey(key);
            boolean in2 = map2.containsKey(key);
            if (in1 && in2) {
                Object v1 = map1.get(key);
                Object v2 = map2.get(key);
                if (Objects.equals(v1, v2)) {
                    result.add(new DiffEntry.Unchanged(key, v1));
                } else {
                    result.add(new DiffEntry.Changed(key, v1, v2));
                }
            } else if (in1) {
                result.add(new DiffEntry.Removed(key, map1.get(key)));
            } else {
                result.add(new DiffEntry.Added(key, map2.get(key)));
            }
        }
        return result;
    }
}
