package model.diagrams;

import model.elements.UseCaseNode;
import model.relationships.Link;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UseCaseDiagram extends UmlDiagram {

    private LinkedHashMap<String, UseCaseNode> actorLookup;
    private LinkedHashMap<String, UseCaseNode> useCaseLookup;
    private LinkedHashSet<UseCaseNode> actors;
    private LinkedHashSet<UseCaseNode> useCases;
    private LinkedHashSet<Link> links;
    private int useCasesCount;
    private int actorsCount;
    private int linksCount;

    // Constructor

    public UseCaseDiagram() {
        this.actorLookup = new LinkedHashMap<>();
        this.useCaseLookup = new LinkedHashMap<>();
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

    public LinkedHashMap<String, UseCaseNode> getActorLookup() {
        return actorLookup;
    }

    public void setActorLookup(LinkedHashMap<String, UseCaseNode> actorLookup) {
        this.actorLookup = actorLookup;
    }

    public LinkedHashMap<String, UseCaseNode> getUseCaseLookup() {
        return useCaseLookup;
    }

    public void setUseCaseLookup(LinkedHashMap<String, UseCaseNode> useCaseLookup) {
        this.useCaseLookup = useCaseLookup;
    }

    public void addUseCaseNode(UseCaseNode node, LinkedHashMap<String, UseCaseNode> map) {
        String name = node.getName();
        String alias = node.getAlias();

        if (map.containsKey(name)) {
            UseCaseNode value = map.get(name);
            value.updateUseCaseNode(node);
            if (!alias.isEmpty()) {
                map.put(alias, node);
            }
        }
        else {
            map.put(name, node);
            if (!alias.isEmpty()) {
                map.put(alias, node);
            }
        }
    }

    public void addSetElements(LinkedHashSet<UseCaseNode> set, LinkedHashMap<String, UseCaseNode> map) {
        if (!map.isEmpty()) {
            set.addAll(map.values());
        }
    }

    public String getElementName(String name) {
        return name.trim().replace("(", "").replace(")", "").replace(":", "").trim();
    }

    public boolean isActor(String name) {
        String actorPattern = "(^:[\\s\\S]+:[\\s\\S]*$|^[^:]+$)";


        return Pattern.matches(actorPattern, name);
    }

    public boolean isUseCase(String name) {
        String actorPattern = "^\\([\\s\\S]+\\)[\\s\\S]*$";

        return Pattern.matches(actorPattern, name);
    }

    public boolean useCaseExists(String name) {
        return useCaseLookup.containsKey(name);
    }

    public boolean actorExists(String name) {
        return actorLookup.containsKey(name);
    }

    public void printUseCases() {
        useCases.forEach(useCase -> System.out.println(useCase.getName()));
    }

    public void printActors() {
        actors.forEach(actor -> System.out.println(actor.getName()));
    }

    public void printLinks() {
        links.forEach(link -> System.out.println(link.getStart().getName() + " -> " + link.getEnd().getName() + " - " + link.getType()));
    }
}
