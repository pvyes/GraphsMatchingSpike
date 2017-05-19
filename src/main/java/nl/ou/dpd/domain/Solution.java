package nl.ou.dpd.domain;

import nl.ou.dpd.domain.node.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

    private final String designPatternName;
    private final List<Node[]> matchingNodes;

    public Solution(String designPatternName) {
        this.matchingNodes = new ArrayList<>();
        this.designPatternName = designPatternName;
    }

    public String getDesignPatternName() {
        return designPatternName;
    }

    public List<Node[]> getMatchingNodes() {
        return matchingNodes;
    }

    public List<String[]> getMatchingNodeNames() {
        return getMatchingNodes().stream()
                .map(nodes -> new String[]{nodes[0].getName(), nodes[1].getName()})
                .collect(Collectors.toList());
    }

    public Solution addMatchingNodes(Node patternNode, Node systemNode) {
        if (!previouslyAddedMatchingNodes(patternNode, systemNode)) {
            matchingNodes.add(new Node[]{patternNode, systemNode});
        }
        return this;
    }

    private boolean previouslyAddedMatchingNodes(Node patternNode, Node systemNode) {
        for (Node[] nodes : getMatchingNodes()) {
            if (patternNode.equals(nodes[0]) && systemNode.equals(nodes[1])) {
                return true;
            }
        }
        return false;
    }
}
