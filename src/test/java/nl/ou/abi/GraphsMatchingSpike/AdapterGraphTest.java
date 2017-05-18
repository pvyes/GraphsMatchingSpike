package nl.ou.abi.GraphsMatchingSpike;

import nl.ou.dpd.domain.edge.Cardinality;
import nl.ou.dpd.domain.edge.Relation;
import nl.ou.dpd.domain.edge.RelationType;
import nl.ou.dpd.domain.node.Clazz;
import nl.ou.dpd.domain.node.Node;
import nl.ou.dpd.domain.node.Visibility;
import org.jgrapht.alg.isomorphism.VF2SubgraphIsomorphismInspector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Comparator;

import static junit.framework.TestCase.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AdapterGraphTest {

    private DesignPatternGraph adapterPattern;
    private Node client;
    private Node target;
    private Node adapter;
    private Node adaptee;

    private SystemUnderConsiderationGraph systemUnderConsideration;
    private Node klant;
    private Node doel;
    private Node aanpasser;
    private Node aangepaste;

    private Comparator<Node> nodecomparator = new NodeComparator();
    private Comparator<Relation> relationcomparator = new RelationComparator();

    @Before
    public void initPattern() {
        adapterPattern = new DesignPatternGraph("Adapter");

        client = createPublicClass("Client");
        target = createPublicAbstractClass("Target");
        adapter = createPublicClass("Adapter");
        adaptee = createPublicClass("Adaptee");

        adapterPattern.addVertex(client);
        adapterPattern.addVertex(target);
        adapterPattern.addVertex(adapter);
        adapterPattern.addVertex(adaptee);

        adapterPattern.addEdge(client, target)
                .setRelationType(RelationType.ASSOCIATION_DIRECTED)
                .setCardinalityLeft(Cardinality.valueOf("1"))
                .setCardinalityRight(Cardinality.valueOf("1"));
        adapterPattern.addEdge(adapter, target)
                .setRelationType(RelationType.INHERITANCE)
                .setCardinalityLeft(Cardinality.valueOf("1"))
                .setCardinalityRight(Cardinality.valueOf("1"));
        adapterPattern.addEdge(adapter, adaptee)
                .setRelationType(RelationType.ASSOCIATION_DIRECTED)
                .setCardinalityLeft(Cardinality.valueOf("1"))
                .setCardinalityRight(Cardinality.valueOf("1"));
    }

    @Before
    public void initSystemUnderConsideration() {
        systemUnderConsideration = new SystemUnderConsiderationGraph("Systeem");

        klant = createPublicClass("Klant");
        doel = createPublicAbstractClass("Doel");
        aanpasser = createPublicClass("Aanpasser");
        aangepaste = createPublicClass("Aangepaste");

        systemUnderConsideration.addVertex(klant);
        systemUnderConsideration.addVertex(doel);
        systemUnderConsideration.addVertex(aanpasser);
        systemUnderConsideration.addVertex(aangepaste);

        systemUnderConsideration.addEdge(klant, doel)
                .setRelationType(RelationType.ASSOCIATION_DIRECTED)
                .setCardinalityLeft(Cardinality.valueOf("1"))
                .setCardinalityRight(Cardinality.valueOf("1"));
        systemUnderConsideration.addEdge(aanpasser, doel)
                .setRelationType(RelationType.INHERITANCE)
                .setCardinalityLeft(Cardinality.valueOf("1"))
                .setCardinalityRight(Cardinality.valueOf("1"));
        systemUnderConsideration.addEdge(aanpasser, aangepaste)
                .setRelationType(RelationType.ASSOCIATION_DIRECTED)
                .setCardinalityLeft(Cardinality.valueOf("1"))
                .setCardinalityRight(Cardinality.valueOf("1"));
    }

    @Test
    public void testMatch() {
        VF2SubgraphIsomorphismInspector<Node, Relation> inspector = new VF2SubgraphIsomorphismInspector<>(
                systemUnderConsideration,
                adapterPattern,
                nodecomparator,
                relationcomparator);
        assertTrue(inspector.isomorphismExists());
    }

    private Node createPublicClass(String name) {
        return new Clazz(name, name, Visibility.PUBLIC, null, false);
    }

    private Node createPublicAbstractClass(String name) {
        return new Clazz(name, name, Visibility.PUBLIC, null, true);
    }

    /**
     * Compares two {@link Relation}s.
     */
    private class RelationComparator implements Comparator<Relation> {
        @Override
        public int compare(Relation o1, Relation o2) {
            final boolean sameRelationType = o1.getRelationType().equals(o2.getRelationType());
            final boolean sameCardinalityLeft = o1.getCardinalityLeft().equals(o2.getCardinalityLeft());
            final boolean sameCardinalityRight = o1.getCardinalityRight().equals(o2.getCardinalityRight());
            final boolean same = sameRelationType && sameCardinalityLeft && sameCardinalityRight;
            if (same) {
                String output = String.format("%s -> %s matches %s -> %s.",
                        adapterPattern.getEdgeSource(o1).getName(),
                        adapterPattern.getEdgeTarget(o1).getName(),
                        systemUnderConsideration.getEdgeSource(o2).getName(),
                        systemUnderConsideration.getEdgeTarget(o2).getName());
                System.out.println(output);
                return 0;
            }
            return -1;
        }
    }

    /**
     * Compares two {@link Node}s.
     */
    private class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return 0;
        }
    }
}
