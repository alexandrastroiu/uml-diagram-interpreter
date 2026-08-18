package detector;

import enums.DiagramType;
import enums.Language;
import parser.plantuml.PlantUmlActivityParser;
import parser.state_diagram.StateDiagramParser;

import java.util.List;
import java.util.regex.Pattern;

public class DiagramDetector {

    public static final String FLOWCHART_PATTERN = "^flowchart\\s+(TB|TD|BT|RL|LR)\\s*$";
    public static final String STATE_DIAGRAM_PATTERN = "^stateDiagram(-v2)?\\s*$";
    private static final String STATE = "^state\\s+\\S+.*$";
    private static final String ACTIVITY = "^(#[A-Za-z]+|#[A-Za-z]+\\[A-Za-z]+|#[A-F]{6})?:[^;]+;\\s+$";
    private static final String USECASE = "^.+\\([\\S\\s]+\\).+$";
    private static final String USECASE_DIAGRAM_ELEMENT = "^(actor|usecase)\\s+\\S+.*$";

    // Default Constructor

    public DiagramDetector() {}

    // Metoda pentru a recunoaste tipul unei diagrame in limbajul PlantUML/Mermaid

    public static DiagramType detectDiagramType(List<String> lines, Language language) {

        if (language.equals(Language.PLANTUML)) {
            for (String line : lines) {
                if (!line.isBlank()) {
                    String trimmedLine = line.trim();

                    if (trimmedLine.contains(StateDiagramParser.SPECIAL_STATE_PATTERN) || Pattern.matches(STATE, trimmedLine)) {
                        return DiagramType.STATE;
                    }

                    if (trimmedLine.equals(PlantUmlActivityParser.START_PATTERN) || trimmedLine.equals(PlantUmlActivityParser.END_PATTERN.get(0)) || trimmedLine.equals(PlantUmlActivityParser.END_PATTERN.get(1)) || Pattern.matches(ACTIVITY, trimmedLine)) {
                        return DiagramType.ACTIVITY;
                    }

                    if (Pattern.matches(USECASE_DIAGRAM_ELEMENT, trimmedLine) || Pattern.matches(USECASE, trimmedLine)) {
                        return DiagramType.USECASE;
                    }
                }
            }
            return DiagramType.UNKOWN;
        }
        else if (language.equals(Language.MERMAID)) {
            for (String line : lines) {
                if (!line.isBlank()) {
                    String trimmedLine = line.trim();
                    if (Pattern.matches(FLOWCHART_PATTERN, trimmedLine)) {
                        return DiagramType.FLOWCHART;
                    } else if (Pattern.matches(STATE_DIAGRAM_PATTERN, trimmedLine)) {
                        return DiagramType.STATE;
                    }
                }
            }

            return DiagramType.UNKOWN;
        }

        return DiagramType.UNKOWN;
    }
}