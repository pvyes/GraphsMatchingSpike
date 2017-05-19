package nl.ou.abi.GraphsMatchingSpike;

import nl.ou.dpd.domain.edge.Relation;
import nl.ou.dpd.domain.node.Node;

import java.util.Comparator;

public class TestHelper {

    // TODO: Create a factory for the several types of comparators?

    public static Comparator<Relation> getCardinalityComparator(final DesignPatternGraph pattern) {
        return new Comparator<Relation>() {
            @Override
            public int compare(Relation o1, Relation o2) {
                final boolean sameCardinalityLeft = o1.getCardinalityLeft().equals(o2.getCardinalityLeft());
                final boolean sameCardinalityRight = o1.getCardinalityRight().equals(o2.getCardinalityRight());
                final boolean same = sameCardinalityLeft && sameCardinalityRight;
                return same ? 0 : 1;
            }
        };
    }

    public static Comparator<Relation> getRelationTypeComparator(final DesignPatternGraph pattern) {
        return new Comparator<Relation>() {
            @Override
            public int compare(Relation o1, Relation o2) {
                final boolean sameRelationType = o1.getRelationType() == o2.getRelationType();
                return sameRelationType ? 0 : 1;
            }
        };
    }

    public static Comparator<Node> getVisibilityComparator(final DesignPatternGraph pattern) {
        return new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.getVisibility() == o2.getVisibility() ? 0 : 1;
            }
        };
    }

    public static Comparator<Node> getAbstractModifierComparator(final DesignPatternGraph pattern) {
        return new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.isAbstract() == o2.isAbstract() ? 0 : 1;
            }
        };
    }
}
