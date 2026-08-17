package parser.mermaid;

import enums.ActivityNodeType;
import enums.DiagramType;
import enums.Language;
import model.diagrams.ActivityDiagram;
import model.diagrams.StateDiagram;
import model.diagrams.UmlDiagram;
import model.elements.ActivityNode;
import parser.DiagramParser;
import parser.state_diagram.StateDiagramParser;

import java.util.List;
import java.util.regex.Pattern;

public class MermaidParser implements DiagramParser {

    @Override
    public UmlDiagram parseDiagram(List<String> lines, Language language, DiagramType type) {
        UmlDiagram umlDiagram = new UmlDiagram();

        if (language.equals(Language.MERMAID)) {
            switch (type) {
                case STATE:
                    return parseStateDiagram(lines, language, type);
                case ACTIVITY:
                    return parseActivityDiagram(lines, language, type);
                case USECASE:
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

    // Metoda pentru interpretarea diagramei de activitati in limbajul Mermaid

    public ActivityDiagram parseActivityDiagram(List<String> lines, Language language, DiagramType type) {
        ActivityDiagram activityDiagram = new ActivityDiagram();
        activityDiagram.setLanguage(language);
        activityDiagram.setType(type);
        activityDiagram.setLinesCount(lines.size());

        String endPattern = "end";
        String activityPattern = "^[A-Za-z0-9]+(\\([\\s\\S]+\\) | \\[[\\s\\S]+\\])$";
        String conditionalPattern = "^[A-Za-z0-9]+\\{[\\s\\S]+\\}$";
        String swimlanePattern = "^subgraph\\s+[\\S]+(\\[[\\s\\S]+\\])?$";
        List <String> nameStart = List.of("(", "[");
        String swimlaneStart = "subgraph";

        final int CAPACITY = 50;
        StringBuilder currentSwimlane = new StringBuilder(CAPACITY);

        for (String line : lines) {
            if (!line.isBlank()) {
                String trimmedLine = line.trim();

                // Identifica o activitate

                if (Pattern.matches(activityPattern, trimmedLine)) {
                    int index = trimmedLine.contains(nameStart.get(0)) ? trimmedLine.indexOf(nameStart.get(0)) : trimmedLine.indexOf(nameStart.get(1));
                    String activityAlias = trimmedLine.substring(0, index).trim();
                    String activityName = getActivityName(trimmedLine.substring(index + 1));
                    ActivityNodeType activityType = getActivityType(activityName);
                    ActivityNode activityNode = new ActivityNode(activityName, activityAlias, activityType, currentSwimlane.toString());
                    activityDiagram.addActivity(activityNode);
                }

                if (Pattern.matches(conditionalPattern, trimmedLine)) {
                    ActivityNode conditionalNode = new ActivityNode("Condition", ActivityNodeType.CONDITIONAL);
                    activityDiagram.addActivity(conditionalNode);
                }

                if (Pattern.matches(swimlanePattern, trimmedLine)) {
                   int index = trimmedLine.contains(nameStart.get(1)) ? trimmedLine.indexOf(nameStart.get(1)) : trimmedLine.length();
                   String swimlaneName = trimmedLine.substring(swimlaneStart.length(), index).trim();
                   currentSwimlane.append(swimlaneName);
                   activityDiagram.getSwimlanes().add(swimlaneName);
                }

                if (Pattern.matches(endPattern, trimmedLine)) {
                    currentSwimlane.delete(0, currentSwimlane.length());
                }
            }
        }

        return activityDiagram;
    }

    private String getActivityName(String name) {
        return name.replace("(", "").replace(")", "").replace("[", "").replace("]", "").trim();
    }

    private ActivityNodeType getActivityType(String name) {
        if (name.equalsIgnoreCase("start")) {
            return ActivityNodeType.START;
        }
        if (name.equalsIgnoreCase("end")) {
            return ActivityNodeType.STOP;
        }
        return ActivityNodeType.ACTIVITY;
    }
}