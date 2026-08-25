package parser.plantuml;

import enums.ActivityNodeType;
import enums.DiagramType;
import enums.Language;
import model.diagrams.ActivityDiagram;
import model.elements.ActivityNode;

import java.util.List;
import java.util.regex.Pattern;

public class PlantUmlActivityParser {

    public static final String START_PATTERN = "start";
    public static final List<String> END_PATTERN = List.of("stop", "end");
    private static final String CONDITIONAL_PATTERN = "^[\\s\\S]*(if|elseif)[\\s\\S]+then[\\s\\S]+$";
    private static final String SWITCH_PATTERN = "case\\s+([\\s\\S]+)\\s*";
    private static final String LABEL_START = "^\\s*:.+$";
    private static final String LABEL_END = ";";
    private static final String FORK_PATTERN = "^fork[\\s\\S]*$";
    private static final String MERGE_PATTERN = "^end\\s+merge\\s*$";
    private static final String SWIMLANE_PATTERN = "^\\|[\\s\\S]+\\|$";
    private static final String GROUP_PATTERN = "^(group|partition|package|rectangle|card)[\\s\\S]+\\{?\\s*$";
    private static final String GROUP_END = "end group";
    private static final String PARTITION_END = "}";
    private static final int CAPACITY = 50;

    // Metoda pentru interpretarea diagramei de activitati in limbajul PlantUML

    public static ActivityDiagram parseActivityDiagram(List<String> lines, Language language, DiagramType type) {
        ActivityDiagram activityDiagram = new ActivityDiagram();
        activityDiagram.setLanguage(language);
        activityDiagram.setType(type);
        activityDiagram.setLinesCount(lines.size());

        StringBuilder currentActivityLabel = new StringBuilder(CAPACITY);
        StringBuilder currentSwimlane = new StringBuilder(CAPACITY);
        StringBuilder currentGroup = new StringBuilder(CAPACITY);
        boolean readingActivityLabel = false;

        for (String line : lines) {
            if (!line.isBlank()) {
                String trimmedLine = line.trim();

                checkStart(activityDiagram, trimmedLine, currentSwimlane, currentGroup);
                checkEnd(activityDiagram, trimmedLine, currentSwimlane, currentGroup);
                checkFork(activityDiagram, trimmedLine, currentSwimlane, currentGroup);
                checkCondition(activityDiagram, trimmedLine, currentSwimlane, currentGroup);
                checkMerge(activityDiagram, trimmedLine, currentSwimlane, currentGroup);

                // Identifica o activitate

                if (isLabelStart(trimmedLine)) {
                    if (!currentActivityLabel.isEmpty()) {
                        currentActivityLabel.delete(0, currentActivityLabel.length());
                    }
                    readingActivityLabel = true;
                }

                if (readingActivityLabel) {
                    currentActivityLabel.append(trimmedLine);
                }

                if (isLabelEnd(trimmedLine)) {
                    readingActivityLabel = false;
                    currentActivityLabel.deleteCharAt(0);
                    currentActivityLabel.deleteCharAt(currentActivityLabel.length() - 1);
                    String activityName = currentActivityLabel.toString();
                    ActivityNode activityNode = new ActivityNode(activityName, ActivityNodeType.ACTIVITY, currentSwimlane.toString(), currentGroup.toString());
                    activityDiagram.addActivity(activityNode);
                }

                checkSwimlane(activityDiagram, trimmedLine, currentSwimlane);
                checkGroup(activityDiagram, trimmedLine, currentGroup);
                checkGroupEnd(trimmedLine, currentGroup);
            }
        }

        activityDiagram.addAllActivities(activityDiagram.getActivityLookup());
        activityDiagram.setElements(activityDiagram.countElements());
        activityDiagram.setActivitiesCount(activityDiagram.getElements());
        activityDiagram.setSwimlanesCount(activityDiagram.getSwimlanes().size());
        activityDiagram.setConditionalNodesCount(activityDiagram.countNodes(ActivityNodeType.CONDITIONAL));

        return activityDiagram;
    }

    private static boolean isSwimlane(String line) {
        return Pattern.matches(SWIMLANE_PATTERN, line);
    }

    private static boolean isGroup(String line) {
        return Pattern.matches(GROUP_PATTERN, line);
    }

    private static boolean isSwitchPattern(String line) {
        return Pattern.matches(SWITCH_PATTERN, line);
    }

    private static boolean isCondition(String line) {
        return Pattern.matches(CONDITIONAL_PATTERN, line);
    }

    private static boolean isFork(String line) {
        return Pattern.matches(FORK_PATTERN, line);
    }

    private static boolean isMerge(String line) {
        return Pattern.matches(MERGE_PATTERN, line);
    }

    private static boolean isLabelStart(String line) {
        return Pattern.matches(LABEL_START, line);
    }

    private static boolean isLabelEnd(String line) {
        return line.endsWith(LABEL_END);
    }

    private static String getCondition(String line) {
        String condition = "";
        int indexStart;

        if (isSwitchPattern(line)) {
            indexStart = line.indexOf("(");
            condition = line.substring(indexStart + 1).trim();
        }
        else if (isCondition(line)) {
            indexStart = line.contains("if") ? line.indexOf("if") + "if".length() : line.indexOf("elseif") + "elseif".length();
            int indexEnd = line.indexOf("then");
            condition = line.substring(indexStart, indexEnd).replace("?", "").replace("(", "").replace(")", "").trim();
        }

        return condition;
    }

    private static void checkStart(ActivityDiagram activityDiagram, String trimmedLine, StringBuilder currentSwimlane, StringBuilder currentGroup) {
        if (trimmedLine.equals(START_PATTERN)) {
            activityDiagram.setInitialStatesCount(activityDiagram.getInitialStatesCount() + 1);
            ActivityNode startNode = new ActivityNode("Start" + activityDiagram.getInitialStatesCount(), ActivityNodeType.START, currentSwimlane.toString(), currentGroup.toString());
            activityDiagram.addActivity(startNode);
        }
    }

    private static void checkEnd(ActivityDiagram activityDiagram, String trimmedLine, StringBuilder currentSwimlane, StringBuilder currentGroup) {
        if (trimmedLine.equals(END_PATTERN.get(0)) || trimmedLine.equals(END_PATTERN.get(1))) {
            activityDiagram.setFinalStatesCount(activityDiagram.getFinalStatesCount() + 1);
            ActivityNode endNode = new ActivityNode("Stop" + activityDiagram.getFinalStatesCount(), ActivityNodeType.STOP, currentSwimlane.toString(), currentGroup.toString());
            activityDiagram.addActivity(endNode);
        }
    }

    private static void checkFork(ActivityDiagram activityDiagram, String trimmedLine, StringBuilder currentSwimlane, StringBuilder currentGroup) {
        if (isFork(trimmedLine)) {
            activityDiagram.setForkCount(activityDiagram.getForkCount() + 1);
            ActivityNode forkNode = new ActivityNode("Fork" + activityDiagram.getForkCount(), ActivityNodeType.FORK, currentSwimlane.toString(), currentGroup.toString());
            activityDiagram.addActivity(forkNode);
        }
    }

    private static void checkCondition(ActivityDiagram activityDiagram, String trimmedLine, StringBuilder currentSwimlane, StringBuilder currentGroup) {
        if (isSwitchPattern(trimmedLine) || isCondition(trimmedLine)) {
            String condition = getCondition(trimmedLine);
            ActivityNode conditionalNode = new ActivityNode(condition, ActivityNodeType.CONDITIONAL, currentSwimlane.toString(), currentGroup.toString());
            activityDiagram.addActivity(conditionalNode);
        }
    }

    private static void checkMerge(ActivityDiagram activityDiagram, String trimmedLine, StringBuilder currentSwimlane, StringBuilder currentGroup) {
        if (isMerge(trimmedLine)) {
            activityDiagram.setMergeCount(activityDiagram.getMergeCount() + 1);
            ActivityNode mergeNode = new ActivityNode("Merge" + activityDiagram.getMergeCount(), ActivityNodeType.MERGE, currentSwimlane.toString(), currentGroup.toString());
            activityDiagram.addActivity(mergeNode);
        }
    }

    private static void checkSwimlane(ActivityDiagram activityDiagram, String trimmedLine, StringBuilder currentSwimlane) {
        if (isSwimlane(trimmedLine)) {
            if (!currentSwimlane.isEmpty()) {
                currentSwimlane.delete(0, currentSwimlane.length());
            }
            currentSwimlane.append(trimmedLine);
            currentSwimlane.deleteCharAt(0);
            currentSwimlane.deleteCharAt(currentSwimlane.length() - 1);
            String swimlaneName = currentSwimlane.toString();
            activityDiagram.getSwimlanes().add(swimlaneName);
        }
    }

    private static void checkGroup(ActivityDiagram activityDiagram, String trimmedLine, StringBuilder currentGroup) {
        if (isGroup(trimmedLine)) {
            String groupName = trimmedLine.replaceFirst("^(group|partition|package|rectangle|card)", "").replace("{", "").trim();
            currentGroup.append(groupName);
            activityDiagram.getGroups().add(currentGroup.toString());
        }
    }

    private static void checkGroupEnd(String trimmedLine, StringBuilder currentGroup) {
        if (trimmedLine.contains(GROUP_END) || trimmedLine.contains(PARTITION_END)) {
            currentGroup.delete(0, currentGroup.length());
        }
    }
}