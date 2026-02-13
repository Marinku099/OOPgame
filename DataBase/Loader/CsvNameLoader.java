package DataBase.Loader;

import java.util.ArrayList;
import java.util.List;

public class CsvNameLoader extends CsvLoader<List<String>> implements NameLoader {

    public CsvNameLoader(String filePath) {
        super(filePath);
    }

    @Override
    public List<String> loadNames() {
        return loadFromFile();
    }

    @Override
    protected List<String> initResult() {
        return new ArrayList<>();
    }

    @Override
    protected void processLine(String line, List<String> result) {
        String[] row = line.split(",");
        if (row.length > 0) {
            result.add(row[0].trim());
        }
    }
}