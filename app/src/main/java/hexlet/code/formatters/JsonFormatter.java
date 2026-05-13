package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DiffEntry;

import java.util.List;

public final class JsonFormatter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonFormatter() {
    }

    public static String format(List<DiffEntry> diff) throws Exception {
        return MAPPER.writeValueAsString(diff);
    }
}
