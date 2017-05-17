package nl.ou.abi.GraphsMatchingSpike;

import nl.ou.dpd.domain.edge.Edge;
import nl.ou.dpd.domain.edge.Relation;
import nl.ou.dpd.domain.node.Clazz;
import nl.ou.dpd.domain.node.Interface;
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

    private SystemUnderConsiderationGraph suc = new SystemUnderConsiderationGraph("SystemUnderConsideration");
    private DesignPatternGraph dp = new DesignPatternGraph("TemplateMethod");

    @Before
    public void init() {
        final Node superClassSys = createPublicAbstractClass("super");
        final Node subClassSys = createPublicClass("sub");
        suc.addVertex(superClassSys);
        suc.addVertex(subClassSys);
        suc.addEdge(superClassSys, subClassSys);

        final Node superClassDp = createPublicAbstractClass("AbstractClass");
        final Node subClassDp = createPublicClass("ConcreteClass");
        dp.addVertex(superClassDp);
        dp.addVertex(subClassDp);
        dp.addEdge(superClassDp, subClassDp);

        when(vertexComparator.compare(superClassSys, superClassDp)).thenReturn(0);
        when(vertexComparator.compare(subClassSys, subClassDp)).thenReturn(0);
        when(edgeComparator.compare(any(Relation.class), any(Relation.class))).thenReturn(0);
    }

    @Test
    public void testSystemUnderConsideration() {
        final Relation relation = suc.edgeSet().stream().findFirst().orElse(null);
        assertThat(relation.getLeftNode().getName(), is("super"));
        assertThat(relation.getRightNode().getName(), is("sub"));
    }

    @Test
    public void testDesignPattern() {
        final Relation relation = dp.edgeSet().stream().findFirst().orElse(null);
        assertThat(relation.getLeftNode().getName(), is("AbstractClass"));
        assertThat(relation.getRightNode().getName(), is("ConcreteClass"));
    }

    @Test
    public void testMatch() {
        VF2SubgraphIsomorphismInspector<Node, Relation> inspector = new VF2SubgraphIsomorphismInspector<>(suc, dp, vertexComparator, edgeComparator);
        assertTrue(inspector.isomorphismExists());
    }

    @Test
    public void testNoEdgeMatch() {
        when(edgeComparator.compare(any(Relation.class), any(Relation.class))).thenReturn(1);
        VF2SubgraphIsomorphismInspector<Node, Relation> inspector = new VF2SubgraphIsomorphismInspector<>(suc, dp, vertexComparator, edgeComparator);
        assertFalse(inspector.isomorphismExists());
    }

    @Test
    public void testNoNodeMatch() {
        when(vertexComparator.compare(any(Node.class), any(Node.class))).thenReturn(1);
        VF2SubgraphIsomorphismInspector<Node, Relation> inspector = new VF2SubgraphIsomorphismInspector<>(suc, dp, vertexComparator, edgeComparator);
        assertFalse(inspector.isomorphismExists());
    }

    private Node createPublicClass(String name) {
        return new Clazz(name, name, Visibility.PUBLIC, null, false);
    }

    private Node createPublicAbstractClass(String name) {
        return new Clazz(name, name, Visibility.PUBLIC, null, true);
    }

}
