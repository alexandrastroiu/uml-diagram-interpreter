package detector;

import enums.Language;
import java.util.List;
import java.util.regex.Pattern;

public class LanguageDetector {

    // Default Constructor
    public LanguageDetector() {}

    public Language detectDiagramLanguage(List<String> lines) {

        for (String line : lines) {

            if (!line.isBlank()) {
                String trimmedLine = line.trim();

                if (trimmedLine.startsWith("@startuml") || trimmedLine.startsWith("@enduml")) {
                    return Language.PLANTUML;
                }

                if (Pattern.matches("\\bflowchart (TB|TD|BT|RL|LR)\\b", trimmedLine) || Pattern.matches("\\bstateDiagram(-v2)?\\b", trimmedLine)) {
                    return Language.MERMAID;
                }
            }
        }

        return Language.UNKNOWN;
    }
}