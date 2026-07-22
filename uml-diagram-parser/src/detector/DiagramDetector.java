package detector;

import enums.DiagramType;
import enums.Language;

import java.util.List;
import java.util.regex.Pattern;

public class DiagramDetector {

    // Default Constructor
    public DiagramDetector() {}

    public DiagramType detectDiagramType(List<String> lines, Language language) {

        if (language.equals(Language.PLANTUML)) {
            for (String line : lines) {
                if (!line.isBlank()) {
                    String trimmedLine = line.trim();

                    if (trimmedLine.contains("[*]") || Pattern.matches("\\bstate \\S+\\b", trimmedLine)) {
                        return DiagramType.STATE;
                    }

                    if (trimmedLine.equals("start") || trimmedLine.equals("stop") || trimmedLine.equals("end") || Pattern.matches("\\b(#[a-z]+|-#\\[[a-z]+,?[a-z]+\\]):.+;\\b", trimmedLine)) {
                        return DiagramType.ACTIVITY;
                    }

                    if (Pattern.matches("\\b(actor|usecase) \\S+\\b", trimmedLine) || Pattern.matches(".+\\(\\w+.*\\).+", trimmedLine)) {
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
                    if (Pattern.matches("\\bflowchart (TB|TD|BT|RL|LR)\\b", trimmedLine)) {
                        return DiagramType.ACTIVITY; //TODO
                    } else if (Pattern.matches("\\bflowchart (TB|TD|BT|RL|LR)\\b", trimmedLine)) {
                        return DiagramType.USECASE; //TODO
                    } else if (Pattern.matches("\\bstateDiagram(-v2)?\\b", trimmedLine)) {
                        return DiagramType.STATE;
                    }
                }
            }

            return DiagramType.UNKOWN;
        }

        return DiagramType.UNKOWN;
    }

}
