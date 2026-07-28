package parser.plantuml;

import enums.DiagramType;
import enums.Language;
import enums.StateType;
import model.diagrams.StateDiagram;
import model.diagrams.UmlDiagram;
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
                    //TODO
                    break;
                case USECASE:
                    //TODO
                    break;
                default:
                    return umlDiagram;
            }
        }
        return umlDiagram;
    }

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
                        //TODO Substates

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

}
