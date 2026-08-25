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
import java.util.Scanner;

public class DiagramComparator {

    public static void comparePromptTypes(Language language, DiagramType type, String scenario) {
        System.out.println("\n" + language + ":");
        System.out.println("-----------------------------------------------------------------------------------------------------\n");

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
        System.out.println("Numarul de noduri de decizie: " + stateDiagram1.getChoiceStatesCount());
        System.out.println("Numarul de stari initiale: " + stateDiagram1.getInitialStatesCount());
        System.out.println("Numarul de stari finale: " + stateDiagram1.getFinalStatesCount());
        System.out.println("Linii de cod: " + stateDiagram1.getLinesCount());
        System.out.println();
        System.out.println("2. Diagrama de stare generata folosind prompt-ul de tip specificatie:\n" + prompt2);
        System.out.println();
        System.out.println("Numarul de elemente (stari): " + stateDiagram2.getElements());
        System.out.println("Numarul de tranzitii: " + stateDiagram2.getRelationships());
        System.out.println("Numarul de noduri de decizie: " + stateDiagram2.getChoiceStatesCount());
        System.out.println("Numarul de stari initiale: " + stateDiagram2.getInitialStatesCount());
        System.out.println("Numarul de stari finale: " + stateDiagram2.getFinalStatesCount());
        System.out.println("Linii de cod: " + stateDiagram2.getLinesCount());
        System.out.println();
        System.out.println("3. Diagrama de stare generata folosind prompt-ul de tip tehnic:\n" + prompt3);
        System.out.println();
        System.out.println("Numarul de elemente (stari): " + stateDiagram3.getElements());
        System.out.println("Numarul de tranzitii: " + stateDiagram3.getRelationships());
        System.out.println("Numarul de noduri de decizie: " + stateDiagram3.getChoiceStatesCount());
        System.out.println("Numarul de stari initiale: " + stateDiagram3.getInitialStatesCount());
        System.out.println("Numarul de stari finale: " + stateDiagram3.getFinalStatesCount());
        System.out.println("Linii de cod: " + stateDiagram3.getLinesCount());
    }

    public static void printActivityDiagramComparison(ActivityDiagram activityDiagram1, ActivityDiagram activityDiagram2, ActivityDiagram activityDiagram3, String prompt1, String prompt2, String prompt3) {
        System.out.println("1. Diagrama de activitati generata folosind prompt-ul general:\n" + prompt1);
        System.out.println();
        System.out.println("Numarul de elemente (activitati): " + activityDiagram1.getElements());
        System.out.println("Numarul de noduri de decizie: " + activityDiagram1.getConditionalNodesCount());
        System.out.println("Numarul de stari initiale: " + activityDiagram1.getInitialStatesCount());
        System.out.println("Numarul de stari finale: " + activityDiagram1.getFinalStatesCount());
        System.out.println("Linii de cod: " + activityDiagram1.getLinesCount());
        System.out.println();
        System.out.println("2. Diagrama de activitati generata folosind prompt-ul de tip specificatie:\n" + prompt2);
        System.out.println();
        System.out.println("Numarul de elemente (activitati): " + activityDiagram2.getElements());
        System.out.println("Numarul de noduri de decizie: " + activityDiagram2.getConditionalNodesCount());
        System.out.println("Numarul de stari initiale: " + activityDiagram2.getInitialStatesCount());
        System.out.println("Numarul de stari finale: " + activityDiagram2.getFinalStatesCount());
        System.out.println("Linii de cod: " + activityDiagram2.getLinesCount());
        System.out.println();
        System.out.println("3. Diagrama de activitati generata folosind prompt-ul de tip tehnic:\n" + prompt3);
        System.out.println();
        System.out.println("Numarul de elemente (activitati): " + activityDiagram3.getElements());
        System.out.println("Numarul de noduri de decizie: " + activityDiagram3.getConditionalNodesCount());
        System.out.println("Numarul de stari initiale: " + activityDiagram3.getInitialStatesCount());
        System.out.println("Numarul de stari finale: " + activityDiagram3.getFinalStatesCount());
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

    public static void compareAllDiagrams() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Scenariu: ");
        String scenario = scanner.nextLine().trim();

        System.out.println("\n==================================================================================================================================================");
        System.out.println("\nScenariu: " + scenario);

        System.out.println("\n==================================================================================================================================================");
        System.out.println("\nCompara diagramele de stare generate in limbajul PlantUML si Mermaid cu trei tipuri de prompt-uri diferite (general, de tip specificatie, tehnic):");
        System.out.println("\n=================================================================================================================================================\n");
        comparePromptTypes(Language.PLANTUML, DiagramType.STATE, scenario);
        comparePromptTypes(Language.MERMAID, DiagramType.STATE, scenario);
        System.out.println("\n=======================================================================================================================================================");
        System.out.println("\nCompara diagramele de activitati generate in limbajul PlantUML si Mermaid cu trei tipuri de prompt-uri diferite (general, de tip specificatie, tehnic):");
        System.out.println("\n======================================================================================================================================================\n");
        comparePromptTypes(Language.PLANTUML, DiagramType.ACTIVITY, scenario);
        comparePromptTypes(Language.MERMAID, DiagramType.ACTIVITY, scenario);
        System.out.println("\n================================================================================================================================================================");
        System.out.println("\nCompara diagramele cazurilor de utilizare generate in limbajul PlantUML si Mermaid cu trei tipuri de prompt-uri diferite (general, de tip specificatie, tehnic):");
        System.out.println("\n===============================================================================================================================================================\n");
        comparePromptTypes(Language.PLANTUML, DiagramType.USECASE, scenario);
        comparePromptTypes(Language.MERMAID, DiagramType.USECASE, scenario);
    }

    public static void compareDiagramsToReference() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Scenariu: ");
        String scenario = scanner.nextLine().trim();

        System.out.println("\n=======================================================================================================================================================================");
        System.out.println("\nScenariu: " + scenario);
        System.out.println("\n========================================================================================================================================================================");
        System.out.println("\nCompara cu diagrama referinta diagramele de stare generate in limbajul PlantUML si Mermaid cu trei tipuri de prompt-uri diferite (general, de tip specificatie, tehnic):");
        System.out.println("\n========================================================================================================================================================================\n");
        compareStateDiagramToReference(Language.PLANTUML, DiagramType.STATE, scenario);
        compareStateDiagramToReference(Language.MERMAID, DiagramType.STATE, scenario);
        System.out.println("\n=============================================================================================================================================================================");
        System.out.println("\nCompara cu diagrama referinta diagramele de activitati generate in limbajul PlantUML si Mermaid cu trei tipuri de prompt-uri diferite (general, de tip specificatie, tehnic):");
        System.out.println("\n=============================================================================================================================================================================\n");
        compareActivityDiagramToReference(Language.PLANTUML, DiagramType.ACTIVITY, scenario);
        compareActivityDiagramToReference(Language.MERMAID, DiagramType.ACTIVITY, scenario);
        System.out.println("\n======================================================================================================================================================================================");
        System.out.println("\nCompara cu diagrama referinta diagramele cazurilor de utilizare generate in limbajul PlantUML si Mermaid cu trei tipuri de prompt-uri diferite (general, de tip specificatie, tehnic):");
        System.out.println("\n======================================================================================================================================================================================\n");
        compareUseCaseDiagramToReference(Language.PLANTUML, DiagramType.USECASE, scenario);
        compareUseCaseDiagramToReference(Language.MERMAID, DiagramType.USECASE, scenario);
    }

    public static void compareStateDiagramToReference(Language language, DiagramType type, String scenario) {
        FileReader fileReader = new FileReader();
        String diagramLanguage = language.equals(Language.PLANTUML) ? "plantuml" :"mermaid";

        List<String> diagram1 = fileReader.readFileIntoList("data/diagrams/" + scenario + "/state-diagram/" + diagramLanguage + "/state_diagram_general.txt");      // Citeste diagrama generata cu prompt-ul de tip general
        List<String> diagram2 = fileReader.readFileIntoList("data/diagrams/" + scenario + "/state-diagram/" + diagramLanguage + "/state_diagram_detailed.txt");     // Citeste diagrama generata cu prompt-ul de tip specificatie
        List<String> diagram3 = fileReader.readFileIntoList("data/diagrams/" + scenario + "/state-diagram/" + diagramLanguage + "/state_diagram_technical.txt");    // Citeste diagrama generata cu prompt-ul de tip tehnic
        List <String> groundTruthDiagram = fileReader.readFileIntoList("data/diagrams/" + scenario + "/state-diagram/state_diagram_reference.txt");    // Citeste diagrama ground truth

        String prompt1 = String.join("\n", fileReader.readFileIntoList("data/prompts/" + scenario + "/state-diagram/" + diagramLanguage + "/general_prompt.txt"));
        String prompt2 = String.join("\n", fileReader.readFileIntoList("data/prompts/" + scenario + "/state-diagram/" + diagramLanguage + "/detailed_prompt.txt"));
        String prompt3 = String.join("\n", fileReader.readFileIntoList("data/prompts/" + scenario + "/state-diagram/" + diagramLanguage + "/technical_prompt.txt"));

        PlantumlParser plantumlParser = new PlantumlParser();
        StateDiagram stateDiagram1, stateDiagram2, stateDiagram3;
        StateDiagram groundTruthStateDiagram = (StateDiagram) plantumlParser.parseDiagram(groundTruthDiagram, Language.PLANTUML, type);

        System.out.println("\n" + language + ":");
        System.out.println("-----------------------------------------------------------------------------------------------------\n");

        switch (language) {
            case PLANTUML:
                stateDiagram1 = (StateDiagram) plantumlParser.parseDiagram(diagram1, language, type);
                stateDiagram2 = (StateDiagram) plantumlParser.parseDiagram(diagram2, language, type);
                stateDiagram3 = (StateDiagram) plantumlParser.parseDiagram(diagram3, language, type);
                printStateComparisonToReference(groundTruthStateDiagram, stateDiagram1, stateDiagram2, stateDiagram3, prompt1, prompt2, prompt3);
                break;
            case MERMAID:
                MermaidParser mermaidParser = new MermaidParser();
                stateDiagram1 = (StateDiagram) mermaidParser.parseDiagram(diagram1, language, type);
                stateDiagram2 = (StateDiagram) mermaidParser.parseDiagram(diagram2, language, type);
                stateDiagram3 = (StateDiagram) mermaidParser.parseDiagram(diagram3, language, type);
                printStateComparisonToReference(groundTruthStateDiagram, stateDiagram1, stateDiagram2, stateDiagram3, prompt1, prompt2, prompt3);
                break;
            default:
                break;
        }
    }

    public static void compareActivityDiagramToReference(Language language, DiagramType type, String scenario) {
        FileReader fileReader = new FileReader();
        String diagramLanguage = language.equals(Language.PLANTUML) ? "plantuml" :"mermaid";

        List<String> diagram1 = fileReader.readFileIntoList("data/diagrams/" + scenario + "/activity-diagram/" + diagramLanguage + "/activity_diagram_general.txt");
        List<String> diagram2 = fileReader.readFileIntoList("data/diagrams/"  + scenario + "/activity-diagram/" + diagramLanguage + "/activity_diagram_detailed.txt");
        List<String> diagram3 = fileReader.readFileIntoList("data/diagrams/"  + scenario + "/activity-diagram/" + diagramLanguage + "/activity_diagram_technical.txt");
        List <String> groundTruthDiagram = fileReader.readFileIntoList("data/diagrams/" + scenario + "/activity-diagram/activity_diagram_reference.txt");

        String prompt1 = String.join("\n", fileReader.readFileIntoList("data/prompts/" + scenario + "/activity-diagram/" + diagramLanguage + "/general_prompt.txt"));
        String prompt2 = String.join("\n", fileReader.readFileIntoList("data/prompts/" + scenario + "/activity-diagram/" + diagramLanguage + "/detailed_prompt.txt"));
        String prompt3 = String.join("\n", fileReader.readFileIntoList("data/prompts/" + scenario + "/activity-diagram/" + diagramLanguage + "/technical_prompt.txt"));

        PlantumlParser plantumlParser = new PlantumlParser();
        ActivityDiagram activityDiagram1, activityDiagram2, activityDiagram3;
        ActivityDiagram groundTruthActivityDiagram = (ActivityDiagram) plantumlParser.parseDiagram(groundTruthDiagram, Language.PLANTUML, type);

        System.out.println("\n" + language + ":");
        System.out.println("-----------------------------------------------------------------------------------------------------\n");

        switch (language) {
            case PLANTUML:
                activityDiagram1 = (ActivityDiagram) plantumlParser.parseDiagram(diagram1, language, type);
                activityDiagram2 = (ActivityDiagram) plantumlParser.parseDiagram(diagram2, language, type);
                activityDiagram3 = (ActivityDiagram) plantumlParser.parseDiagram(diagram3, language, type);
                printActivityComparisonToReference(groundTruthActivityDiagram, activityDiagram1, activityDiagram2, activityDiagram3, prompt1, prompt2, prompt3);
                break;
            case MERMAID:
                MermaidParser mermaidParser = new MermaidParser();
                activityDiagram1 = (ActivityDiagram) mermaidParser.parseDiagram(diagram1, language, type);
                activityDiagram2 = (ActivityDiagram) mermaidParser.parseDiagram(diagram2, language, type);
                activityDiagram3 = (ActivityDiagram) mermaidParser.parseDiagram(diagram3, language, type);
                printActivityComparisonToReference(groundTruthActivityDiagram, activityDiagram1, activityDiagram2, activityDiagram3, prompt1, prompt2, prompt3);
                break;
            default:
                break;
        }
    }

    public static void compareUseCaseDiagramToReference(Language language, DiagramType type, String scenario) {
        FileReader fileReader = new FileReader();
        String diagramLanguage = language.equals(Language.PLANTUML) ? "plantuml" :"mermaid";

        List<String> diagram1 = fileReader.readFileIntoList("data/diagrams/" + scenario + "/usecase-diagram/" + diagramLanguage + "/usecase_diagram_general.txt");
        List<String> diagram2 = fileReader.readFileIntoList("data/diagrams/" + scenario + "/usecase-diagram/" + diagramLanguage + "/usecase_diagram_detailed.txt");
        List<String> diagram3 = fileReader.readFileIntoList("data/diagrams/" + scenario + "/usecase-diagram/" + diagramLanguage + "/usecase_diagram_technical.txt");
        List <String> groundTruthDiagram = fileReader.readFileIntoList("data/diagrams/" + scenario + "/usecase-diagram/usecase_diagram_reference.txt");

        String prompt1 = String.join("\n", fileReader.readFileIntoList("data/prompts/" + scenario + "/usecase-diagram/" + diagramLanguage + "/general_prompt.txt"));
        String prompt2 = String.join("\n", fileReader.readFileIntoList("data/prompts/" + scenario + "/usecase-diagram/" + diagramLanguage + "/detailed_prompt.txt"));
        String prompt3 = String.join("\n", fileReader.readFileIntoList("data/prompts/" + scenario + "/usecase-diagram/"+ diagramLanguage +"/technical_prompt.txt"));

        PlantumlParser plantumlParser = new PlantumlParser();
        UseCaseDiagram useCaseDiagram1, useCaseDiagram2, useCaseDiagram3;
        UseCaseDiagram groundTruthUseCaseDiagram = (UseCaseDiagram) plantumlParser.parseDiagram(groundTruthDiagram, Language.PLANTUML, type);

        switch (language) {
            case PLANTUML:
                useCaseDiagram1 = (UseCaseDiagram) plantumlParser.parseDiagram(diagram1, language, type);
                useCaseDiagram2 = (UseCaseDiagram) plantumlParser.parseDiagram(diagram2, language, type);
                useCaseDiagram3 = (UseCaseDiagram) plantumlParser.parseDiagram(diagram3, language, type);
                printUseCaseComparisonToReference(groundTruthUseCaseDiagram, useCaseDiagram1, useCaseDiagram2, useCaseDiagram3, prompt1, prompt2, prompt3);
                break;
            case MERMAID:
                MermaidParser mermaidParser = new MermaidParser();
                useCaseDiagram1 = (UseCaseDiagram) mermaidParser.parseDiagram(diagram1, language, type);
                useCaseDiagram2 = (UseCaseDiagram)  mermaidParser.parseDiagram(diagram2, language, type);
                useCaseDiagram3 = (UseCaseDiagram)  mermaidParser.parseDiagram(diagram3, language, type);
                printUseCaseComparisonToReference(groundTruthUseCaseDiagram, useCaseDiagram1, useCaseDiagram2, useCaseDiagram3, prompt1, prompt2, prompt3);
                break;
            default:
                break;
        }
    }

    public static void printUseCaseComparisonToReference(UseCaseDiagram groundTruth, UseCaseDiagram diagram1, UseCaseDiagram diagram2, UseCaseDiagram diagram3, String prompt1, String prompt2, String prompt3) {
        System.out.println("Elemente ale diagramei ground truth: ");
        groundTruth.printAllElements();

        System.out.println("\n1. Diagrama cazurilor de utilizare generata folosind prompt-ul general        |        Diagrama Ground truth\n");
        System.out.println("Prompt general: " + prompt1);
        System.out.println("\nNumarul de elemente:                " + diagram1.getElements() + "        |        "  + groundTruth.getElements());
        System.out.println("\nElemente:");
        diagram1.printAllElements();
        System.out.println("\nNumarul de relatii:                 " + diagram1.getRelationships() + "        |        " + groundTruth.getRelationships());
        System.out.println("\nLinii de cod:                       " + diagram1.getLinesCount() + "        |        "  + groundTruth.getLinesCount());

        System.out.println("\n2. Diagrama cazurilor de utilizare generata folosind prompt-ul de tip specificatie        |        Diagrama Ground truth\n");
        System.out.println("Prompt de tip specificatie: " + prompt2);
        System.out.println("\nNumarul de elemente:                " + diagram2.getElements() + "        |        "  + groundTruth.getElements());
        System.out.println("\nElemente:");
        diagram2.printAllElements();
        System.out.println("\nNumarul de relatii:                " + diagram2.getRelationships() + "        |        " + groundTruth.getRelationships());
        System.out.println("\nLinii de cod:                      " + diagram2.getLinesCount() + "        |        "  + groundTruth.getLinesCount());

        System.out.println("\n3. Diagrama cazurilor de utilizare generata folosind prompt-ul de tip tehnic        |        Diagrama Ground truth\n");
        System.out.println("Prompt tehnic: " + prompt3);
        System.out.println("\nNumarul de elemente:               " + diagram3.getElements() + "        |        "  + groundTruth.getElements());
        System.out.println("\nElemente:");
        diagram3.printAllElements();
        System.out.println("\nNumarul de relatii:                " + diagram3.getRelationships() + "        |        " + groundTruth.getRelationships());
        System.out.println("\nLinii de cod:                      " + diagram3.getLinesCount() + "        |        "  + groundTruth.getLinesCount());
    }

    public static void printStateComparisonToReference(StateDiagram groundTruth, StateDiagram diagram1, StateDiagram diagram2, StateDiagram diagram3, String prompt1, String prompt2, String prompt3) {
        System.out.println("Stari ale diagramei ground truth:");
        groundTruth.printStates();
        System.out.println("Noduri de decizie ale diagramei ground truth:");
        groundTruth.printConditions();

        System.out.println("\n1. Diagrama de stare generata folosind prompt-ul general        |        Diagrama Ground truth\n");
        System.out.println("Prompt general: " + prompt1);
        System.out.println("\nNumarul de elemente (stari):             " + diagram1.getElements() + "        |        "  + groundTruth.getElements());
        System.out.println("\nStari:");
        diagram1.printStates();
        System.out.println("\nNoduri de decizie:");
        diagram1.printConditions();
        System.out.println("\nNumarul de tranzitii:                   " + diagram1.getRelationships() + "        |        " + groundTruth.getRelationships());
        System.out.println("\nNumarul de noduri de decizie:           " + diagram1.getChoiceStatesCount() + "        |        " + groundTruth.getChoiceStatesCount());
        System.out.println("\nNumarul de stari initiale:              " + diagram1.getInitialStatesCount() + "        |        " + groundTruth.getInitialStatesCount());
        System.out.println("\nNumarul de stari finale:                " + diagram1.getFinalStatesCount() + "        |        " + groundTruth.getFinalStatesCount());
        System.out.println("\nLinii de cod:                           " + diagram1.getLinesCount() + "        |        "  + groundTruth.getLinesCount());

        System.out.println("\n2. Diagrama de stare generata folosind prompt-ul de tip specificatie        |        Diagrama Ground truth\n");
        System.out.println("Prompt de tip specificatie: " + prompt2);
        System.out.println("\nNumarul de elemente (stari):                " + diagram2.getElements() + "        |        "  + groundTruth.getElements());
        System.out.println("\nStari:");
        diagram2.printStates();
        System.out.println("\nNoduri de decizie:");
        diagram2.printConditions();
        System.out.println("\nNumarul de tranzitii:                     " + diagram2.getRelationships() + "        |        " + groundTruth.getRelationships());
        System.out.println("\nNumarul de noduri de decizie:             " + diagram2.getChoiceStatesCount() + "        |        "  + groundTruth.getChoiceStatesCount());
        System.out.println("\nNumarul de stari initiale:                " + diagram2.getInitialStatesCount() + "        |        " + groundTruth.getInitialStatesCount());
        System.out.println("\nNumarul de stari finale:                  " + diagram2.getFinalStatesCount() + "        |        " + groundTruth.getFinalStatesCount());
        System.out.println("\nLinii de cod:                             " + diagram2.getLinesCount() + "        |        "  + groundTruth.getLinesCount());

        System.out.println("\n3. Diagrama de stare generata folosind prompt-ul de tip tehnic         |        Diagrama Ground truth\n");
        System.out.println("Prompt tehnic: " + prompt3);
        System.out.println("\nNumarul de elemente (stari):                " + diagram3.getElements() + "        |        "  + groundTruth.getElements());
        System.out.println("\nStari:");
        diagram3.printStates();
        System.out.println("\nNoduri de decizie:");
        diagram3.printConditions();
        System.out.println("\nNumarul de tranzitii:                        " + diagram3.getRelationships() + "        |        "  + groundTruth.getRelationships());
        System.out.println("\nNumarul de noduri de decizie:                " + diagram3.getChoiceStatesCount() + "        |        "  + groundTruth.getChoiceStatesCount());
        System.out.println("\nNumarul de stari initiale:                   " + diagram3.getInitialStatesCount() + "        |        " + groundTruth.getInitialStatesCount());
        System.out.println("\nNumarul de stari finale:                     " + diagram3.getFinalStatesCount() + "        |        " + groundTruth.getFinalStatesCount());
        System.out.println("\nLinii de cod:                                " + diagram3.getLinesCount() + "        |        "  + groundTruth.getLinesCount());
    }

    public static void printActivityComparisonToReference(ActivityDiagram groundTruth, ActivityDiagram diagram1, ActivityDiagram diagram2, ActivityDiagram diagram3, String prompt1, String prompt2, String prompt3) {
        System.out.println("Activitati ale diagramei ground truth:");
        groundTruth.printActivities();
        System.out.println("\nConditii ale diagramei ground truth:");
        groundTruth.printConditions();

        System.out.println("\n1. Diagrama de activitati generata folosind prompt-ul general        |        Diagrama Ground truth\n");
        System.out.println("Prompt general: " + prompt1);
        System.out.println("\nNumarul de elemente (activitati):         " + diagram1.getElements() + "        |        " + groundTruth.getElements());
        System.out.println("\nActivitati:");
        diagram1.printActivities();
        System.out.println("\nConditii:");
        diagram1.printConditions();
        System.out.println("\nNumarul de stari initiale:                " + diagram1.getInitialStatesCount() + "        |        " + groundTruth.getInitialStatesCount());
        System.out.println("\nNumarul de stari finale:                  " + diagram1.getFinalStatesCount() + "        |        " + groundTruth.getFinalStatesCount());
        System.out.println("\nNumarul de noduri de decizie:             " + diagram1.getConditionalNodesCount() + "        |        " + groundTruth.getConditionalNodesCount());
        System.out.println("\nLinii de cod:                             " + diagram1.getLinesCount()  + "        |        " + groundTruth.getLinesCount());

        System.out.println("\n2. Diagrama de activitati generata folosind prompt-ul de tip specificatie        |        Diagrama Ground truth\n");
        System.out.println("Prompt de tip specificatie: " + prompt2);
        System.out.println("\nNumarul de elemente (activitati):         " + diagram2.getElements() + "        |        " + groundTruth.getElements());
        System.out.println("\nActivitati:");
        diagram2.printActivities();
        System.out.println("\nConditii:");
        diagram2.printConditions();
        System.out.println("\nNumarul de stari initiale:                " + diagram2.getInitialStatesCount() + "        |        " + groundTruth.getInitialStatesCount());
        System.out.println("\nNumarul de stari finale:                  " + diagram2.getFinalStatesCount() + "        |        " + groundTruth.getFinalStatesCount());
        System.out.println("\nNumarul de noduri de decizie:             " + diagram2.getConditionalNodesCount() + "        |        " + groundTruth.getConditionalNodesCount());
        System.out.println("\nLinii de cod:                             " + diagram2.getLinesCount()  + "        |        " + groundTruth.getLinesCount());

        System.out.println("\n3. Diagrama de activitati generata folosind prompt-ul de tip tehnic        |        Diagrama Ground truth\n");
        System.out.println("Prompt tehnic: " + prompt3);
        System.out.println("\nNumarul de elemente (activitati):          " + diagram3.getElements() + "        |        " + groundTruth.getElements());
        System.out.println("\nActivitati:");
        diagram3.printActivities();
        System.out.println("\nConditii:");
        diagram3.printConditions();
        System.out.println("\nNumarul de stari initiale:                 " + diagram3.getInitialStatesCount() + "        |        " + groundTruth.getInitialStatesCount());
        System.out.println("\nNumarul de stari finale:                   " + diagram3.getFinalStatesCount() + "        |        " + groundTruth.getFinalStatesCount());
        System.out.println("\nNumarul de noduri de decizie:              " + diagram3.getConditionalNodesCount() + "        |        " + groundTruth.getConditionalNodesCount());
        System.out.println("\nLinii de cod:                              " + diagram3.getLinesCount()  + "        |        " + groundTruth.getLinesCount());
    }
}