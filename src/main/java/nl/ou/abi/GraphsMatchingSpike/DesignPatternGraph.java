package nl.ou.abi.GraphsMatchingSpike;

import nl.ou.dpd.domain.edge.Relation;
import nl.ou.dpd.domain.node.Node;
import nl.ou.dpd.domain.rule.CompoundComparator;
import org.jgrapht.alg.isomorphism.VF2SubgraphIsomorphismInspector;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.util.Comparator;

public class DesignPatternGraph extends DefaultDirectedGraph<Node, Relation> {

    final private String name;

    private CompoundComparator<Relation> relationComparator;
    private CompoundComparator<Node> nodeComparator;

    public DesignPatternGraph(String name) {
        super(new RelationFactory());
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public CompoundComparator<Relation> getRelationComparator() {
        return relationComparator;
    }

    public void setRelationComparator(CompoundComparator<Relation> relationComparator) {
        this.relationComparator = relationComparator;
    }

    public CompoundComparator<Node> getNodeComparator() {
        return nodeComparator;
    }

    public void setNodeComparator(CompoundComparator<Node> nodeComparator) {
        this.nodeComparator = nodeComparator;
    }

    public boolean match(SystemUnderConsiderationGraph systemUnderConsideration) {
        final VF2SubgraphIsomorphismInspector<Node, Relation> inspector = new VF2SubgraphIsomorphismInspector<>(
                systemUnderConsideration,
                this,
                getNodeComparator(),
                getRelationComparator());
        return inspector.isomorphismExists();
    }

}
