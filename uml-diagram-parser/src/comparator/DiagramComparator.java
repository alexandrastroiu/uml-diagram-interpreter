package comparator;

import enums.DiagramType;
import enums.Language;
import io.FileReader;
import model.diagrams.ActivityDiagram;
import model.diagrams.StateDiagram;
import model.diagrams.UseCaseDiagram;
import parser.mermaid.MermaidParser;
import parser.plantuml.PlantumlParser;

import java.util.List;

public class DiagramComparator {

    public static void comparePromptTypes(Language language, DiagramType type, String scenario) {
        switch (type){
            case ACTIVITY:
                compareActivityDiagrams(language, type, scenario);
                break;
            case STATE:
                compareStateDiagrams(language, type, scenario);
                break;
            case USECASE:
                compareUseCaseDiagrams(language, type, scenario);
                break;
            default:
                break;
        }
    }

    public static void compareStateDiagrams(Language language, DiagramType type, String scenario) {
        FileReader fileReader = new FileReader();
        String diagramLanguage = language.equals(Language.PLANTUML) ? "plantuml" :"mermaid";

        List<String> diagram1 = fileReader.readFileIntoList("data/diagrams/" + scenario + "/state-diagram/" + diagramLanguage + "/state_diagram_general.txt");      // Citeste diagrama generata cu prompt-ul de tip general
        List<String> diagram2 = fileReader.readFileIntoList("data/diagrams/" + scenario + "/state-diagram/" + diagramLanguage + "/state_diagram_detailed.txt");     // Citeste diagrama generata cu prompt-ul de tip specificatie
        List<String> diagram3 = fileReader.readFileIntoList("data/diagrams/" + scenario + "/state-diagram/" + diagramLanguage + "/state_diagram_technical.txt");    // Citeste diagrama generata cu prompt-ul de tip tehnic

        String prompt1 = String.join("\n", fileReader.readFileIntoList("data/prompts/" + scenario + "/state-diagram/" + diagramLanguage + "/general_prompt.txt"));
        String prompt2 = String.join("\n", fileReader.readFileIntoList("data/prompts/" + scenario + "/state-diagram/" + diagramLanguage + "/detailed_prompt.txt"));
        String prompt3 = String.join("\n", fileReader.readFileIntoList("data/prompts/" + scenario + "/state-diagram/" + diagramLanguage + "/technical_prompt.txt"));

        StateDiagram stateDiagram1, stateDiagram2, stateDiagram3;

        switch (language) {
            case PLANTUML:
                PlantumlParser plantumlParser = new PlantumlParser();
                stateDiagram1 = (StateDiagram) plantumlParser.parseDiagram(diagram1, language, type);
                stateDiagram2 = (StateDiagram) plantumlParser.parseDiagram(diagram2, language, type);
                stateDiagram3 = (StateDiagram) plantumlParser.parseDiagram(diagram3, language, type);
                printStateDiagramComparison(stateDiagram1, stateDiagram2, stateDiagram3, prompt1, prompt2, prompt3);

                break;
            case MERMAID:
                MermaidParser mermaidParser = new MermaidParser();
                stateDiagram1 = (StateDiagram) mermaidParser.parseDiagram(diagram1, language, type);
                stateDiagram2 = (StateDiagram) mermaidParser.parseDiagram(diagram2, language, type);
                stateDiagram3 = (StateDiagram) mermaidParser.parseDiagram(diagram3, language, type);
                printStateDiagramComparison(stateDiagram1, stateDiagram2, stateDiagram3, prompt1, prompt2, prompt3);
                break;
            default:
                break;
        }
    }

    public static void compareActivityDiagrams(Language language, DiagramType type, String scenario) {
        FileReader fileReader = new FileReader();
        String diagramLanguage = language.equals(Language.PLANTUML) ? "plantuml" :"mermaid";

        List<String> diagram1 = fileReader.readFileIntoList("data/diagrams/" + scenario + "/activity-diagram/" + diagramLanguage + "/activity_diagram_general.txt");
        List<String> diagram2 = fileReader.readFileIntoList("data/diagrams/"  + scenario + "/activity-diagram/" + diagramLanguage + "/activity_diagram_detailed.txt");
        List<String> diagram3 = fileReader.readFileIntoList("data/diagrams/"  + scenario + "/activity-diagram/" + diagramLanguage + "/activity_diagram_technical.txt");

        String prompt1 = String.join("\n", fileReader.readFileIntoList("data/prompts/" + scenario + "/activity-diagram/" + diagramLanguage + "/general_prompt.txt"));
        String prompt2 = String.join("\n", fileReader.readFileIntoList("data/prompts/" + scenario + "/activity-diagram/" + diagramLanguage + "/detailed_prompt.txt"));
        String prompt3 = String.join("\n", fileReader.readFileIntoList("data/prompts/" + scenario + "/activity-diagram/" + diagramLanguage + "/technical_prompt.txt"));

        ActivityDiagram activityDiagram1, activityDiagram2, activityDiagram3;

        switch (language) {
            case PLANTUML:
                PlantumlParser plantumlParser = new PlantumlParser();
                activityDiagram1 = (ActivityDiagram) plantumlParser.parseDiagram(diagram1, language, type);
                activityDiagram2 = (ActivityDiagram) plantumlParser.parseDiagram(diagram2, language, type);
                activityDiagram3 = (ActivityDiagram) plantumlParser.parseDiagram(diagram3, language, type);
                printActivityDiagramComparison(activityDiagram1, activityDiagram2, activityDiagram3, prompt1, prompt2, prompt3);
                break;
            case MERMAID:
                MermaidParser mermaidParser = new MermaidParser();
                activityDiagram1 = (ActivityDiagram) mermaidParser.parseDiagram(diagram1, language, type);
                activityDiagram2 = (ActivityDiagram) mermaidParser.parseDiagram(diagram2, language, type);
                activityDiagram3 = (ActivityDiagram) mermaidParser.parseDiagram(diagram3, language, type);
                printActivityDiagramComparison(activityDiagram1, activityDiagram2, activityDiagram3, prompt1, prompt2, prompt3);
                break;
            default:
                break;
        }
    }

    public static void compareUseCaseDiagrams(Language language, DiagramType type, String scenario) {
        FileReader fileReader = new FileReader();
        String diagramLanguage = language.equals(Language.PLANTUML) ? "plantuml" :"mermaid";

        List<String> diagram1 = fileReader.readFileIntoList("data/diagrams/" + scenario + "/usecase-diagram/" + diagramLanguage + "/usecase_diagram_general.txt");
        List<String> diagram2 = fileReader.readFileIntoList("data/diagrams/" + scenario + "/usecase-diagram/" + diagramLanguage + "/usecase_diagram_detailed.txt");
        List<String> diagram3 = fileReader.readFileIntoList("data/diagrams/" + scenario + "/usecase-diagram/" + diagramLanguage + "/usecase_diagram_technical.txt");

        String prompt1 = String.join("\n", fileReader.readFileIntoList("data/prompts/" + scenario + "/usecase-diagram/" + diagramLanguage + "/general_prompt.txt"));
        String prompt2 = String.join("\n", fileReader.readFileIntoList("data/prompts/" + scenario + "/usecase-diagram/" + diagramLanguage + "/detailed_prompt.txt"));
        String prompt3 = String.join("\n", fileReader.readFileIntoList("data/prompts/" + scenario + "/usecase-diagram/"+ diagramLanguage +"/technical_prompt.txt"));

        UseCaseDiagram useCaseDiagram1, useCaseDiagram2, useCaseDiagram3;

        switch (language) {
            case PLANTUML:
                PlantumlParser plantumlParser = new PlantumlParser();
                useCaseDiagram1 = (UseCaseDiagram) plantumlParser.parseDiagram(diagram1, language, type);
                useCaseDiagram2 = (UseCaseDiagram) plantumlParser.parseDiagram(diagram2, language, type);
                useCaseDiagram3 = (UseCaseDiagram) plantumlParser.parseDiagram(diagram3, language, type);
                printUseCaseDiagram(useCaseDiagram1, useCaseDiagram2, useCaseDiagram3, prompt1, prompt2, prompt3);
                break;
            case MERMAID:
                MermaidParser mermaidParser = new MermaidParser();
                useCaseDiagram1 = (UseCaseDiagram) mermaidParser.parseDiagram(diagram1, language, type);
                useCaseDiagram2 = (UseCaseDiagram)  mermaidParser.parseDiagram(diagram2, language, type);
                useCaseDiagram3 = (UseCaseDiagram)  mermaidParser.parseDiagram(diagram3, language, type);
                printUseCaseDiagram(useCaseDiagram1, useCaseDiagram2, useCaseDiagram3, prompt1, prompt2, prompt3);
                break;
            default:
                break;
        }
    }

    public static void printStateDiagramComparison(StateDiagram stateDiagram1, StateDiagram stateDiagram2, StateDiagram stateDiagram3, String prompt1, String prompt2, String prompt3) {
        System.out.println("1. Diagrama de stare generata folosind prompt-ul general:\n" + prompt1);
        System.out.println();
        System.out.println("Numarul de elemente (stari): " + stateDiagram1.getElements());
        System.out.println("Numarul de tranzitii: " + stateDiagram1.getRelationships());
        System.out.println("Linii de cod: " + stateDiagram1.getLinesCount());
        System.out.println();
        System.out.println("2. Diagrama de stare generata folosind prompt-ul de tip specificatie:\n" + prompt2);
        System.out.println();
        System.out.println("Numarul de elemente (stari): " + stateDiagram2.getElements());
        System.out.println("Numarul de tranzitii: " + stateDiagram2.getRelationships());
        System.out.println("Linii de cod: " + stateDiagram2.getLinesCount());
        System.out.println();
        System.out.println("3. Diagrama de stare generata folosind prompt-ul de tip tehnic:\n" + prompt3);
        System.out.println();
        System.out.println("Numarul de elemente (stari): " + stateDiagram3.getElements());
        System.out.println("Numarul de tranzitii: " + stateDiagram3.getRelationships());
        System.out.println("Linii de cod: " + stateDiagram3.getLinesCount());
    }

    public static void printActivityDiagramComparison(ActivityDiagram activityDiagram1, ActivityDiagram activityDiagram2, ActivityDiagram activityDiagram3, String prompt1, String prompt2, String prompt3) {
        System.out.println("1. Diagrama de activitati generata folosind prompt-ul general:\n" + prompt1);
        System.out.println();
        System.out.println("Numarul de elemente (activitati): " + activityDiagram1.getElements());
        System.out.println("Linii de cod: " + activityDiagram1.getLinesCount());
        System.out.println();
        System.out.println("2. Diagrama de activitati generata folosind prompt-ul de tip specificatie:\n" + prompt2);
        System.out.println();
        System.out.println("Numarul de elemente (activitati): " + activityDiagram2.getElements());
        System.out.println("Linii de cod: " + activityDiagram2.getLinesCount());
        System.out.println();
        System.out.println("3. Diagrama de activitati generata folosind prompt-ul de tip tehnic:\n" + prompt3);
        System.out.println();
        System.out.println("Numarul de elemente (activitati): " + activityDiagram3.getElements());
        System.out.println("Linii de cod: " + activityDiagram3.getLinesCount());
    }

    public static void printUseCaseDiagram(UseCaseDiagram useCaseDiagram1, UseCaseDiagram useCaseDiagram2, UseCaseDiagram useCaseDiagram3, String prompt1, String prompt2, String prompt3) {
        System.out.println("1. Diagrama cazurilor de utilizare generata folosind prompt-ul general:\n" + prompt1);
        System.out.println();
        System.out.println("Numarul de elemente: " + useCaseDiagram1.getElements());
        System.out.println("Numarul de cazuri de utilizare: " + useCaseDiagram1.getUseCasesCount());
        System.out.println("Numarul de actori: " + useCaseDiagram1.getActorsCount());
        System.out.println("Numarul de relatii: " + useCaseDiagram1.getLinksCount());
        System.out.println("Linii de cod: " + useCaseDiagram1.getLinesCount());
        System.out.println();
        System.out.println("2. Diagrama cazurilor de utilizare generata folosind prompt-ul de tip specificatie:\n" + prompt2);
        System.out.println();
        System.out.println("Numarul de elemente: " + useCaseDiagram2.getElements());
        System.out.println("Numarul de cazuri de utilizare: " + useCaseDiagram2.getUseCasesCount());
        System.out.println("Numarul de actori: " + useCaseDiagram2.getActorsCount());
        System.out.println("Numarul de relatii: " + useCaseDiagram2.getLinksCount());
        System.out.println("Linii de cod: " +  useCaseDiagram2.getLinesCount());
        System.out.println();
        System.out.println("3. Diagrama cazurilor de utilizare generata folosind prompt-ul de tip tehnic:\n" + prompt3);
        System.out.println();
        System.out.println("Numarul de elemente: " + useCaseDiagram3.getElements());
        System.out.println("Numarul de cazuri de utilizare: " + useCaseDiagram3.getUseCasesCount());
        System.out.println("Numarul de actori: " + useCaseDiagram3.getActorsCount());
        System.out.println("Numarul de relatii: " + useCaseDiagram3.getLinksCount());
        System.out.println("Linii de cod: " + useCaseDiagram3.getLinesCount());
    }
}