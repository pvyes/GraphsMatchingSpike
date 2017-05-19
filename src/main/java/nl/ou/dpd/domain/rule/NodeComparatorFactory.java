package nl.ou.dpd.domain.rule;

import nl.ou.dpd.domain.edge.Relation;
import nl.ou.dpd.domain.node.Node;

import java.util.Comparator;

/**
 * Created by martindeboer on 19-05-17.
 */
public class NodeComparatorFactory {

    public static Comparator<Node> createVisibilityComparator() {
        return new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.getVisibility() == o2.getVisibility() ? 0 : 1;
            }
        };
    }

    public static Comparator<Node> createAbstractModifierComparator() {
        return new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.isAbstract() == o2.isAbstract() ? 0 : 1;
            }
        };
    }

}
