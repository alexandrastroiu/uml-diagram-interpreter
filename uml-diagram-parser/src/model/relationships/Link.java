package model.relationships;

import enums.LinkType;
import enums.NodeType;
import model.diagrams.UseCaseDiagram;
import model.elements.UseCaseNode;

public class Link {

    private LinkType type;
    private UseCaseNode start;
    private UseCaseNode end;

    // Constructor

    public Link(LinkType type) {
        this.type = type;
    }

    public Link(LinkType type, UseCaseNode start, UseCaseNode end) {
        this.type = type;
        this.start = start;
        this.end = end;
    }

    // Getters, Setters

    public LinkType getType() {
        return type;
    }

    public void setType(LinkType type) {
        this.type = type;
    }

    public UseCaseNode getStart() {
        return start;
    }

    public void setStart(UseCaseNode start) {
        this.start = start;
    }

    public UseCaseNode getEnd() {
        return end;
    }

    public void setEnd(UseCaseNode end) {
        this.end = end;
    }

    public void addLinkElements(UseCaseDiagram useCaseDiagram, String startName, String endName) {
        UseCaseNode startNode = getElement(useCaseDiagram, startName);
        UseCaseNode endNode = getElement(useCaseDiagram, endName);
        setStart(startNode);
        setEnd(endNode);
    }

    public UseCaseNode getElement(UseCaseDiagram useCaseDiagram, String elementName) {
        String name = UseCaseDiagram.getElementName(elementName);

        if (UseCaseDiagram.isUseCase(elementName) || useCaseDiagram.useCaseExists(name)) {
            if (!useCaseDiagram.useCaseExists(name)) {
                UseCaseNode useCase = new UseCaseNode(name, NodeType.USECASE);
                useCaseDiagram.addUseCaseNode(useCase, useCaseDiagram.getUseCaseLookup());
            }
            return useCaseDiagram.getUseCaseLookup().get(name);
        }
        else if (UseCaseDiagram.isActor(elementName) || useCaseDiagram.actorExists(name)) {
            if (!useCaseDiagram.actorExists(name)) {
                UseCaseNode actor = new UseCaseNode(name, NodeType.ACTOR);
                useCaseDiagram.addUseCaseNode(actor, useCaseDiagram.getActorLookup());
            }
            return useCaseDiagram.getActorLookup().get(name);
        }
        else {
            if (!useCaseDiagram.isMermaidElement(elementName)) {
                UseCaseNode element = new UseCaseNode(name, NodeType.ELEMENT);
                useCaseDiagram.addUseCaseNode(element, useCaseDiagram.getElementLookup());
            }
            return useCaseDiagram.getElementLookup().get(name);
        }
    }
}