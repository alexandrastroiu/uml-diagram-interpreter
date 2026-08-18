package parser.mermaid;

import enums.ActivityNodeType;
import enums.DiagramType;
import enums.Language;
import model.diagrams.ActivityDiagram;
import model.elements.ActivityNode;

import java.util.List;
import java.util.regex.Pattern;

public class MermaidActivityParser {

    private static final String END_PATTERN = "end";
    private static final String ACTIVITY_PATTERN = "^[A-Za-z0-9]+(\\([\\s\\S]+\\) | \\[[\\s\\S]+\\])$";
    private static final String CONDITIONAL_PATTERN = "^[A-Za-z0-9]+\\{[\\s\\S]+\\}$";
    private static final String SWIMLANE_PATTERN = "^subgraph\\s+[\\S]+(\\[[\\s\\S]+\\])?$";
    private static final List<String> NAME_START = List.of("(", "[");
    private static final String SWIMLANE_START = "subgraph";
    private static final int CAPACITY = 50;

    // Metoda pentru interpretarea diagramei de activitati in limbajul Mermaid

    public static ActivityDiagram parseActivityDiagram(List<String> lines, Language language, DiagramType type) {
        ActivityDiagram activityDiagram = new ActivityDiagram();
        activityDiagram.setLanguage(language);
        activityDiagram.setType(type);
        activityDiagram.setLinesCount(lines.size());

        StringBuilder currentSwimlane = new StringBuilder(CAPACITY);

        for (String line : lines) {
            if (!line.isBlank()) {
                String trimmedLine = line.trim();

                // Identifica o activitate

                if (Pattern.matches(ACTIVITY_PATTERN, trimmedLine)) {
                    int index = trimmedLine.contains(NAME_START.get(0)) ? trimmedLine.indexOf(NAME_START.get(0)) : trimmedLine.indexOf(NAME_START.get(1));
                    String activityAlias = trimmedLine.substring(0, index).trim();
                    String activityName = MermaidParser.getMermaidElementName(trimmedLine.substring(index + 1));
                    ActivityNodeType activityType = getActivityType(activityName);
                    ActivityNode activityNode = new ActivityNode(activityName, activityAlias, activityType, currentSwimlane.toString());
                    activityDiagram.addActivity(activityNode);
                }

                if (Pattern.matches(CONDITIONAL_PATTERN, trimmedLine)) {
                    ActivityNode conditionalNode = new ActivityNode("Condition", ActivityNodeType.CONDITIONAL);
                    activityDiagram.addActivity(conditionalNode);
                }

                if (Pattern.matches(SWIMLANE_PATTERN, trimmedLine)) {
                    int index = trimmedLine.contains(NAME_START.get(1)) ? trimmedLine.indexOf(NAME_START.get(1)) : trimmedLine.length();
                    String swimlaneName = trimmedLine.substring(SWIMLANE_START.length(), index).trim();
                    currentSwimlane.append(swimlaneName);
                    activityDiagram.getSwimlanes().add(swimlaneName);
                }

                if (Pattern.matches(END_PATTERN, trimmedLine)) {
                    currentSwimlane.delete(0, currentSwimlane.length());
                }
            }
        }

        activityDiagram.setElements(activityDiagram.countElements());
        activityDiagram.setActivitiesCount(activityDiagram.getElements());
        activityDiagram.setSwimlanesCount(activityDiagram.getSwimlanes().size());
        activityDiagram.setConditionalNodes(activityDiagram.countNodes(ActivityNodeType.CONDITIONAL));

        return activityDiagram;
    }

    private static ActivityNodeType getActivityType(String name) {
        if (name.equalsIgnoreCase("start")) {
            return ActivityNodeType.START;
        }
        if (name.equalsIgnoreCase("end")) {
            return ActivityNodeType.STOP;
        }

        return ActivityNodeType.ACTIVITY;
    }
}