package detector;

import enums.Language;
import java.util.List;
import java.util.regex.Pattern;

public class LanguageDetector {

    private static final String PLANTUML_START = "@startuml";
    private static final String PLANTUML_END = "@enduml";

    // Default Constructor

    public LanguageDetector() {}

    // Metoda pentru a recunoaste limbajul unei diagrame

    public static Language detectDiagramLanguage(List<String> lines) {

        for (String line : lines) {

            if (!line.isBlank()) {
                String trimmedLine = line.trim();

                if (trimmedLine.startsWith(PLANTUML_START) || trimmedLine.startsWith(PLANTUML_END)) {
                    return Language.PLANTUML;
                }

                if (Pattern.matches(DiagramDetector.FLOWCHART_PATTERN, trimmedLine) || Pattern.matches(DiagramDetector.STATE_DIAGRAM_PATTERN, trimmedLine)) {
                    return Language.MERMAID;
                }
            }
        }

        return Language.UNKNOWN;
    }
}