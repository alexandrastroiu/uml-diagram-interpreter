package parser.mermaid;

import enums.DiagramType;
import enums.Language;
import model.diagrams.UmlDiagram;
import parser.DiagramParser;

import java.util.List;

public class MermaidParser implements DiagramParser {

    @Override
    public UmlDiagram parseDiagram(List<String> lines, Language language, DiagramType type) {
        return null;
    }
}
