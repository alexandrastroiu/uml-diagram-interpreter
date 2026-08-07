package parser.plantuml;

import enums.ActivityNodeType;
import enums.DiagramType;
import enums.Language;
import enums.StateType;
import model.diagrams.ActivityDiagram;
import model.diagrams.StateDiagram;
import model.diagrams.UmlDiagram;
import model.diagrams.UseCaseDiagram;
import model.elements.ActivityNode;
import model.elements.State;
import model.relationships.Transition;
import parser.DiagramParser;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class PlantumlParser implements DiagramParser {

    @Override
    public UmlDiagram parseDiagram(List<String> lines, Language language, DiagramType type) {
        UmlDiagram umlDiagram = new UmlDiagram();

        if (language.equals(Language.PLANTUML)) {
            switch (type) {
                case STATE:
                    return parseStateDiagram(lines);
                case ACTIVITY:
                    return parseActivityDiagram(lines);
                case USECASE:
                    return parseUseCaseDiagram(lines);
                default:
                    return umlDiagram;
            }
        }
        return umlDiagram;
    }

    // Interpreteaza diagrama de stare

    public StateDiagram parseStateDiagram(List<String> lines) {
        StateDiagram stateDiagram = new StateDiagram();
        stateDiagram.setLanguage(Language.PLANTUML);
        stateDiagram.setType(DiagramType.STATE);
        stateDiagram.setLinesCount(lines.size());

        Map<String, StateType> stereotypes = Map.ofEntries(
                Map.entry("<<start>>", StateType.INITIAL),
                Map.entry("<<end>>", StateType.FINAL),
                Map.entry("<<choice>>", StateType.CHOICE),
                Map.entry("<<fork>>", StateType.FORK),
                Map.entry("<<join>>", StateType.JOIN)
        );

        String initialPattern = "[*] ";
        String finalPattern = " [*]";
        String statePattern = "state ";
        String descriptionPattern = " as ";
        String colorPattern = " #";
        String compositePattern = " {";
        String longNamePattern = "^state [\\s\\S]+ as [\\s\\S]+$";
        String transitionPattern = "^[\\s\\S]+ -[a-zA-Z0-9,#\\[\\]]*-> [\\s\\S]+$";
        String startPattern = " -";
        String endPattern = "-> ";
        String stateDescription = " :";

        for (String line : lines) {
            if (!line.isBlank()) {
                String trimmedLine = line.trim();

                // Stare

                if (trimmedLine.startsWith(statePattern)) {
                    State state = new State();
                    int index1 = statePattern.length();
                    String name = trimmedLine.substring(index1);

                    if (Pattern.matches(longNamePattern, trimmedLine)) {
                        int index2 = trimmedLine.indexOf(descriptionPattern);
                        int index3 = index2 + descriptionPattern.length();

                        String description = trimmedLine.substring(index1, index2).trim();
                        name = trimmedLine.substring(index3).trim();

                        state.setDescription(description);
                    }

                    if (trimmedLine.endsWith(compositePattern)) {
                        int index6 = name.indexOf(compositePattern);
                        name = name.substring(0, index6).trim();

                        state.setType(StateType.COMPOSITE);
                    }

                    if (trimmedLine.contains(colorPattern)) {
                        int index5 = name.indexOf(colorPattern);
                        name = name.substring(0, index5).trim();
                    }

                    for (String key : stereotypes.keySet()) {
                        if (trimmedLine.contains(key)) {
                            int index4 = name.indexOf(key);
                            name = name.substring(0, index4).trim();

                            state.setType(stereotypes.get(key));
                        }
                    }

                    state.setName(name);
                    stateDiagram.addState(state);
                }

                // Tranzitie

                if (Pattern.matches(transitionPattern, trimmedLine)) {
                    Transition transition = new Transition();
                    State startState = new State();
                    State endState = new State();
                    String name;

                    if (trimmedLine.startsWith(initialPattern)) {
                        startState.setName("Initial State");
                        startState.setType(StateType.INITIAL);
                    }
                    else {
                        int indexStart = trimmedLine.indexOf(startPattern);

                        name = trimmedLine.substring(0, indexStart).trim();
                        startState.setName(name);
                    }

                    int indexEnd = trimmedLine.indexOf(endPattern);
                    int indexStateEnd = indexEnd + endPattern.length();

                    if (trimmedLine.contains(stateDescription)) {
                        int indexDescription = trimmedLine.indexOf(stateDescription);
                        name = trimmedLine.substring(indexStateEnd, indexDescription).trim();
                        String description = trimmedLine.substring(indexDescription + stateDescription.length()).trim();

                        transition.setTransitionDescription(description);
                    }
                    else {
                        name = trimmedLine.substring(indexStateEnd).trim();
                    }

                    if (name.endsWith(finalPattern.trim())) {
                        endState.setName("Final State");
                        endState.setType(StateType.FINAL);
                    } else {
                        endState.setName(name);
                    }

                    transition.addTransitionStates(stateDiagram, startState, endState);
                    stateDiagram.addState(startState);
                    stateDiagram.addState(endState);
                    stateDiagram.getTransitions().add(transition);
                }
            }
        }

        stateDiagram.setTransitionCount(stateDiagram.getTransitions().size());
        stateDiagram.setRelationships(stateDiagram.getTransitionCount());
        stateDiagram.setForkCount(stateDiagram.countNodes(StateType.FORK));
        stateDiagram.setJoinCount(stateDiagram.countNodes(StateType.JOIN));
        stateDiagram.setChoiceStates(stateDiagram.countNodes(StateType.CHOICE));
        stateDiagram.setCompositeStates(stateDiagram.countNodes(StateType.COMPOSITE));
        stateDiagram.setElements(stateDiagram.countElements());

        return stateDiagram;
    }

    public ActivityDiagram parseActivityDiagram(List<String> lines) {
        ActivityDiagram activityDiagram = new ActivityDiagram();
        activityDiagram.setLanguage(Language.PLANTUML);
        activityDiagram.setType(DiagramType.ACTIVITY);
        activityDiagram.setLinesCount(lines.size());

        String startPattern = "start";
        List<String> endPattern = List.of("stop", "end");
        String conditionalPattern = "^[\\s\\S]*(if|elseif)[\\s\\S]+then[\\s\\S]+$";
        String switchPattern = "case\\s+([\\s\\S]+)\\s*";
        String labelStart = "^\\s*:.+$";
        String labelEnd = ";";
        String forkPattern = "^fork[\\s\\S]*$";
        String mergePattern = "^end\\s+merge\\s*$";
        String swimlanePattern = "^\\|[\\s\\S]+\\|$";
        String groupPattern = "^(group|partition|package|rectangle|card)[\\s\\S]+\\{?\\s*$";
        String groupEnd = "end group";
        String partitionEnd = "}";

        final int CAPACITY = 50;
        StringBuilder currentActivityLabel = new StringBuilder(CAPACITY);
        StringBuilder currentSwimlane = new StringBuilder(CAPACITY);
        StringBuilder currentGroup = new StringBuilder(CAPACITY);
        boolean readingActivityLabel = false;

        //TODO
        for (String line : lines) {
            if (!line.isBlank()) {
                String trimmedLine = line.trim();

                if (trimmedLine.equals(startPattern)) {
                    ActivityNode startNode = new ActivityNode("Start", ActivityNodeType.START, currentSwimlane.toString(), currentGroup.toString());
                    activityDiagram.getActivities().add(startNode);
                }

                if (trimmedLine.equals(endPattern.get(0)) || trimmedLine.equals(endPattern.get(1))) {
                    ActivityNode endNode = new ActivityNode("Stop", ActivityNodeType.STOP, currentSwimlane.toString(), currentGroup.toString());
                    activityDiagram.getActivities().add(endNode);
                }

                if (Pattern.matches(forkPattern, trimmedLine)) {
                    ActivityNode forkNode = new ActivityNode("Fork", ActivityNodeType.FORK, currentSwimlane.toString(), currentGroup.toString());
                    activityDiagram.getActivities().add(forkNode);
                }

                if (Pattern.matches(switchPattern, trimmedLine) || Pattern.matches(conditionalPattern, trimmedLine)) {
                    ActivityNode conditionalNode = new ActivityNode("Condition", ActivityNodeType.CONDITIONAL, currentSwimlane.toString(), currentGroup.toString());
                    activityDiagram.getActivities().add(conditionalNode);
                }

                if (Pattern.matches(mergePattern, trimmedLine)) {
                    ActivityNode mergeNode = new ActivityNode("Merge", ActivityNodeType.MERGE, currentSwimlane.toString(), currentGroup.toString());
                    activityDiagram.getActivities().add(mergeNode);
                }

                if (Pattern.matches(labelStart, trimmedLine)) {
                    if (!currentActivityLabel.isEmpty()) {
                        currentActivityLabel.delete(0, currentActivityLabel.length());
                    }
                    readingActivityLabel = true;
                }

                if (readingActivityLabel) {
                    currentActivityLabel.append(trimmedLine);
                }

                if (trimmedLine.endsWith(labelEnd)) {
                    readingActivityLabel = false;
                    currentActivityLabel.deleteCharAt(0);
                    currentActivityLabel.deleteCharAt(currentActivityLabel.length() - 1);
                    String activityName = currentActivityLabel.toString();
                    ActivityNode activityNode = new ActivityNode(activityName, ActivityNodeType.ACTIVITY);
                    activityNode.setSwimlane(currentSwimlane.toString());
                    activityDiagram.getActivities().add(activityNode);
                }

                if (Pattern.matches(swimlanePattern, trimmedLine)) {
                    activityDiagram.getSwimlanes().add("");

                    if (!currentSwimlane.isEmpty()) {
                        currentSwimlane.delete(0, currentActivityLabel.length());
                    }
                    currentSwimlane.append(trimmedLine);
                    currentSwimlane.deleteCharAt(0);
                    currentSwimlane.deleteCharAt(currentSwimlane.length() - 1);
                    String swimlaneName = currentSwimlane.toString();
                    activityDiagram.getSwimlanes().add(swimlaneName);
                }

                if (Pattern.matches(groupPattern, trimmedLine)) {
                    String groupName = trimmedLine.replaceFirst("^(group|partition|package|rectangle|card)", "").replace("{", "").trim();
                    currentGroup.append(groupName);
                    activityDiagram.getGroups().add(currentGroup.toString());
                }

                if (trimmedLine.contains(groupEnd) || trimmedLine.contains(partitionEnd)) {
                    currentGroup.delete(0, currentGroup.length());
                }
            }
        }

        activityDiagram.setElements(activityDiagram.countElements());
        activityDiagram.setActivitiesCount(activityDiagram.getElements());
        activityDiagram.setSwimlanesCount(activityDiagram.getSwimlanes().size());
        activityDiagram.setConditionalNodes(activityDiagram.countNodes(ActivityNodeType.CONDITIONAL));
        activityDiagram.setForkCount(activityDiagram.countNodes(ActivityNodeType.FORK));
        activityDiagram.setMergeCount(activityDiagram.countNodes(ActivityNodeType.MERGE));

        return activityDiagram;
    }

    public UseCaseDiagram parseUseCaseDiagram(List<String> lines) {
        UseCaseDiagram useCaseDiagram = new UseCaseDiagram();
        useCaseDiagram.setLanguage(Language.PLANTUML);
        useCaseDiagram.setType(DiagramType.USECASE);
        useCaseDiagram.setLinesCount(lines.size());

        String linkPattern = "^.+-[a-z\\-]*>.+$";
        String extensionPattern = "^.+<|--.+$";
        String includePattern = "^.+\\.>.+:\\s*include\\s*$";
        String excludePattern = "^.+\\.>.+:\\s*extends\\s*$";
        String useCaseDefinition = "^usecase [\\s\\S]+$";
        String useCasePattern = "^([\\s\\S]+).*$";
        String actorDefinition = "^actor [\\s\\S]+$";
        String actorPattern = "^:[\\s\\S]+:.*$";
        String aliasPattern = "^.+ as .+$";

        // TODO
        for (String line : lines) {
            if (!line.isBlank()) {
                String trimmedLine = line.trim();
            }
        }

        return useCaseDiagram;
    }

}
