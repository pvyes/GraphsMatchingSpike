package nl.ou.abi.GraphsMatchingSpike;

import nl.ou.dpd.domain.edge.Relation;
import nl.ou.dpd.domain.node.Node;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.util.Comparator;

public class DesignPatternGraph extends DefaultDirectedGraph<Node, Relation> {

    private String name;

    private Comparator<Relation> relationComparator;
    private Comparator<Node> nodeComparator;

    public DesignPatternGraph(String name) {
        super(new RelationFactory());
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
