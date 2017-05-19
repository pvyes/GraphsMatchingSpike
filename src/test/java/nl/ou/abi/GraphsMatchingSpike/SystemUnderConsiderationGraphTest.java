package nl.ou.abi.GraphsMatchingSpike;

import nl.ou.dpd.domain.edge.Cardinality;
import nl.ou.dpd.domain.edge.Relation;
import nl.ou.dpd.domain.node.Clazz;
import nl.ou.dpd.domain.node.Node;
import nl.ou.dpd.domain.node.Visibility;
import org.jgrapht.alg.isomorphism.VF2SubgraphIsomorphismInspector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Comparator;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SystemUnderConsiderationGraphTest {

    @Mock
    Comparator<Node> vertexComparator;
    @Mock
    Comparator<Relation> edgeComparator;

    private SystemUnderConsiderationGraph suc;
    private Node superClassSys;
    private Node subClassSys;

    private DesignPatternGraph dp;
    private Node superClassDp;
    private Node subClassDp;

    @Before
    public void initSystemUnderConsideration() {
        suc = new SystemUnderConsiderationGraph("SystemUnderConsideration");
        final Node superClassSys = createPublicAbstractClass("super");
        final Node subClassSys = createPublicClass("sub");

        suc.addVertex(superClassSys);
        suc.addVertex(subClassSys);
        final Relation sucRelation = suc.addEdge(superClassSys, subClassSys);

        sucRelation.setCardinalityLeft(Cardinality.valueOf("1"));
        sucRelation.setCardinalityRight(Cardinality.valueOf("*"));
    }

    @Before
    public void initDesignPattern() {
        dp = new DesignPatternGraph("TemplateMethod");
        superClassDp = createPublicAbstractClass("AbstractClass");
        subClassDp = createPublicClass("ConcreteClass");

        final Relation dpRelation = new Relation("A", "B");
        dpRelation.setCardinalityLeft(Cardinality.valueOf("2"));
        dpRelation.setCardinalityRight(Cardinality.valueOf("2"));

        dp.addVertex(superClassDp);
        dp.addVertex(subClassDp);
        dp.addEdge(superClassDp, subClassDp, dpRelation);
    }

    @Before
    public void initComparators() {
        when(vertexComparator.compare(superClassSys, superClassDp)).thenReturn(0);
        when(vertexComparator.compare(subClassSys, subClassDp)).thenReturn(0);
        when(edgeComparator.compare(any(Relation.class), any(Relation.class))).thenReturn(0);
        dp.setRelationComparator(edgeComparator);
        dp.setNodeComparator(vertexComparator);
    }

    @Test
    public void testSystemUnderConsideration() {
        final Relation relation = suc.edgeSet().stream().findFirst().orElse(null);
        assertThat(suc.getEdgeSource(relation).getName(), is("super"));
        assertThat(suc.getEdgeTarget(relation).getName(), is("sub"));

        // Check if the default id and name are set by the relation factory
        assertThat(relation.getId(), is("super-sub"));
        assertThat(relation.getName(), is("super-sub"));

        // Check the cardinality set after creating the edge in the graph
        assertThat(relation.getCardinalityLeft(), is(Cardinality.valueOf("1")));
        assertThat(relation.getCardinalityRight(), is(Cardinality.valueOf("*")));
    }

    @Test
    public void testDesignPattern() {
        final Relation relation = dp.edgeSet().stream().findFirst().orElse(null);
        assertThat(dp.getEdgeSource(relation).getName(), is("AbstractClass"));
        assertThat(dp.getEdgeTarget(relation).getName(), is("ConcreteClass"));

        // Check if the name and id are taken from the relation passed to the addEdge method
        assertThat(relation.getId(), is("A"));
        assertThat(relation.getName(), is("B"));

        // Check the cardinality that was set before creating the edge in the graph
        assertThat(relation.getCardinalityLeft(), is(Cardinality.valueOf("2")));
        assertThat(relation.getCardinalityRight(), is(Cardinality.valueOf("2")));
    }

    @Test
    public void testMatch() {
        assertTrue(dp.match(suc));
    }

    @Test
    public void testNoEdgeMatch() {
        when(edgeComparator.compare(any(Relation.class), any(Relation.class))).thenReturn(1);
        assertFalse(dp.match(suc));
    }

    @Test
    public void testNoNodeMatch() {
        when(vertexComparator.compare(any(Node.class), any(Node.class))).thenReturn(1);
        assertFalse(dp.match(suc));
    }

    private Node createPublicClass(String name) {
        return new Clazz(name, name, Visibility.PUBLIC, null, false);
    }

    private Node createPublicAbstractClass(String name) {
        return new Clazz(name, name, Visibility.PUBLIC, null, true);
    }

}
