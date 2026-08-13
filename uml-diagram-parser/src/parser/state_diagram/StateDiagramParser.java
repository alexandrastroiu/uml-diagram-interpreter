package parser.state_diagram;

import enums.DiagramType;
import enums.Language;
import enums.StateType;
import model.diagrams.StateDiagram;
import model.elements.State;
import model.relationships.Transition;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class StateDiagramParser {

    // Metoda pentru interpretarea diagramei de stare in limbajul PlantUML/Mermaid

    public StateDiagram parseStateDiagram(List<String> lines, Language language, DiagramType type) {
        StateDiagram stateDiagram = new StateDiagram();
        stateDiagram.setLanguage(language);
        stateDiagram.setType(type);
        stateDiagram.setLinesCount(lines.size());

        Map<String, StateType> stereotypes = Map.ofEntries(
                Map.entry("<<start>>", StateType.INITIAL),
                Map.entry("<<end>>", StateType.FINAL),
                Map.entry("<<choice>>", StateType.CHOICE),
                Map.entry("<<fork>>", StateType.FORK),
                Map.entry("<<join>>", StateType.JOIN)
        );

        String specialStatePattern = "[*]";
        String statePattern = "state ";
        String colorPattern = " #";
        String compositePattern = "{";
        String aliasPattern = "^state [\\s\\S]+ as [\\s\\S]+$";
        String alias = " as ";
        String transitionPattern = "^[\\s\\S]+ -[a-zA-Z0-9,#\\[\\]]*-> [\\s\\S]+$";
        String startPattern = "-";
        String endPattern = "->";
        String descriptionPattern = ":";
        String stateDescription = "^[A-Za-z0-9\"]+\\s*:[A-Za-z0-9\"]+\\s*$";
        String stateIdDefinition = "^[A-za-z0-9]+$";

        for (String line : lines) {
            if (!line.isBlank()) {
                String trimmedLine = line.trim();

                // Identifica o stare

                if (trimmedLine.startsWith(statePattern)) {
                    State state = new State();
                    int index1 = statePattern.length();
                    String name = trimmedLine.substring(index1).trim();
                    String stateAlias = "";

                    if (trimmedLine.endsWith(compositePattern)) {
                        int index2 = name.indexOf(compositePattern);
                        name = name.substring(0, index2).trim();

                        state.setType(StateType.COMPOSITE);
                    }

                    if (trimmedLine.contains(colorPattern)) {
                        int index3 = name.indexOf(colorPattern);
                        name = name.substring(0, index3).trim();
                    }

                    for (String key : stereotypes.keySet()) {
                        if (trimmedLine.contains(key)) {
                            int index4 = name.indexOf(key);
                            name = name.substring(0, index4).trim();

                            state.setType(stereotypes.get(key));
                        }
                    }

                    if (Pattern.matches(aliasPattern, trimmedLine)) {
                        int index5 = name.indexOf(alias);
                        int index6 = index5 + alias.length();

                        stateAlias = name.substring(index6).replace("\"", "").trim();
                        name = name.substring(0, index5).trim();
                    }

                    state.setName(name);
                    state.setAlias(stateAlias);
                    stateDiagram.addState(state);
                }
                else if (Pattern.matches(stateDescription, trimmedLine)) {
                    State state = new State();
                    int index = trimmedLine.indexOf(descriptionPattern);
                    String stateAlias = trimmedLine.substring(0, index).trim();
                    String name = trimmedLine.substring(index).trim();

                    state.setName(name);
                    state.setAlias(stateAlias);
                    stateDiagram.addState(state);
                }

                if (language.equals(Language.MERMAID)) {
                    if (Pattern.matches(stateIdDefinition, trimmedLine)) {
                        State state = new State();
                        String name = trimmedLine.trim();
                        state.setName(name);
                        stateDiagram.addState(state);
                    }
                }

                // Identifica o tranzitie

                if (Pattern.matches(transitionPattern, trimmedLine)) {
                    Transition transition = new Transition();
                    State startState = new State();
                    State endState = new State();
                    String name;

                    if (trimmedLine.startsWith(specialStatePattern)) {
                        startState.setName("Initial State");
                        startState.setType(StateType.INITIAL);
                    }
                    else {
                        int indexStart = trimmedLine.indexOf(startPattern);

                        name = trimmedLine.substring(0, indexStart).replace("\"", "").trim();
                        startState.setName(name);
                    }

                    int indexEnd = trimmedLine.indexOf(endPattern);
                    int indexStateEnd = indexEnd + endPattern.length();

                    if (trimmedLine.contains(descriptionPattern)) {
                        int indexDescription = trimmedLine.indexOf(descriptionPattern);
                        name = trimmedLine.substring(indexStateEnd, indexDescription).replace("\"", "").trim();
                        String description = trimmedLine.substring(indexDescription + descriptionPattern.length()).trim();

                        transition.setTransitionDescription(description);
                    }
                    else {
                        name = trimmedLine.substring(indexStateEnd).replace("\"", "").trim();
                    }

                    if (name.endsWith(specialStatePattern.trim())) {
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
        stateDiagram.addSetElements(stateDiagram.getStates(), stateDiagram.getStateLookup());
        stateDiagram.setForkCount(stateDiagram.countNodes(StateType.FORK));
        stateDiagram.setJoinCount(stateDiagram.countNodes(StateType.JOIN));
        stateDiagram.setChoiceStates(stateDiagram.countNodes(StateType.CHOICE));
        stateDiagram.setCompositeStates(stateDiagram.countNodes(StateType.COMPOSITE));
        stateDiagram.setElements(stateDiagram.countElements());

        return stateDiagram;
    }
}