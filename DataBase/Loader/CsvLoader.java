package DataBase.Loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class CsvLoader<T> {
    
    protected String filePath;

    public CsvLoader(String filePath) {
        this.filePath = filePath;
    }

    public T loadFromFile() {
        T result = initResult();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // br.readLine(); // (Optional: ถ้ามี Header ให้เปิดบรรทัดนี้)
            
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    processLine(line, result);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
        
        return result;
    }
    
    protected abstract T initResult(); 
    protected abstract void processLine(String line, T result);
}