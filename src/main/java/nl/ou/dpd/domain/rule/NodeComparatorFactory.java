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

    public static Comparator<Node> createNodeComparator() {
        return new CompoundNodeComparator();
    }

    private static Comparator<Node> createVisibilityComparator() {
        return new Comparator<Node>() {
            @Override
            public int compare(Node systemNode, Node patternNode) {
                return systemNode.getVisibility() == patternNode.getVisibility() ? 0 : 1;
            }
        };
    }

    private static Comparator<Node> createAbstractModifierComparator() {
        return new Comparator<Node>() {
            @Override
            public int compare(Node systemNode, Node patternNode) {
                return systemNode.isAbstract() == patternNode.isAbstract() ? 0 : 1;
            }
        };
    }

    private static class CompoundNodeComparator implements Comparator<Node> {
        private List<Comparator<Node>> comparators = new ArrayList<>();

        private CompoundNodeComparator() {
            comparators.add(createVisibilityComparator());
            comparators.add(createAbstractModifierComparator());
        }

        @Override
        public int compare(Node systemNode, Node patternNode) {
            return (int) comparators.stream()
                    .filter(comparator -> comparator.compare(systemNode, patternNode) != 0)
                    .count();
        }
    }
}
