import detector.DiagramDetector;
import detector.LanguageDetector;
import io.FileReader;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        FileReader f = new FileReader();
        LanguageDetector l = new LanguageDetector();
        DiagramDetector d = new DiagramDetector();

        // Test
        List<String> lines = f.readFileIntoList("test_diagram.txt");
        System.out.println("Diagram Language: " + l.detectDiagramLanguage(lines));
        System.out.println("Diagram Type: " + d.detectDiagramType(lines, l.detectDiagramLanguage(lines)));
    }
}
