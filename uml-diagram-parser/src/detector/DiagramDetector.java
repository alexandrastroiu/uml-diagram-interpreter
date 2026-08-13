package detector;

import enums.DiagramType;
import enums.Language;

import java.util.List;
import java.util.regex.Pattern;

public class DiagramDetector {

    // Default Constructor

    public DiagramDetector() {}

    // Metoda pentru a recunoaste tipul unei diagrame in limbajul PlantUML/Mermaid

    public DiagramType detectDiagramType(List<String> lines, Language language) {

        if (language.equals(Language.PLANTUML)) {
            for (String line : lines) {
                if (!line.isBlank()) {
                    String trimmedLine = line.trim();

                    if (trimmedLine.contains("[*]") || Pattern.matches("^state\\s+\\S+.*$", trimmedLine)) {
                        return DiagramType.STATE;
                    }

                    if (trimmedLine.equals("start") || trimmedLine.equals("stop") || trimmedLine.equals("end") || Pattern.matches("^(#[A-Za-z]+|#[A-Za-z]+\\[A-Za-z]+|#[A-F]{6})?:[^;]+;\\s+$", trimmedLine)) {
                        return DiagramType.ACTIVITY;
                    }

                    if (Pattern.matches("^(actor|usecase)\\s+\\S+.*$", trimmedLine) || Pattern.matches("^.+\\([\\S\\s]+\\).+$", trimmedLine)) {
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
                    if (Pattern.matches("^flowchart\\s+(TB|TD|BT|RL|LR)\\s*$", trimmedLine)) {
                        return DiagramType.FLOWCHART;
                    } else if (Pattern.matches("^stateDiagram(-v2)?\\s*$", trimmedLine)) {
                        return DiagramType.STATE;
                    }
                }
            }

            return DiagramType.UNKOWN;
        }

        return DiagramType.UNKOWN;
    }

}