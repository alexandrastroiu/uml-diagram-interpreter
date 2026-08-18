package parser.plantuml;

import enums.*;
import model.diagrams.UmlDiagram;
import parser.DiagramParser;
import parser.state_diagram.StateDiagramParser;

import java.util.List;

public class PlantumlParser implements DiagramParser {

    @Override
    public UmlDiagram parseDiagram(List<String> lines, Language language, DiagramType type) {
        UmlDiagram umlDiagram = new UmlDiagram();

        if (language.equals(Language.PLANTUML)) {
            switch (type) {
                case STATE:
                    return StateDiagramParser.parseStateDiagram(lines, language, type);
                case ACTIVITY:
                    return PlantUmlActivityParser.parseActivityDiagram(lines, language, type);
                case USECASE:
                    return PlantUmlUseCaseParser.parseUseCaseDiagram(lines, language, type);
                default:
                    return umlDiagram;
            }
        }
        return umlDiagram;
    }
}