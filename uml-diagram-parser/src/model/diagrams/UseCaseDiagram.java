package model.diagrams;

import model.elements.UseCaseNode;
import model.relationships.Link;

import java.util.HashSet;
import java.util.Set;

public class UseCaseDiagram extends UmlDiagram {

    private Set<UseCaseNode> actors;
    private Set<UseCaseNode> useCases;
    private Set<Link> links;
    private int useCasesCount;
    private int actorsCount;
    private int linksCount;

    // Getters, setters

    public UseCaseDiagram() {
        this.actors = new HashSet<>();
        this.useCases = new HashSet<>();
        this.links = new HashSet<>();
        useCasesCount = 0;
        actorsCount = 0;
        linksCount = 0;
    }

    public Set<UseCaseNode> getActors() {
        return actors;
    }

    public void setActors(Set<UseCaseNode> actors) {
        this.actors = actors;
    }

    public Set<UseCaseNode> getUseCases() {
        return useCases;
    }

    public void setUseCases(Set<UseCaseNode> useCases) {
        this.useCases = useCases;
    }

    public Set<Link> getLinks() {
        return links;
    }

    public void setLinks(Set<Link> links) {
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
