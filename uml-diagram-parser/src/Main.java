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
        FileReader f = new FileReader();
        LanguageDetector l = new LanguageDetector();
        DiagramDetector d = new DiagramDetector();
        PlantumlParser p = new PlantumlParser();

        // Test
        List<String> lines = f.readFileIntoList("test.txt");
        System.out.println("Limbaj: " + l.detectDiagramLanguage(lines));
        Language lang = l.detectDiagramLanguage(lines);
        System.out.println("Tipul Diagramei: " + d.detectDiagramType(lines, l.detectDiagramLanguage(lines)));
        DiagramType type = d.detectDiagramType(lines, l.detectDiagramLanguage(lines));
        System.out.println();

        // Test parser
        StateDiagram stateDiagram = (StateDiagram)p.parseDiagram(lines, lang, type);
        System.out.println("Stari:");
        stateDiagram.printStates();
        System.out.println("Numarul de elemente: ");
        System.out.println(stateDiagram.getElements());
        System.out.println();
        System.out.println("Tranzitii:");
        stateDiagram.printTransitions();
        System.out.println("Numarul de tranzitii: ");
        System.out.println(stateDiagram.getRelationships());
        System.out.println();
        System.out.println("Linii de Cod:");
        System.out.println(stateDiagram.getLinesCount());
    }
}
