package nl.ou.abi.GraphsMatchingSpike;

import nl.ou.dpd.domain.edge.Edge;
import nl.ou.dpd.domain.edge.Edges;
import nl.ou.dpd.domain.node.Node;
/*
import nl.ou.dpd.domain.rule.Condition;
import nl.ou.dpd.domain.rule.Conditions;
*/
import java.util.List;

import org.jgrapht.graph.DefaultDirectedGraph;

/**
 * Represents an design pattern. The structur of a design pattern is defined by its {@link Edges}. Apart form structure
 * a design pattern has a set of {@link Condition}s defining its character.
 *
 * @author Martin de Boer
 */
public class DesignPattern extends DefaultDirectedGraph<Node, Edge>{

    private final String name;
//    private final Conditions conditions;

    public DesignPattern(String name) {
    	super(new EdgeOfNodesFactory());
        this.name = name;
//        this.conditions = new Conditions();
    }

    public String getName() {
        return name;
    }
/*
    public Conditions getConditions() {
        return conditions;
    }
*/
 
    /**
     * This method orders the array of {@link Edge}'s. It guarantees that every edge in the graph has at least one
     * vertex that is also present in one or more preceding edges. One exception to this rule is the first edge in the
     * graph, obviously because it has no preceding edge. In other words: for every edge E(v1 -> v2) in the graph
     * (except the first one), a previous edge E(v1 -> x2), E(x1 -> v1), E(x1 -> v2) or E(v2 -> x2) is present. This way
     * every edge is connected to a vertex of a preceding edge.
     * <p/>
     * Example: A->B, C->D, A->C becomes A->B, A->C, C->D.
     */
/*    void order() {
        order(getEdges(), 0, 1, 2);
    }
*/
/*    private void order(List<Edge> graph, int base, int switchable, int start) {
        for (int i = start; i < graph.size(); i++) {
            final boolean switchableConnected = isConnectedToPrecedingEdge(graph, switchable, base);
            final boolean currentConnected = isConnectedToPrecedingEdge(graph, i, base);
            if (currentConnected && !switchableConnected) {
                switchEdges(graph, i, switchable);
                order(graph, ++base, ++switchable, ++start);
            }
        }
    }
*/
/*    private boolean isConnectedToPrecedingEdge(List<Edge> graph, int test, int base) {
        for (int i = 0; i <= base; i++) {
            if (areEdgesConnected(graph.get(test), graph.get(i))) {
                return true;
            }
        }
        return false;
    }
*/
/*    private void switchEdges(List<Edge> graph, int i, int j) {
        final Edge temp = graph.get(j);
        graph.set(j, graph.get(i));
        graph.set(i, temp);
    }
*/
/*    private boolean areEdgesConnected(Edge edge1, Edge edge2) {
        final Node v1 = edge1.getLeftNode();
        final Node v2 = edge1.getRightNode();
        final Node v3 = edge2.getLeftNode();
        final Node v4 = edge2.getRightNode();
        return v1.equals(v3) || v1.equals(v4) || v2.equals(v3) || v2.equals(v4);
    }
*/
}