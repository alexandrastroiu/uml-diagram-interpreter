package model.relationships;

import enums.LinkType;
import model.elements.UseCaseNode;

public class Link {

    private LinkType type;
    private UseCaseNode start;
    private UseCaseNode end;

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
}
