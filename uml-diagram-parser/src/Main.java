import detector.DiagramDetector;
import detector.LanguageDetector;
import enums.DiagramType;
import enums.Language;
import io.FileReader;
import model.diagrams.StateDiagram;
import parser.plantuml.PlantumlParser;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        FileReader fileReader = new FileReader();
        LanguageDetector languageDetector = new LanguageDetector();
        DiagramDetector diagramDetector = new DiagramDetector();
        PlantumlParser plantumlParser = new PlantumlParser();

        List<String> lines = fileReader.readFileIntoList("test.txt");
        System.out.println("Limbaj: " + languageDetector.detectDiagramLanguage(lines));
        Language lang = languageDetector.detectDiagramLanguage(lines);
        System.out.println("Tipul Diagramei: " + diagramDetector.detectDiagramType(lines, languageDetector.detectDiagramLanguage(lines)));
        DiagramType type = diagramDetector.detectDiagramType(lines, languageDetector.detectDiagramLanguage(lines));
        System.out.println();

        // Test parser - Diagrama de stare

        StateDiagram stateDiagram = (StateDiagram) plantumlParser.parseDiagram(lines, lang, type);
        System.out.println("Stari:");
        stateDiagram.printStates();
        System.out.println();
        System.out.println("Numarul de elemente: ");
        System.out.println(stateDiagram.getElements());
        System.out.println();
        System.out.println("Tranzitii:");
        stateDiagram.printTransitions();
        System.out.println();
        System.out.println("Numarul de tranzitii: ");
        System.out.println(stateDiagram.getRelationships());
        System.out.println();
        System.out.println("Linii de Cod:");
        System.out.println(stateDiagram.getLinesCount());
    }
}
