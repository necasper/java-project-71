package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Help.Visibility;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.Map;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.",
        customSynopsis = "gendiff [-hV] [-f=format] filepath1 filepath2",
        parameterListHeading = "",
        optionListHeading = "")
public class App implements Callable<Integer> {

    @Parameters(index = "0", paramLabel = "filepath1", description = "path to first file")
    private String filepath1;

    @Parameters(index = "1", paramLabel = "filepath2", description = "path to second file")
    private String filepath2;

    @Option(names = {"-f", "--format"}, paramLabel = "format",
            description = "output format: stylish, plain [default: stylish]",
            defaultValue = "stylish",
            showDefaultValue = Visibility.NEVER)
    private String format;

    @Override
    public Integer call() throws Exception {
        Map<String, Object> data1 = Parser.getData(Parser.readFile(filepath1), filepath1);
        Map<String, Object> data2 = Parser.getData(Parser.readFile(filepath2), filepath2);
        String diff = Differ.generate(data1, data2, format);
        System.out.println(diff);
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
