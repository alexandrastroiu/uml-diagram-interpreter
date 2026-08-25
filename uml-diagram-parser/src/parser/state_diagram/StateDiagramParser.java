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

    public static final String SPECIAL_STATE_PATTERN = "[*]";
    private static final String STATE_PATTERN = "state ";
    private static final String COLOR_PATTERN = " #";
    private static final String COMPOSITE_PATTERN = "{";
    private static final String ALIAS_PATTERN = "^state [\\s\\S]+ as [\\s\\S]+$";
    private static final String ALIAS = " as ";
    private static final String TRANSITION_PATTERN = "^[\\s\\S]+ -[a-zA-Z0-9,#\\[\\]]*-> [\\s\\S]+$";
    private static final String START_PATTERN = "-";
    private static final String END_PATTERN = "->";
    private static final String DESCRIPTION_PATTERN = ":";
    private static final String STATE_DESCRIPTION = "^[A-Za-z0-9\"]+\\s*:[A-Za-z0-9\"]+\\s*$";
    private static final String STATE_ID_DEFINITION = "^[A-za-z0-9]+$";
    private static final Map<String, StateType> STEREOTYPES = Map.ofEntries(
            Map.entry("<<start>>", StateType.INITIAL),
            Map.entry("<<end>>", StateType.FINAL),
            Map.entry("<<choice>>", StateType.CHOICE),
            Map.entry("<<fork>>", StateType.FORK),
            Map.entry("<<join>>", StateType.JOIN)
    );

    // Metoda pentru interpretarea diagramei de stare in limbajul PlantUML/Mermaid

    public static StateDiagram parseStateDiagram(List<String> lines, Language language, DiagramType type) {
        StateDiagram stateDiagram = new StateDiagram();
        stateDiagram.setLanguage(language);
        stateDiagram.setType(type);
        stateDiagram.setLinesCount(lines.size());

        for (String line : lines) {
            if (!line.isBlank()) {
                String trimmedLine = line.trim();

                // Identifica o stare

                checkState(stateDiagram, trimmedLine);
                checkMermaidState(stateDiagram, trimmedLine, language);

                // Identifica o tranzitie

                checkTransition(stateDiagram, trimmedLine);
            }
        }

        stateDiagram.setTransitionCount(stateDiagram.getTransitions().size());
        stateDiagram.setRelationships(stateDiagram.getTransitionCount());
        stateDiagram.addSetElements(stateDiagram.getStates(), stateDiagram.getStateLookup());
        stateDiagram.setForkCount(stateDiagram.countNodes(StateType.FORK));
        stateDiagram.setJoinCount(stateDiagram.countNodes(StateType.JOIN));
        stateDiagram.setChoiceStatesCount(stateDiagram.countNodes(StateType.CHOICE));
        stateDiagram.setCompositeStatesCount(stateDiagram.countNodes(StateType.COMPOSITE));
        stateDiagram.setElements(stateDiagram.countElements());

        return stateDiagram;
    }

    private static boolean isTransition(String line) {
        return Pattern.matches(TRANSITION_PATTERN, line);
    }

    private static boolean isState(String line) {
        return line.startsWith(STATE_PATTERN);
    }

    private static void checkState(StateDiagram stateDiagram, String trimmedLine) {
        if (isState(trimmedLine)) {
            State state = new State();
            int index1 = STATE_PATTERN.length();
            String name = trimmedLine.substring(index1).replace("\"", "").trim();
            String stateAlias = "";

            if (trimmedLine.endsWith(COMPOSITE_PATTERN)) {
                int index2 = name.indexOf(COMPOSITE_PATTERN);
                name = name.substring(0, index2).replace("\"", "").trim();

                state.setType(StateType.COMPOSITE);
            }

            if (trimmedLine.contains(DESCRIPTION_PATTERN)) {
                int index3 = name.indexOf(DESCRIPTION_PATTERN);
                name = name.substring(0, index3).replace("\"", "").trim();
            }

            if (trimmedLine.contains(COLOR_PATTERN)) {
                int index4 = name.indexOf(COLOR_PATTERN);
                name = name.substring(0, index4).replace("\"", "").trim();
            }

            for (String key : STEREOTYPES.keySet()) {
                if (trimmedLine.contains(key)) {
                    int index5 = name.indexOf(key);
                    name = name.substring(0, index5).replace("\"", "").trim();

                    state.setType(STEREOTYPES.get(key));
                }
            }

            if (Pattern.matches(ALIAS_PATTERN, trimmedLine)) {
                int index6 = name.indexOf(ALIAS);
                int index7 = index6 + ALIAS.length();

                stateAlias = name.substring(index7).replace("\"", "").trim();
                name = name.substring(0, index6).replace("\"", "").trim();
            }

            state.setName(name);
            state.setAlias(stateAlias);
            stateDiagram.addState(state);
        }
        else if (Pattern.matches(STATE_DESCRIPTION, trimmedLine)) {
            State state = new State();
            int index = trimmedLine.indexOf(DESCRIPTION_PATTERN);
            String name = trimmedLine.substring(0, index).replace("\"", "").trim();
            String description = trimmedLine.substring(index).replace("\"", "").trim();

            state.setName(name);
            state.setDescription(description);
            stateDiagram.addState(state);
        }
    }

    private static void checkMermaidState(StateDiagram stateDiagram, String trimmedLine, Language language) {
        if (language.equals(Language.MERMAID)) {
            if (Pattern.matches(STATE_ID_DEFINITION, trimmedLine)) {
                State state = new State();
                String name = trimmedLine.replace("\"", "").trim();
                state.setName(name);
                stateDiagram.addState(state);
            }
        }
    }

    private static void checkTransition(StateDiagram stateDiagram, String trimmedLine) {
        if (isTransition(trimmedLine)) {
            Transition transition = new Transition();
            State startState = new State();
            State endState = new State();
            String name;

            if (trimmedLine.startsWith(SPECIAL_STATE_PATTERN)) {
                stateDiagram.setInitialStatesCount(stateDiagram.getInitialStatesCount() + 1);
                startState.setName("Initial State" + stateDiagram.getInitialStatesCount());
                startState.setType(StateType.INITIAL);
            }
            else {
                int indexStart = trimmedLine.indexOf(START_PATTERN);

                name = trimmedLine.substring(0, indexStart).replace("\"", "").trim();
                startState.setName(name);
            }

            int indexEnd = trimmedLine.indexOf(END_PATTERN);
            int indexStateEnd = indexEnd + END_PATTERN.length();

            if (trimmedLine.contains(DESCRIPTION_PATTERN)) {
                int indexDescription = trimmedLine.indexOf(DESCRIPTION_PATTERN);
                name = trimmedLine.substring(indexStateEnd, indexDescription).replace("\"", "").trim();
                String description = trimmedLine.substring(indexDescription + DESCRIPTION_PATTERN.length()).trim();

                transition.setTransitionDescription(description);
            }
            else {
                name = trimmedLine.substring(indexStateEnd).replace("\"", "").trim();
            }

            if (name.endsWith(SPECIAL_STATE_PATTERN.trim())) {
                stateDiagram.setFinalStatesCount(stateDiagram.getFinalStatesCount() + 1);
                endState.setName("Final State" + stateDiagram.getFinalStatesCount());
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