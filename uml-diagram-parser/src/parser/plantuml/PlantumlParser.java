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
                    StateDiagram stateDiagram = new StateDiagram();
                    stateDiagram.setLanguage(Language.PLANTUML);
                    stateDiagram.setType(DiagramType.STATE);
                    stateDiagram.setLinesCount(lines.size());

                    Map<String, StateType> stereotypes = Map.ofEntries(
                            Map.entry("<<start>>", StateType.INITIAL),
                            Map.entry("<<end>>", StateType.FINAL),
                            Map.entry("<<choice>>", StateType.CHOICE),
                            Map.entry("fork", StateType.FORK),
                            Map.entry("join", StateType.JOIN)
                    );

                    for (String line : lines) {
                        if (!line.isBlank()) {
                            String trimmedLine = line.trim();

                            if (trimmedLine.startsWith("[*] ")) {
                                State initialState = new State();

                                initialState.setName("Initial State");
                                initialState.setType(StateType.INITIAL);

                                stateDiagram.getStates().add(initialState);
                            }

                            if (trimmedLine.endsWith("[*] ")) {
                                State finalState = new State();

                                finalState.setName("Final State");
                                finalState.setType(StateType.FINAL);

                                stateDiagram.getStates().add(finalState);
                            }

                            if (trimmedLine.startsWith("state ")) {
                                State state = new State();

                                if (Pattern.matches("\\bstate .+ as .+\\b", trimmedLine)) {
                                    //TODO description
                                }

                                if (trimmedLine.endsWith("{")) {
                                    state.setType(StateType.COMPOSITE);
                                    //TODO composite state + substates
                                }

                                for (String key : stereotypes.keySet()) {
                                    if (trimmedLine.endsWith(key)) {
                                        state.setType(stereotypes.get(key));
                                        //TODO stereotype
                                    }
                                }
                            }

                            if (Pattern.matches("\\b.+ -[a-zA-Z,#\\[\\]]*-> .+ \\b", trimmedLine)) {
                                //TODO transition
                                Transition transition = new Transition();

                                stateDiagram.getTransitions().add(transition);
                            }
                        }
                    }

                    return stateDiagram;
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

}
