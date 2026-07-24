package parser.plantuml;

import enums.DiagramType;
import enums.Language;
import model.diagrams.UmlDiagram;
import parser.DiagramParser;

import java.util.List;

public class PlantumlParser implements DiagramParser {

    @Override
    public UmlDiagram parseDiagram(List<String> lines, Language language, DiagramType type) {
        if (language.equals(Language.PLANTUML)) {
            switch (type) {
                case STATE:
                    for (String line : lines) {
                        if (!line.isBlank()) {
                            String trimmedLine = line.trim();

                        }
                    }
                    break;
                case ACTIVITY:
                    break;
                case USECASE:
                    break;
                case UNKOWN:
                    break;
                default:
            }
        }
    }
}
