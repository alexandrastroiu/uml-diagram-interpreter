import comparator.DiagramComparator;

public class Main {

    public static void main(String[] args) {

        /**
        * Compara diagramele generate folosind UML Diagram Expert cu trei tipuri de prompt-uri diferite (general, de tip specificatie, tehnic)
        * pentru fiecare tip de diagrama (diagrama se stare, diagrama de activitati, diagrama cazurilor de utilizare)
        * in limbajul PlantUML si Mermaid pentru un scenariu ales
        **/

         // DiagramComparator.compareAllDiagrams();

        /**
         * Compara cu o diagrama de referinta diagramele generate folosind UML Diagram Expert cu trei tipuri de prompt-uri diferite (general, de tip specificatie, tehnic)
         * pentru fiecare tip de diagrama (diagrama se stare, diagrama de activitati, diagrama cazurilor de utilizare)
         * in limbajul PlantUML si Mermaid pentru un scenariu ales
         **/

         DiagramComparator.compareDiagramsToReference();

    }
}