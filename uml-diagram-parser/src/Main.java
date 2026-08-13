import comparator.DiagramComparator;
import enums.DiagramType;
import enums.Language;

public class Main {

    public static void main(String[] args) {
        DiagramComparator comparator = new DiagramComparator();

        System.out.println("\n==========================================================================================================================================");
        System.out.println("\nCompara diagramele de stare generate in limbajul PlantUML cu trei tipuri de prompt-uri diferite (general, de tip specificatie, tehnic):");
        System.out.println("\n==========================================================================================================================================\n");
        comparator.comparePromptTypes(Language.PLANTUML, DiagramType.STATE);
        System.out.println("\n===========================================================================================================================================");
        System.out.println("\nCompara diagramele de activitati generate in limbajul PlantUML cu trei tipuri de prompt-uri diferite (general, de tip specificatie, tehnic):");
        System.out.println("\n===========================================================================================================================================\n");
        comparator.comparePromptTypes(Language.PLANTUML, DiagramType.ACTIVITY);
        System.out.println("\n=========================================================================================================================================================");
        System.out.println("\nCompara diagramele cazurilor de utilizare generate in limbajul PlantUML cu trei tipuri de prompt-uri diferite (general, de tip specificatie, tehnic):");
        System.out.println("\n=========================================================================================================================================================\n");
        comparator.comparePromptTypes(Language.PLANTUML, DiagramType.USECASE);
    }
}