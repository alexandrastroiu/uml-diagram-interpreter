package parser;

import enums.DiagramType;
import enums.Language;
import model.diagrams.UmlDiagram;

import java.util.List;

public interface DiagramParser {

    public UmlDiagram parseDiagram(List<String> lines, Language language, DiagramType type);
}
