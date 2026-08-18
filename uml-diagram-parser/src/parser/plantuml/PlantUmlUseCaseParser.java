package parser.plantuml;

import enums.DiagramType;
import enums.Language;
import enums.LinkType;
import enums.NodeType;
import model.diagrams.UseCaseDiagram;
import model.elements.UseCaseNode;
import model.relationships.Link;

import java.util.List;
import java.util.regex.Pattern;

public class PlantUmlUseCaseParser {

    private static final String LINK_PATTERN = "^.+-[a-z\\-]*>.+$";
    private static final String EXTENSION_PATTERN = "^.+<\\|--.+$";
    private static final String INCLUDE_PATTERN = "^.+\\.>.+:\\s*<<include>>\\s*$";
    private static final String EXTEND_PATTERN = "^.+\\.>.+:\\s*<<extend>>\\s*$";
    private static final String USE_CASE_DEFINITION = "^usecase [\\s\\S]+$";
    public static final String USE_CASE_PATTERN = "^\\([\\s\\S]+\\).*$";
    private static final String ACTOR_DEFINITION = "^actor [\\s\\S]+$";
    private static final String ACTOR_PATTERN = "^:[\\s\\S]+:.*$";
    private static final String ALIAS_PATTERN = "^.+ as .+$";
    private static final String USECASE = "usecase";
    private static final String ACTOR = "actor";
    private static final String ALIAS= " as ";

    // Metoda pentru interpretarea diagramei cazurilor de utilizare in limbajul PlantUML

    public static UseCaseDiagram parseUseCaseDiagram(List<String> lines, Language language, DiagramType type) {
        UseCaseDiagram useCaseDiagram = new UseCaseDiagram();
        useCaseDiagram.setLanguage(language);
        useCaseDiagram.setType(type);
        useCaseDiagram.setLinesCount(lines.size());

        for (String line : lines) {

            if (!line.isBlank()) {
                String trimmedLine = line.trim();

                // Identifica un caz de utilizare

                if (Pattern.matches(USE_CASE_DEFINITION, trimmedLine)) {
                    int nameStart = USECASE.length();
                    int nameEnd = trimmedLine.length();
                    int aliasStart = trimmedLine.length();

                    if (Pattern.matches(ALIAS_PATTERN, trimmedLine)) {
                        nameEnd = trimmedLine.indexOf(ALIAS);
                        aliasStart = nameEnd + ALIAS.length();
                    }

                    String useCaseName = trimmedLine.substring(nameStart, nameEnd).replace("\"", "").trim();
                    String useCaseAlias = trimmedLine.substring(aliasStart);
                    UseCaseNode newUseCase = new UseCaseNode(useCaseName, useCaseAlias, NodeType.USECASE);
                    useCaseDiagram.addUseCaseNode(newUseCase, useCaseDiagram.getUseCaseLookup());
                }

                if (Pattern.matches(USE_CASE_PATTERN, trimmedLine)) {
                    int nameStart = trimmedLine.indexOf("(");
                    int nameEnd = trimmedLine.length();
                    int aliasStart = trimmedLine.length();

                    if (Pattern.matches(ALIAS_PATTERN, trimmedLine)) {
                        nameEnd = trimmedLine.indexOf(ALIAS);
                        aliasStart = nameEnd + ALIAS.length();
                    }

                    String useCaseName = trimmedLine.substring(nameStart, nameEnd).replace("(", "").replace(")", ""). trim();
                    String useCaseAlias = trimmedLine.substring(aliasStart);
                    UseCaseNode newUseCase = new UseCaseNode(useCaseName, useCaseAlias, NodeType.USECASE);
                    useCaseDiagram.addUseCaseNode(newUseCase, useCaseDiagram.getUseCaseLookup());
                }

                // Identifica un actor

                if (Pattern.matches(ACTOR_DEFINITION, trimmedLine)) {
                    int nameStart = ACTOR.length();
                    int nameEnd = trimmedLine.length();
                    int aliasStart = trimmedLine.length();

                    if (Pattern.matches(ALIAS_PATTERN, trimmedLine)) {
                        nameEnd = trimmedLine.indexOf(ALIAS);
                        aliasStart = nameEnd + ALIAS.length();
                    }

                    String actorName = trimmedLine.substring(nameStart, nameEnd).replace("\"", "").trim();
                    String actorAlias = trimmedLine.substring(aliasStart);
                    UseCaseNode newActor = new UseCaseNode(actorName, actorAlias, NodeType.ACTOR);
                    useCaseDiagram.addUseCaseNode(newActor, useCaseDiagram.getActorLookup());
                }

                if (Pattern.matches(ACTOR_PATTERN, trimmedLine)) {
                    int nameStart = trimmedLine.indexOf(":");
                    int nameEnd = trimmedLine.length();
                    int aliasStart = trimmedLine.length();

                    if (Pattern.matches(ALIAS_PATTERN, trimmedLine)) {
                        nameEnd = trimmedLine.indexOf(ALIAS);
                        aliasStart = nameEnd + ALIAS.length();
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

                if (Pattern.matches(LINK_PATTERN, trimmedLine)) {
                    int linkStart = trimmedLine.indexOf(" -");
                    int linkEnd = trimmedLine.indexOf("> ");
                    String element1 = trimmedLine.substring(0, linkStart).trim();
                    String element2 = trimmedLine.substring(linkEnd + 1).trim();
                    Link link = new Link(LinkType.LINK);
                    link.addLinkElements(useCaseDiagram, element1, element2);
                    useCaseDiagram.getLinks().add(link);
                }

                if (Pattern.matches(EXTEND_PATTERN, trimmedLine)) {
                    int linkStart = trimmedLine.indexOf(" .");
                    int linkEnd = trimmedLine.indexOf("> ");
                    int index = trimmedLine.indexOf("<<extend>>");
                    String element1 = trimmedLine.substring(0, linkStart).trim();
                    String element2 = trimmedLine.substring(linkEnd + 1, index).trim();
                    Link link = new Link(LinkType.EXTEND);
                    link.addLinkElements(useCaseDiagram, element1, element2);
                    useCaseDiagram.getLinks().add(link);
                }

                if (Pattern.matches(INCLUDE_PATTERN, trimmedLine)) {
                    int linkStart = trimmedLine.indexOf(" .");
                    int linkEnd = trimmedLine.indexOf("> ");
                    int index = trimmedLine.indexOf("<<include>>");
                    String element1 = trimmedLine.substring(0, linkStart).trim();
                    String element2 = trimmedLine.substring(linkEnd + 1, index).trim();
                    Link link = new Link(LinkType.INCLUDE);
                    link.addLinkElements(useCaseDiagram, element1, element2);
                    useCaseDiagram.getLinks().add(link);
                }

                if (Pattern.matches(EXTENSION_PATTERN, trimmedLine)) {
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
