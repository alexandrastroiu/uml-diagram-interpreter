package model.relationships;

import enums.FlowType;
import model.elements.ActivityNode;

public class ControlFlow {

    private ActivityNode start;
    private ActivityNode end;
    private FlowType type;

    //  Getters, Setters

    public ActivityNode getStart() {
        return start;
    }

    public void setStart(ActivityNode start) {
        this.start = start;
    }

    public ActivityNode getEnd() {
        return end;
    }

    public void setEnd(ActivityNode end) {
        this.end = end;
    }

    public FlowType getType() {
        return type;
    }

    public void setType(FlowType type) {
        this.type = type;
    }

}
