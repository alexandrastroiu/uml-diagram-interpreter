package io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class FileReader {

    // Default Constructor

    public FileReader() {}

    public List<String> readFileIntoList(String fileName) {
        List<String> lines = Collections.emptyList();
        Path filePath = Paths.get(fileName);

        try {
            lines = Files.readAllLines(filePath);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

}
