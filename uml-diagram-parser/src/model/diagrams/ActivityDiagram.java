package model.diagrams;

import enums.ActivityNodeType;
import model.elements.ActivityNode;
import model.relationships.ControlFlow;

import java.util.*;

public class ActivityDiagram extends UmlDiagram {

    private LinkedHashSet<ActivityNode> activities;
    private LinkedHashMap<String, ActivityNode> activityLookup;
    private LinkedHashSet<String> swimlanes;
    private LinkedHashSet<String> groups;
    private LinkedHashSet<ControlFlow> controlFlows;
    private int activitiesCount;
    private int swimlanesCount;
    private int initialStatesCount;
    private int finalStatesCount;
    private int forkCount;
    private int mergeCount;
    private int conditionalNodesCount;

    // Constructor

    public ActivityDiagram() {
        super();
        this.activities = new LinkedHashSet<>();
        this.activityLookup = new LinkedHashMap<>();
        this.swimlanes = new LinkedHashSet<>();
        this.groups = new LinkedHashSet<>();
        this.controlFlows = new LinkedHashSet<>();
        this.activitiesCount = 0;
        this.swimlanesCount = 0;
        this.initialStatesCount = 0;
        this.finalStatesCount = 0;
        this.forkCount = 0;
        this.mergeCount = 0;
        this.conditionalNodesCount = 0;
    }

    // Getters, setters

    public LinkedHashSet<ActivityNode> getActivities() {
        return activities;
    }

    public void setActivities(LinkedHashSet<ActivityNode> activities) {
        this.activities = activities;
    }

    public int getActivitiesCount() {
        return activitiesCount;
    }

    public void setActivitiesCount(int activitiesCount) {
        this.activitiesCount = activitiesCount;
    }

    public LinkedHashSet<String> getSwimlanes() {
        return swimlanes;
    }

    public void setSwimlanes(LinkedHashSet<String> swimlanes) {
        this.swimlanes = swimlanes;
    }

    public int getSwimlanesCount() {
        return swimlanesCount;
    }

    public void setSwimlanesCount(int swimlanesCount) {
        this.swimlanesCount = swimlanesCount;
    }

    public LinkedHashSet<ControlFlow> getControlFlows() {
        return controlFlows;
    }

    public void setControlFlows(LinkedHashSet<ControlFlow> controlFlows) {
        this.controlFlows = controlFlows;
    }

    public LinkedHashSet<String> getGroups() {
        return groups;
    }

    public void setGroups(LinkedHashSet<String> groups) {
        this.groups = groups;
    }

    public int getForkCount() {
        return forkCount;
    }

    public void setForkCount(int forkCount) {
        this.forkCount = forkCount;
    }

    public int getMergeCount() {
        return mergeCount;
    }

    public void setMergeCount(int mergeCount) {
        this.mergeCount = mergeCount;
    }

    public int getConditionalNodesCount() {
        return conditionalNodesCount;
    }

    public void setConditionalNodesCount(int conditionalNodesCount) {
        this.conditionalNodesCount = conditionalNodesCount;
    }

    public int getInitialStatesCount() {
        return initialStatesCount;
    }

    public void setInitialStatesCount(int initialStatesCount) {
        this.initialStatesCount = initialStatesCount;
    }

    public int getFinalStatesCount() {
        return finalStatesCount;
    }

    public void setFinalStatesCount(int finalStatesCount) {
        this.finalStatesCount = finalStatesCount;
    }

    public LinkedHashMap<String, ActivityNode> getActivityLookup() {
        return activityLookup;
    }

    public void setActivityLookup(LinkedHashMap<String, ActivityNode> activityLookup) {
        this.activityLookup = activityLookup;
    }

    public int countNodes(ActivityNodeType type) {
        List<ActivityNode> nodes = this.activities.stream().filter(activity -> activity.getType().equals(type)).toList();
        return nodes.size();
    }

    public int countElements() {
        List<ActivityNode> elements = this.activities.stream().filter(activity -> !activity.getType().equals(ActivityNodeType.CONDITIONAL) && !activity.getType().equals(ActivityNodeType.FORK) && !activity.getType().equals(ActivityNodeType.MERGE)).toList();
        return elements.size();
    }

    public void addActivity(ActivityNode activityNode) {
        HashMap<String, ActivityNode> activityNodes = getActivityLookup();
        String name = activityNode.getName();
        String alias = activityNode.getAlias();

        if (!activityNodes.containsKey(name)) {
            activityNodes.put(name, activityNode);
        }
        if (alias != null && !alias.isEmpty() && !activityNodes.containsKey(alias)) {
            activityNodes.put(alias,activityNode);
        }
    }

    public ActivityNode findActivity(String key) {
        return activityLookup.get(key);
    }

    public void addAllActivities(LinkedHashMap<String, ActivityNode> map) {
        if (!map.isEmpty()) {
            activities.addAll(map.values());
        }
    }

    public void printActivities() {
        this.activities.stream().filter(activity -> !activity.getType().equals(ActivityNodeType.FORK) && !activity.getType().equals(ActivityNodeType.MERGE) && !activity.getType().equals(ActivityNodeType.CONDITIONAL)).forEach(activity -> System.out.println(activity.getName()));
    }

    public void printConditions() {
        this.activities.stream().filter(condition -> condition.getType().equals(ActivityNodeType.CONDITIONAL)).forEach(condition -> System.out.println(condition.getName()));
    }

    public void printSwimlanes() {
        this.swimlanes.forEach(System.out::println);
    }

    public void printGroups() {
        this.groups.forEach(System.out::println);
    }
}