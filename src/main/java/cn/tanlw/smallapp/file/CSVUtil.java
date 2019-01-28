package cn.tanlw.smallapp.file;

import au.com.bytecode.opencsv.CSVWriter;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVUtil {
    @Test
    public void write() throws IOException {
        File tempFile = File.createTempFile("prefix", ".test");
        tempFile.deleteOnExit();
        CSVWriter csvWriter = new CSVWriter(new FileWriter(tempFile));
        int i = 0;
        while (i < 10){
            csvWriter.writeNext("line:"+(++i));
        }
        csvWriter.close();
    }
}
