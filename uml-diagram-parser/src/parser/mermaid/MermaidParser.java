package parser.mermaid;

import enums.DiagramType;
import enums.Language;
import model.diagrams.StateDiagram;
import model.diagrams.UmlDiagram;
import parser.DiagramParser;
import parser.state_diagram.StateDiagramParser;

import java.util.List;

public class MermaidParser implements DiagramParser {

    @Override
    public UmlDiagram parseDiagram(List<String> lines, Language language, DiagramType type) {
        UmlDiagram umlDiagram = new UmlDiagram();

        if (language.equals(Language.MERMAID)) {
            switch (type) {
                case STATE:
                    return parseStateDiagram(lines, language, type);
                case FLOWCHART:
                    break;
                default:
                    return umlDiagram;
            }
        }
        return umlDiagram;
    }

    // Metoda pentru interpretarea diagramei de stare in limbajul Mermaid

    public StateDiagram parseStateDiagram(List<String> lines, Language language, DiagramType type) {
        StateDiagramParser stateDiagramParser = new StateDiagramParser();
        return stateDiagramParser.parseStateDiagram(lines, language, type);
    }
}
