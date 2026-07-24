package model.diagrams;

import enums.DiagramType;
import enums.Language;

public class UmlDiagram {

    private Language language;
    private DiagramType type;
    private int elements;
    private int relationships;
    private int linesCount;

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public DiagramType getType() {
        return type;
    }

    public void setType(DiagramType type) {
        this.type = type;
    }

    public int getElements() {
        return elements;
    }

    public void setElements(int elements) {
        this.elements = elements;
    }

    public int getRelationships() {
        return relationships;
    }

    public void setRelationships(int relationships) {
        this.relationships = relationships;
    }

    public int getLinesCount() {
        return linesCount;
    }

    public void setLinesCount(int linesCount) {
        this.linesCount = linesCount;
    }
}
