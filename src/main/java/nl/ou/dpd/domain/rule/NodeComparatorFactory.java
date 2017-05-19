package nl.ou.dpd.domain.rule;

import nl.ou.abi.GraphsMatchingSpike.DesignPatternGraph;
import nl.ou.dpd.domain.edge.Relation;
import nl.ou.dpd.domain.node.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by martindeboer on 19-05-17.
 */
public class NodeComparatorFactory {

    public static Comparator<Node> createMasterComparator() {
        return new CompoundNodeComparator();
    }

    private static Comparator<Node> createVisibilityComparator() {
        return new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.getVisibility() == o2.getVisibility() ? 0 : 1;
            }
        };
    }

    private static Comparator<Node> createAbstractModifierComparator() {
        return new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.isAbstract() == o2.isAbstract() ? 0 : 1;
            }
        };
    }

    private static class CompoundNodeComparator implements Comparator<Node> {
        private List<Comparator<Node>> comparators = new ArrayList<>();

        public CompoundNodeComparator() {
            comparators.add(createVisibilityComparator());
            comparators.add(createAbstractModifierComparator());
        }

        @Override
        public int compare(Node o1, Node o2) {
            return (int) comparators.stream()
                    .filter(comparator -> comparator.compare(o1, o2) != 0)
                    .count();
        }
    }
}
