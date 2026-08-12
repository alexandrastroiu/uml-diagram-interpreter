package comparator;

import enums.DiagramType;
import enums.Language;
import io.FileReader;
import model.diagrams.ActivityDiagram;
import model.diagrams.StateDiagram;
import model.diagrams.UseCaseDiagram;
import parser.plantuml.PlantumlParser;

import java.util.List;

public class DiagramComparator {

    public void comparePromptTypes(Language language, DiagramType type) {
        switch (type){
            case ACTIVITY:
                compareActivityDiagrams(language, type);
                break;
            case STATE:
                compareStateDiagrams(language, type);
                break;
            case USECASE:
                compareUseCaseDiagrams(language, type);
                break;
            case FLOWCHART:
                break;
            default:
                break;
        }
    }

    public void compareStateDiagrams(Language language, DiagramType type) {
        FileReader fileReader = new FileReader();

        List<String> diagram1 = fileReader.readFileIntoList("data/diagrams/state-diagram/state_diagram_general.txt");
        List<String> diagram2 = fileReader.readFileIntoList("data/diagrams/state-diagram/state_diagram_detailed.txt");
        List<String> diagram3 = fileReader.readFileIntoList("data/diagrams/state-diagram/state_diagram_technical.txt");

        String prompt1 = String.join("\n", fileReader.readFileIntoList("data/prompts/state-diagram/general_prompt.txt"));
        String prompt2 = String.join("\n", fileReader.readFileIntoList("data/prompts/state-diagram/detailed_prompt.txt"));
        String prompt3 = String.join("\n", fileReader.readFileIntoList("data/prompts/state-diagram/technical_prompt.txt"));


        switch (language) {
            case PLANTUML:
                PlantumlParser plantumlParser = new PlantumlParser();
                StateDiagram stateDiagram1 = (StateDiagram) plantumlParser.parseDiagram(diagram1, language, type);
                StateDiagram stateDiagram2 = (StateDiagram) plantumlParser.parseDiagram(diagram2, language, type);
                StateDiagram stateDiagram3 = (StateDiagram) plantumlParser.parseDiagram(diagram3, language, type);

                System.out.println("1. Diagrama de stare generata folosind prompt-ul general:\n" + prompt1);
                System.out.println();
                System.out.println("Numarul de elemente (stari): " + stateDiagram1.getElements());
                System.out.println("Numarul de tranzitii: " + stateDiagram1.getRelationships());
                System.out.println("Linii de Cod: " + stateDiagram1.getLinesCount());
                System.out.println();
                System.out.println("2. Diagrama de stare generata folosind prompt-ul de tip specificatie:\n" + prompt2);
                System.out.println();
                System.out.println("Numarul de elemente (stari): " + stateDiagram2.getElements());
                System.out.println("Numarul de tranzitii: " + stateDiagram2.getRelationships());
                System.out.println("Linii de Cod: " + stateDiagram2.getLinesCount());
                System.out.println();
                System.out.println("3. Diagrama de stare generata folosind prompt-ul de tip tehnic:\n" + prompt3);
                System.out.println();
                System.out.println("Numarul de elemente (stari): " + stateDiagram3.getElements());
                System.out.println("Numarul de tranzitii: " + stateDiagram3.getRelationships());
                System.out.println("Linii de Cod: " + stateDiagram3.getLinesCount());
                break;
            case MERMAID:
                break;
            default:
                break;
        }
    }

    public void compareActivityDiagrams(Language language, DiagramType type) {
        FileReader fileReader = new FileReader();

        List<String> diagram1 = fileReader.readFileIntoList("data/diagrams/activity-diagram/activity_diagram_general.txt");
        List<String> diagram2 = fileReader.readFileIntoList("data/diagrams/activity-diagram/activity_diagram_detailed.txt");
        List<String> diagram3 = fileReader.readFileIntoList("data/diagrams/activity-diagram/activity_diagram_technical.txt");

        String prompt1 = String.join("\n", fileReader.readFileIntoList("data/prompts/activity-diagram/general_prompt.txt"));
        String prompt2 = String.join("\n", fileReader.readFileIntoList("data/prompts/activity-diagram/detailed_prompt.txt"));
        String prompt3 = String.join("\n", fileReader.readFileIntoList("data/prompts/activity-diagram/technical_prompt.txt"));

        switch (language) {
            case PLANTUML:
                PlantumlParser plantumlParser = new PlantumlParser();
                ActivityDiagram activityDiagram1 = (ActivityDiagram) plantumlParser.parseDiagram(diagram1, language, type);
                ActivityDiagram activityDiagram2 = (ActivityDiagram) plantumlParser.parseDiagram(diagram2, language, type);
                ActivityDiagram activityDiagram3 = (ActivityDiagram) plantumlParser.parseDiagram(diagram3, language, type);

                System.out.println("1. Diagrama de activitati generata folosind prompt-ul general:\n" + prompt1);
                System.out.println();
                System.out.println("Numarul de elemente (activitati): " + activityDiagram1.getElements());
                System.out.println("Linii de Cod: " + activityDiagram1.getLinesCount());
                System.out.println();
                System.out.println("2. Diagrama de activitati generata folosind prompt-ul de tip specificatie:\n" + prompt2);
                System.out.println();
                System.out.println("Numarul de elemente (activitati): " + activityDiagram2.getElements());
                System.out.println("Linii de Cod: " + activityDiagram2.getLinesCount());
                System.out.println();
                System.out.println("3. Diagrama de activitati generata folosind prompt-ul de tip tehnic:\n" + prompt3);
                System.out.println();
                System.out.println("Numarul de elemente (activitati): " + activityDiagram3.getElements());
                System.out.println("Linii de Cod: " + activityDiagram3.getLinesCount());
                break;
            case MERMAID:
                break;
            default:
                break;
        }
    }

    public void compareUseCaseDiagrams(Language language, DiagramType type) {
        FileReader fileReader = new FileReader();

        List<String> diagram1 = fileReader.readFileIntoList("data/diagrams/usecase-diagram/usecase_diagram_general.txt");
        List<String> diagram2 = fileReader.readFileIntoList("data/diagrams/usecase-diagram/usecase_diagram_detailed.txt");
        List<String> diagram3 = fileReader.readFileIntoList("data/diagrams/usecase-diagram/usecase_diagram_technical.txt");

        String prompt1 = String.join("\n", fileReader.readFileIntoList("data/prompts/usecase-diagram/general_prompt.txt"));
        String prompt2 = String.join("\n", fileReader.readFileIntoList("data/prompts/usecase-diagram/detailed_prompt.txt"));
        String prompt3 = String.join("\n", fileReader.readFileIntoList("data/prompts/usecase-diagram/technical_prompt.txt"));

        switch (language) {
            case PLANTUML:
                PlantumlParser plantumlParser = new PlantumlParser();
                UseCaseDiagram useCaseDiagram1 = (UseCaseDiagram) plantumlParser.parseDiagram(diagram1, language, type);
                UseCaseDiagram useCaseDiagram2 = (UseCaseDiagram) plantumlParser.parseDiagram(diagram2, language, type);
                UseCaseDiagram useCaseDiagram3 = (UseCaseDiagram) plantumlParser.parseDiagram(diagram3, language, type);

                System.out.println("1. Diagrama de cazuri de utilizare generata folosind prompt-ul general:\n" + prompt1);
                System.out.println();
                System.out.println("Numarul de elemente: " + useCaseDiagram1.getElements());
                System.out.println("Numarul de cazuri de utilizare: " + useCaseDiagram1.getUseCasesCount());
                System.out.println("Numarul de actori: " + useCaseDiagram1.getActorsCount());
                System.out.println("Numarul de relatii: " + useCaseDiagram1.getLinksCount());
                System.out.println("Linii de Cod: " + useCaseDiagram1.getLinesCount());
                System.out.println();
                System.out.println("2. Diagrama de cazuri de utilizare generata folosind prompt-ul de tip specificatie:\n" + prompt2);
                System.out.println();
                System.out.println("Numarul de elemente: " + useCaseDiagram2.getElements());
                System.out.println("Numarul de cazuri de utilizare: " + useCaseDiagram2.getUseCasesCount());
                System.out.println("Numarul de actori: " + useCaseDiagram2.getActorsCount());
                System.out.println("Numarul de relatii: " + useCaseDiagram2.getLinksCount());
                System.out.println("Linii de Cod: " +  useCaseDiagram2.getLinesCount());
                System.out.println();
                System.out.println("3. Diagrama de cazuri de utilizare generata folosind prompt-ul de tip tehnic:\n" + prompt3);
                System.out.println();
                System.out.println("Numarul de elemente: " + useCaseDiagram3.getElements());
                System.out.println("Numarul de cazuri de utilizare: " + useCaseDiagram3.getUseCasesCount());
                System.out.println("Numarul de actori: " + useCaseDiagram3.getActorsCount());
                System.out.println("Numarul de relatii: " + useCaseDiagram3.getLinksCount());
                System.out.println("Linii de Cod: " + useCaseDiagram3.getLinesCount());
                break;
            case MERMAID:
                break;
            default:
                break;
        }
    }
}