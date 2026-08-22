import comparator.DiagramComparator;
import enums.DiagramType;
import enums.Language;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Scenariu: ");
        String scenario = scanner.nextLine();

        System.out.println("Scenariu: " + scenario);

        System.out.println("\n==========================================================================================================================================");
        System.out.println("\nCompara diagramele de stare generate in limbajul PlantUML cu trei tipuri de prompt-uri diferite (general, de tip specificatie, tehnic):");
        System.out.println("\n==========================================================================================================================================\n");
        DiagramComparator.comparePromptTypes(Language.PLANTUML, DiagramType.STATE, scenario);
        System.out.println("\n===========================================================================================================================================");
        System.out.println("\nCompara diagramele de activitati generate in limbajul PlantUML cu trei tipuri de prompt-uri diferite (general, de tip specificatie, tehnic):");
        System.out.println("\n===========================================================================================================================================\n");
        DiagramComparator.comparePromptTypes(Language.PLANTUML, DiagramType.ACTIVITY, scenario);
        System.out.println("\n=========================================================================================================================================================");
        System.out.println("\nCompara diagramele cazurilor de utilizare generate in limbajul PlantUML cu trei tipuri de prompt-uri diferite (general, de tip specificatie, tehnic):");
        System.out.println("\n=========================================================================================================================================================\n");
        DiagramComparator.comparePromptTypes(Language.PLANTUML, DiagramType.USECASE, scenario);
    }
}