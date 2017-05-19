package nl.ou.abi.GraphsMatchingSpike;

import nl.ou.dpd.domain.Feedback;
import nl.ou.dpd.domain.edge.Cardinality;
import nl.ou.dpd.domain.edge.Relation;
import nl.ou.dpd.domain.edge.RelationType;
import nl.ou.dpd.domain.node.Clazz;
import nl.ou.dpd.domain.node.Node;
import nl.ou.dpd.domain.node.Visibility;
import nl.ou.dpd.domain.rule.NodeComparatorFactory;
import nl.ou.dpd.domain.rule.Operation;
import nl.ou.dpd.domain.rule.RelationComparatorFactory;
import nl.ou.dpd.domain.rule.Scope;
import nl.ou.dpd.domain.rule.Topic;
import org.jgrapht.alg.isomorphism.VF2SubgraphIsomorphismInspector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

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

    private Feedback feedback;

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

        adapterPattern.setRelationComparator(RelationComparatorFactory.createCompoundRelationComparator());
        adapterPattern.getRelationComparator().addComparator(RelationComparatorFactory.createRelationComparator(Scope.RELATION, Topic.TYPE, Operation.EQUALS));
        adapterPattern.getRelationComparator().addComparator(RelationComparatorFactory.createRelationComparator(Scope.RELATION, Topic.CARDINALITY_LEFT, Operation.EQUALS));
        adapterPattern.getRelationComparator().addComparator(RelationComparatorFactory.createRelationComparator(Scope.RELATION, Topic.CARDINALITY_RIGHT, Operation.EQUALS));

        adapterPattern.setNodeComparator(
                NodeComparatorFactory.createCompoundNodeComparator(
                        NodeComparatorFactory.createNodeComparator(Scope.OBJECT, Topic.MODIFIER_ABSTRACT, Operation.EQUALS)
                )
        );


        feedback = new Feedback(adapterPattern.getName());
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
        assertTrue(adapterPattern.match(systemUnderConsideration));

//        final List<String[]> matchingNodeNames = feedback.getMatchingNodeNames();
//        assertContains(matchingNodeNames, new String[]{"Client", "Klant"});
//        assertContains(matchingNodeNames, new String[]{"Target", "Doel"});
//        assertContains(matchingNodeNames, new String[]{"Adapter", "Aanpasser"});
//        assertContains(matchingNodeNames, new String[]{"Adaptee", "Aangepaste"});
//        assertThat(matchingNodeNames.size(), is(4));
    }

    private void assertContains(List<String[]> actualNames, String[] expectedNames) {
        String[] found = actualNames.stream()
                .filter(names -> names[0].equals(expectedNames[0]) && names[1].equals(expectedNames[1]))
                .findFirst().orElse(null);
        assertTrue(found != null);
    }

    private Node createPublicClass(String name) {
        return new Clazz(name, name, Visibility.PUBLIC, null, false);
    }

    private Node createPublicAbstractClass(String name) {
        return new Clazz(name, name, Visibility.PUBLIC, null, true);
    }

}
