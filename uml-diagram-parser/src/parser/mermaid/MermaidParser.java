package parser.mermaid;

import enums.*;
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
                    return StateDiagramParser.parseStateDiagram(lines, language, type);
                case ACTIVITY:
                    return MermaidActivityParser.parseActivityDiagram(lines, language, type);
                case USECASE:
                    return MermaidUseCaseParser.parseUseCaseDiagram(lines, language, type);
                default:
                    return umlDiagram;
            }
        }
        return umlDiagram;
    }

    public static String getMermaidElementName(String name) {
        return name.replace("(", "").replace(")", "").replace("[", "").replace("]", "").replace("\"", "").replace("/", "").replace("\\", "").replace("-", "").replace("|", "").trim();
    }
}