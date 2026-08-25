package parser.mermaid;

import enums.ActivityNodeType;
import enums.DiagramType;
import enums.Language;
import model.diagrams.ActivityDiagram;
import model.elements.ActivityNode;
import model.relationships.ControlFlow;

import java.util.List;
import java.util.regex.Pattern;

public class MermaidActivityParser {

    private static final String END_PATTERN = "end";
    private static final String ACTIVITY_PATTERN = "^[A-Za-z0-9]+(\\([\\s\\S]+\\)|\\[[\\s\\S]+\\])$";
    private static final String CONDITIONAL_PATTERN = "^[A-Za-z0-9]+\\{[\\s\\S]+\\}$";
    private static final String SWIMLANE_PATTERN = "^subgraph\\s+[\\S]+(\\[[\\s\\S]+\\])?$";
    private static final String CONTROL_FLOW_PATTERN = "^[\\s\\S]+\\s*-[\\s\\S]*->\\s*[\\s\\S]+$";
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

               checkActivity(activityDiagram, trimmedLine, currentSwimlane);

                // Identifica o conditie

               checkCondition(activityDiagram, trimmedLine, currentSwimlane);
               checkSwimlaneStart(activityDiagram, trimmedLine, currentSwimlane);
               checkSwimlaneEnd(trimmedLine, currentSwimlane);
            }
        }

        for (String line : lines) {
            if (!line.isBlank()) {
                String trimmedLine = line.trim();

                // Identifica relatiile dintre elemente

                checkControlFlow(activityDiagram, trimmedLine, currentSwimlane);
                checkSwimlaneStart(activityDiagram, trimmedLine, currentSwimlane);
                checkSwimlaneEnd(trimmedLine, currentSwimlane);
            }
        }

        activityDiagram.addAllActivities(activityDiagram.getActivityLookup());
        activityDiagram.setElements(activityDiagram.countElements());
        activityDiagram.setRelationships(activityDiagram.getControlFlows().size());
        activityDiagram.setActivitiesCount(activityDiagram.getElements());
        activityDiagram.setSwimlanesCount(activityDiagram.getSwimlanes().size());
        activityDiagram.setConditionalNodesCount(activityDiagram.countNodes(ActivityNodeType.CONDITIONAL));

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

    private static boolean isActivity(String line) {
        return Pattern.matches(ACTIVITY_PATTERN, line);
    }

    private static boolean isCondition(String line) {
        return Pattern.matches(CONDITIONAL_PATTERN, line);
    }

    private static boolean isSwimlaneStart(String line) {
        return Pattern.matches(SWIMLANE_PATTERN, line);
    }

    private static boolean isSwimlaneEnd(String line) {
        return Pattern.matches(END_PATTERN, line);
    }

    private static boolean isControlFlow(String line) {
        return Pattern.matches(CONTROL_FLOW_PATTERN, line);
    }

    private static void checkActivity(ActivityDiagram activityDiagram, String trimmedLine, StringBuilder currentSwimlane) {
        if (isActivity(trimmedLine)) {
            int index = trimmedLine.contains(NAME_START.get(0)) ? trimmedLine.indexOf(NAME_START.get(0)) : trimmedLine.indexOf(NAME_START.get(1));
            String activityAlias = trimmedLine.substring(0, index).trim();
            String activityName = MermaidParser.getMermaidElementName(trimmedLine.substring(index + 1));
            ActivityNodeType activityType = getActivityType(activityName);

            if (activityType.equals(ActivityNodeType.START)) {
                activityDiagram.setInitialStatesCount(activityDiagram.getInitialStatesCount() + 1);
                activityName = activityName + activityDiagram.getInitialStatesCount();
            }
            else if (activityType.equals(ActivityNodeType.STOP)) {
                activityDiagram.setFinalStatesCount(activityDiagram.getFinalStatesCount() + 1);
                activityName = activityName + activityDiagram.getFinalStatesCount();
            }

            ActivityNode activityNode = new ActivityNode(activityName, activityAlias, activityType, currentSwimlane.toString());
            activityDiagram.addActivity(activityNode);
        }
    }

    private static void checkCondition(ActivityDiagram activityDiagram, String trimmedLine, StringBuilder currentSwimlane) {
        if (isCondition(trimmedLine)) {
            int indexStart = trimmedLine.indexOf("{");
            int indexEnd = trimmedLine.indexOf("}");
            String alias = trimmedLine.substring(0, indexStart);
            String condition = trimmedLine.substring(indexStart + 1, indexEnd).replace("?", "").trim();
            ActivityNode conditionalNode = new ActivityNode(condition, alias, ActivityNodeType.CONDITIONAL, currentSwimlane.toString());
            activityDiagram.addActivity(conditionalNode);
        }
    }

    private static void addControlFlowElement(ActivityDiagram activityDiagram, String element, StringBuilder currentSwimlane) {
        checkActivity(activityDiagram, element, currentSwimlane);
        checkCondition(activityDiagram, element, currentSwimlane);
        if (!isActivity(element) && !isCondition(element)) {
            ActivityNodeType activityType = getActivityType(element);
            activityDiagram.addActivity(new ActivityNode(element, activityType));
        }
    }

    public static void checkControlFlow(ActivityDiagram activityDiagram, String trimmedLine, StringBuilder currentSwimlane) {
        if (isControlFlow(trimmedLine)) {
            ControlFlow flow = new ControlFlow();
            int indexStart = trimmedLine.indexOf("-");
            int indexEnd = trimmedLine.indexOf(">");
            String element1 = trimmedLine.substring(0, indexStart).trim();
            String element2 = trimmedLine.substring(indexEnd + 1).trim();
            element2 = element2.contains("|") ? element2.substring(element2.lastIndexOf("|") + 1).trim() : element2;
            addControlFlowElement(activityDiagram, element1, currentSwimlane);
            addControlFlowElement(activityDiagram, element2, currentSwimlane);
            flow.setStart(activityDiagram.findActivity(element1));
            flow.setEnd(activityDiagram.findActivity(element2));
            activityDiagram.getControlFlows().add(flow);
        }
    }

    private static void checkSwimlaneStart(ActivityDiagram activityDiagram, String trimmedLine, StringBuilder currentSwimlane) {
        if (isSwimlaneStart(trimmedLine)) {
            int index = trimmedLine.contains(NAME_START.get(1)) ? trimmedLine.indexOf(NAME_START.get(1)) : trimmedLine.length();
            String swimlaneName = trimmedLine.substring(SWIMLANE_START.length(), index).trim();
            currentSwimlane.append(swimlaneName);
            activityDiagram.getSwimlanes().add(swimlaneName);
        }
    }

    private static void checkSwimlaneEnd(String trimmedLine, StringBuilder currentSwimlane) {
        if (isSwimlaneEnd(trimmedLine)) {
            currentSwimlane.delete(0, currentSwimlane.length());
        }
    }
}