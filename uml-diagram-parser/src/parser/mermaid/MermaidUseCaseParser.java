package parser.mermaid;

import enums.DiagramType;
import enums.Language;
import enums.LinkType;
import enums.NodeType;
import model.diagrams.UseCaseDiagram;
import model.elements.UseCaseNode;
import model.relationships.Link;

import java.util.List;
import java.util.regex.Pattern;

public class MermaidUseCaseParser {

    private static final String ELEMENT_PATTERN = "^[A-Za-z0-9]+(\\([\\s\\S]+\\)|\\[[\\s\\S]+\\])$";
    private static final List<String> LINK_PATTERNS = List.of(
            "^\\S+\\s*-[\\s\\S]*->?[\\S\\s]*\\S+$",
            "^\\S+\\s*=[\\s\\S]*=>[\\S\\s]*\\S+$"
    );
    private static final List<String> LINK_START = List.of(
            "-",
            "="
    );
    private static final String LINK_END = ">";
    private static final List<String> NAME_START = List.of("(", "[");

    // Metoda pentru interpretarea diagramei cazurilor de utilizare in limbajul Mermaid

    public static UseCaseDiagram parseUseCaseDiagram(List<String> lines, Language language, DiagramType type) {
        UseCaseDiagram useCaseDiagram = new UseCaseDiagram();
        useCaseDiagram.setLanguage(language);
        useCaseDiagram.setType(type);
        useCaseDiagram.setLinesCount(lines.size());

        for (String line : lines) {
            if (!line.isBlank()) {
                String trimmedLine = line.trim();

                // Identifica elementele (cazuri de utilizare si actori)

                checkElement(useCaseDiagram, trimmedLine);
            }
        }

        for (String line : lines) {
            if (!line.isBlank()) {
                String trimmedLine = line.trim();

                // Identifica relatiile dintre elemente

                checkLink(useCaseDiagram, trimmedLine);
            }
        }

        useCaseDiagram.addSetElements(useCaseDiagram.getDiagramElements(), useCaseDiagram.getElementLookup());
        useCaseDiagram.setElements(useCaseDiagram.getDiagramElements().size());
        useCaseDiagram.setLinksCount(useCaseDiagram.getLinks().size());
        useCaseDiagram.setRelationships(useCaseDiagram.getLinksCount());

        return useCaseDiagram;
    }

    private static LinkType getLinkType(String link) {
        if (link.contains("<<extend>>")) {
            return LinkType.EXTEND;
        }
        if (link.contains("<<include>>")) {
            return LinkType.INCLUDE;
        }

        return LinkType.LINK;
    }

    private static boolean isElement(String line) {
        return Pattern.matches(ELEMENT_PATTERN, line);
    }

    private static boolean isLink(String line) {
        return (Pattern.matches(LINK_PATTERNS.get(0), line) || Pattern.matches(LINK_PATTERNS.get(1), line));
    }

    private static void checkElement(UseCaseDiagram useCaseDiagram, String trimmedLine) {
        if (isElement(trimmedLine)) {
            int index = trimmedLine.contains(NAME_START.get(0)) ? trimmedLine.indexOf(NAME_START.get(0)) : trimmedLine.indexOf(NAME_START.get(1));
            String elementAlias = trimmedLine.substring(0, index).trim();
            String elementName = MermaidParser.getMermaidElementName(trimmedLine.substring(index + 1));
            UseCaseNode useCaseNode = new UseCaseNode(elementName, elementAlias, NodeType.ELEMENT);
            useCaseDiagram.addUseCaseNode(useCaseNode, useCaseDiagram.getElementLookup());
        }
    }

    private static void checkLink(UseCaseDiagram useCaseDiagram, String trimmedLine) {
        if (isLink(trimmedLine)) {
            int linkStartIndex = trimmedLine.contains(LINK_START.get(0)) ? trimmedLine.indexOf(LINK_START.get(0)) : trimmedLine.indexOf(LINK_START.get(1));
            int linkEndIndex = trimmedLine.contains(LINK_END) ? trimmedLine.indexOf(LINK_END) : trimmedLine.lastIndexOf(LINK_START.get(0));
            linkEndIndex= trimmedLine.contains("|") ? trimmedLine.lastIndexOf("|") : linkEndIndex;
            String element1 = MermaidParser.getMermaidElementName(trimmedLine.substring(0, linkStartIndex));
            String element2 = MermaidParser.getMermaidElementName(trimmedLine.substring(linkEndIndex));
            Link link = new Link(getLinkType(trimmedLine));
            link.addLinkElements(useCaseDiagram, element1, element2);
            useCaseDiagram.getLinks().add(link);
        }
    }
}