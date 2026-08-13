package parser.plantuml;

import enums.*;
import model.diagrams.ActivityDiagram;
import model.diagrams.StateDiagram;
import model.diagrams.UmlDiagram;
import model.diagrams.UseCaseDiagram;
import model.elements.ActivityNode;
import model.elements.UseCaseNode;
import model.relationships.Link;
import parser.DiagramParser;
import parser.state_diagram.StateDiagramParser;

import java.util.List;
import java.util.regex.Pattern;

public class PlantumlParser implements DiagramParser {

    @Override
    public UmlDiagram parseDiagram(List<String> lines, Language language, DiagramType type) {
        UmlDiagram umlDiagram = new UmlDiagram();

        if (language.equals(Language.PLANTUML)) {
            switch (type) {
                case STATE:
                    return parseStateDiagram(lines, language, type);
                case ACTIVITY:
                    return parseActivityDiagram(lines);
                case USECASE:
                    return parseUseCaseDiagram(lines);
                default:
                    return umlDiagram;
            }
        }
        return umlDiagram;
    }

    // Metoda pentru interpretarea diagramei de stare in limbajul PlantUML

    public StateDiagram parseStateDiagram(List<String> lines, Language language, DiagramType type) {
        StateDiagramParser stateDiagramParser = new StateDiagramParser();
        return stateDiagramParser.parseStateDiagram(lines, language, type);
    }

    // Metoda pentru interpretarea diagramei de activitati in limbajul PlantUML

    public ActivityDiagram parseActivityDiagram(List<String> lines) {
        ActivityDiagram activityDiagram = new ActivityDiagram();
        activityDiagram.setLanguage(Language.PLANTUML);
        activityDiagram.setType(DiagramType.ACTIVITY);
        activityDiagram.setLinesCount(lines.size());

        String startPattern = "start";
        List<String> endPattern = List.of("stop", "end");
        String conditionalPattern = "^[\\s\\S]*(if|elseif)[\\s\\S]+then[\\s\\S]+$";
        String switchPattern = "case\\s+([\\s\\S]+)\\s*";
        String labelStart = "^\\s*:.+$";
        String labelEnd = ";";
        String forkPattern = "^fork[\\s\\S]*$";
        String mergePattern = "^end\\s+merge\\s*$";
        String swimlanePattern = "^\\|[\\s\\S]+\\|$";
        String groupPattern = "^(group|partition|package|rectangle|card)[\\s\\S]+\\{?\\s*$";
        String groupEnd = "end group";
        String partitionEnd = "}";

        final int CAPACITY = 50;
        StringBuilder currentActivityLabel = new StringBuilder(CAPACITY);
        StringBuilder currentSwimlane = new StringBuilder(CAPACITY);
        StringBuilder currentGroup = new StringBuilder(CAPACITY);
        boolean readingActivityLabel = false;

        for (String line : lines) {
            if (!line.isBlank()) {
                String trimmedLine = line.trim();

                if (trimmedLine.equals(startPattern)) {
                    ActivityNode startNode = new ActivityNode("Start", ActivityNodeType.START, currentSwimlane.toString(), currentGroup.toString());
                    activityDiagram.addActivity(startNode);
                }

                if (trimmedLine.equals(endPattern.get(0)) || trimmedLine.equals(endPattern.get(1))) {
                    ActivityNode endNode = new ActivityNode("Stop", ActivityNodeType.STOP, currentSwimlane.toString(), currentGroup.toString());
                    activityDiagram.addActivity(endNode);
                }

                if (Pattern.matches(forkPattern, trimmedLine)) {
                    ActivityNode forkNode = new ActivityNode("Fork", ActivityNodeType.FORK, currentSwimlane.toString(), currentGroup.toString());
                    activityDiagram.addActivity(forkNode);
                }

                if (Pattern.matches(switchPattern, trimmedLine) || Pattern.matches(conditionalPattern, trimmedLine)) {
                    ActivityNode conditionalNode = new ActivityNode("Condition", ActivityNodeType.CONDITIONAL, currentSwimlane.toString(), currentGroup.toString());
                    activityDiagram.addActivity(conditionalNode);
                }

                if (Pattern.matches(mergePattern, trimmedLine)) {
                    ActivityNode mergeNode = new ActivityNode("Merge", ActivityNodeType.MERGE, currentSwimlane.toString(), currentGroup.toString());
                    activityDiagram.addActivity(mergeNode);
                }

                // Identifica o activitate

                if (Pattern.matches(labelStart, trimmedLine)) {
                    if (!currentActivityLabel.isEmpty()) {
                        currentActivityLabel.delete(0, currentActivityLabel.length());
                    }
                    readingActivityLabel = true;
                }

                if (readingActivityLabel) {
                    currentActivityLabel.append(trimmedLine);
                }

                if (trimmedLine.endsWith(labelEnd)) {
                    readingActivityLabel = false;
                    currentActivityLabel.deleteCharAt(0);
                    currentActivityLabel.deleteCharAt(currentActivityLabel.length() - 1);
                    String activityName = currentActivityLabel.toString();
                    ActivityNode activityNode = new ActivityNode(activityName, ActivityNodeType.ACTIVITY);
                    activityNode.setSwimlane(currentSwimlane.toString());
                    activityDiagram.addActivity(activityNode);
                }

                if (Pattern.matches(swimlanePattern, trimmedLine)) {
                    activityDiagram.getSwimlanes().add("");

                    if (!currentSwimlane.isEmpty()) {
                        currentSwimlane.delete(0, currentActivityLabel.length());
                    }
                    currentSwimlane.append(trimmedLine);
                    currentSwimlane.deleteCharAt(0);
                    currentSwimlane.deleteCharAt(currentSwimlane.length() - 1);
                    String swimlaneName = currentSwimlane.toString();
                    activityDiagram.getSwimlanes().add(swimlaneName);
                }

                if (Pattern.matches(groupPattern, trimmedLine)) {
                    String groupName = trimmedLine.replaceFirst("^(group|partition|package|rectangle|card)", "").replace("{", "").trim();
                    currentGroup.append(groupName);
                    activityDiagram.getGroups().add(currentGroup.toString());
                }

                if (trimmedLine.contains(groupEnd) || trimmedLine.contains(partitionEnd)) {
                    currentGroup.delete(0, currentGroup.length());
                }
            }
        }

        activityDiagram.setElements(activityDiagram.countElements());
        activityDiagram.setActivitiesCount(activityDiagram.getElements());
        activityDiagram.setSwimlanesCount(activityDiagram.getSwimlanes().size());
        activityDiagram.setConditionalNodes(activityDiagram.countNodes(ActivityNodeType.CONDITIONAL));
        activityDiagram.setForkCount(activityDiagram.countNodes(ActivityNodeType.FORK));
        activityDiagram.setMergeCount(activityDiagram.countNodes(ActivityNodeType.MERGE));

        return activityDiagram;
    }

    // Metoda pentru interpretarea diagramei cazurilor de utilizare in limbajul PlantUML

    public UseCaseDiagram parseUseCaseDiagram(List<String> lines) {
        UseCaseDiagram useCaseDiagram = new UseCaseDiagram();
        useCaseDiagram.setLanguage(Language.PLANTUML);
        useCaseDiagram.setType(DiagramType.USECASE);
        useCaseDiagram.setLinesCount(lines.size());

        String linkPattern = "^.+-[a-z\\-]*>.+$";
        String extensionPattern = "^.+<\\|--.+$";
        String includePattern = "^.+\\.>.+:\\s*<<include>>\\s*$";
        String extendPattern = "^.+\\.>.+:\\s*<<extend>>\\s*$";
        String useCaseDefinition = "^usecase [\\s\\S]+$";
        String useCasePattern = "^\\([\\s\\S]+\\).*$";
        String actorDefinition = "^actor [\\s\\S]+$";
        String actorPattern = "^:[\\s\\S]+:.*$";
        String aliasPattern = "^.+ as .+$";
        String usecase = "usecase";
        String actor = "actor";
        String alias = " as ";

        for (String line : lines) {

            if (!line.isBlank()) {
                String trimmedLine = line.trim();

                // Identifica un caz de utilizare

                if (Pattern.matches(useCaseDefinition, trimmedLine)) {
                    int nameStart = usecase.length();
                    int nameEnd = trimmedLine.length();
                    int aliasStart = trimmedLine.length();

                    if (Pattern.matches(aliasPattern, trimmedLine)) {
                        nameEnd = trimmedLine.indexOf(alias);
                        aliasStart = nameEnd + alias.length();
                    }

                    String useCaseName = trimmedLine.substring(nameStart, nameEnd).replace("\"", "").trim();
                    String useCaseAlias = trimmedLine.substring(aliasStart);
                    UseCaseNode newUseCase = new UseCaseNode(useCaseName, useCaseAlias, NodeType.USECASE);
                    useCaseDiagram.addUseCaseNode(newUseCase, useCaseDiagram.getUseCaseLookup());
                }

                if (Pattern.matches(useCasePattern, trimmedLine)) {
                    int nameStart = trimmedLine.indexOf("(");
                    int nameEnd = trimmedLine.length();
                    int aliasStart = trimmedLine.length();

                    if (Pattern.matches(aliasPattern, trimmedLine)) {
                        nameEnd = trimmedLine.indexOf(alias);
                        aliasStart = nameEnd + alias.length();
                    }

                    String useCaseName = trimmedLine.substring(nameStart, nameEnd).replace("(", "").replace(")", ""). trim();
                    String useCaseAlias = trimmedLine.substring(aliasStart);
                    UseCaseNode newUseCase = new UseCaseNode(useCaseName, useCaseAlias, NodeType.USECASE);
                    useCaseDiagram.addUseCaseNode(newUseCase, useCaseDiagram.getUseCaseLookup());
                }

                // Identifica un actor

                if (Pattern.matches(actorDefinition, trimmedLine)) {
                    int nameStart = actor.length();
                    int nameEnd = trimmedLine.length();
                    int aliasStart = trimmedLine.length();

                    if (Pattern.matches(aliasPattern, trimmedLine)) {
                        nameEnd = trimmedLine.indexOf(alias);
                        aliasStart = nameEnd + alias.length();
                    }

                    String actorName = trimmedLine.substring(nameStart, nameEnd).replace("\"", "").trim();
                    String actorAlias = trimmedLine.substring(aliasStart);
                    UseCaseNode newActor = new UseCaseNode(actorName, actorAlias, NodeType.ACTOR);
                    useCaseDiagram.addUseCaseNode(newActor, useCaseDiagram.getActorLookup());
                }

                if (Pattern.matches(actorPattern, trimmedLine)) {
                    int nameStart = trimmedLine.indexOf(":");
                    int nameEnd = trimmedLine.length();
                    int aliasStart = trimmedLine.length();

                    if (Pattern.matches(aliasPattern, trimmedLine)) {
                        nameEnd = trimmedLine.indexOf(alias);
                        aliasStart = nameEnd + alias.length();
                    }

                    String actorName = trimmedLine.substring(nameStart, nameEnd).replace(":", ""). trim();
                    String actorAlias = trimmedLine.substring(aliasStart);
                    UseCaseNode newActor = new UseCaseNode(actorName, actorAlias, NodeType.USECASE);
                    useCaseDiagram.addUseCaseNode(newActor, useCaseDiagram.getActorLookup());
                }
            }
        }

        // Identifica relatiile dintre elemente

        for (String line : lines) {
            if (!line.isBlank()) {
                String trimmedLine = line.trim();

                if (Pattern.matches(linkPattern, trimmedLine)) {
                    int linkStart = trimmedLine.indexOf(" -");
                    int linkEnd = trimmedLine.indexOf("> ");
                    String element1 = trimmedLine.substring(0, linkStart).trim();
                    String element2 = trimmedLine.substring(linkEnd + 1).trim();
                    Link link = new Link(LinkType.LINK);
                    link.addLinkElements(useCaseDiagram, element1, element2);
                    useCaseDiagram.getLinks().add(link);
                }

                if (Pattern.matches(extendPattern, trimmedLine)) {
                    int linkStart = trimmedLine.indexOf(" .");
                    int linkEnd = trimmedLine.indexOf("> ");
                    int index = trimmedLine.indexOf("<<extend>>");
                    String element1 = trimmedLine.substring(0, linkStart).trim();
                    String element2 = trimmedLine.substring(linkEnd + 1, index).trim();
                    Link link = new Link(LinkType.EXTEND);
                    link.addLinkElements(useCaseDiagram, element1, element2);
                    useCaseDiagram.getLinks().add(link);
                }

                if (Pattern.matches(includePattern, trimmedLine)) {
                    int linkStart = trimmedLine.indexOf(" .");
                    int linkEnd = trimmedLine.indexOf("> ");
                    int index = trimmedLine.indexOf("<<include>>");
                    String element1 = trimmedLine.substring(0, linkStart).trim();
                    String element2 = trimmedLine.substring(linkEnd + 1, index).trim();
                    Link link = new Link(LinkType.INCLUDE);
                    link.addLinkElements(useCaseDiagram, element1, element2);
                    useCaseDiagram.getLinks().add(link);
                }

                if (Pattern.matches(extensionPattern, trimmedLine)) {
                    int linkStart = trimmedLine.indexOf(" <");
                    int linkEnd = trimmedLine.indexOf("- ");
                    String element1 = trimmedLine.substring(0, linkStart).trim();
                    String element2 = trimmedLine.substring(linkEnd + 1).trim();
                    Link link = new Link(LinkType.EXTENSION);
                    link.addLinkElements(useCaseDiagram, element1, element2);
                    useCaseDiagram.getLinks().add(link);
                }
            }
        }

        useCaseDiagram.addSetElements(useCaseDiagram.getUseCases(), useCaseDiagram.getUseCaseLookup());
        useCaseDiagram.setUseCasesCount(useCaseDiagram.getUseCases().size());
        useCaseDiagram.addSetElements(useCaseDiagram.getActors(), useCaseDiagram.getActorLookup());
        useCaseDiagram.setActorsCount(useCaseDiagram.getActors().size());
        useCaseDiagram.setElements(useCaseDiagram.getUseCasesCount() + useCaseDiagram.getActorsCount());
        useCaseDiagram.setLinksCount(useCaseDiagram.getLinks().size());

        return useCaseDiagram;
    }

}