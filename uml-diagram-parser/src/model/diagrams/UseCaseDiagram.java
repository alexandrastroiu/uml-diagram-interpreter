package model.diagrams;

import model.elements.UseCaseNode;
import model.relationships.Link;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class UseCaseDiagram extends UmlDiagram {

    private LinkedHashSet<UseCaseNode> actors;
    private LinkedHashSet<UseCaseNode> useCases;
    private LinkedHashSet<Link> links;
    private int useCasesCount;
    private int actorsCount;
    private int linksCount;

    // Constructor

    public UseCaseDiagram() {
        this.actors = new LinkedHashSet<>();
        this.useCases = new LinkedHashSet<>();
        this.links = new LinkedHashSet<>();
        useCasesCount = 0;
        actorsCount = 0;
        linksCount = 0;
    }

    // Getters, setters

    public LinkedHashSet<UseCaseNode> getActors() {
        return actors;
    }

    public void setActors(LinkedHashSet<UseCaseNode> actors) {
        this.actors = actors;
    }

    public LinkedHashSet<UseCaseNode> getUseCases() {
        return useCases;
    }

    public void setUseCases(LinkedHashSet<UseCaseNode> useCases) {
        this.useCases = useCases;
    }

    public LinkedHashSet<Link> getLinks() {
        return links;
    }

    public void setLinks(LinkedHashSet<Link> links) {
        this.links = links;
    }

    public int getUseCasesCount() {
        return useCasesCount;
    }

    public void setUseCasesCount(int useCasesCount) {
        this.useCasesCount = useCasesCount;
    }

    public int getLinksCount() {
        return linksCount;
    }

    public void setLinksCount(int linksCount) {
        this.linksCount = linksCount;
    }

    public int getActorsCount() {
        return actorsCount;
    }

    public void setActorsCount(int actorsCount) {
        this.actorsCount = actorsCount;
    }

    public void printUseCases() {
        this.useCases.forEach(usecase -> System.out.println(usecase.getName()));
    }

    public void printActors() {
        this.actors.forEach(actor -> System.out.println(actor.getName()));
    }
}
