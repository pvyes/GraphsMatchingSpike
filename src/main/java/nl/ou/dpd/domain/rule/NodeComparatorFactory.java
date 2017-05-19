package nl.ou.dpd.domain.rule;

import nl.ou.dpd.domain.node.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by martindeboer on 19-05-17.
 */
public class NodeComparatorFactory {

    public static Comparator<Node> createCompoundNodeComparator(Comparator<Node>... subComparators) {
        return new CompoundNodeComparator(subComparators);
    }

    public static Comparator<Node> createNodeComparator(Scope scope, Topic topic, Operation operation) {
        if (scope != Scope.OBJECT) {
            throw new RuleException(String.format("Unknown scope: %s.", scope));
        }
        return createObjectNodeComparator(topic, operation);
    }

    private static Comparator<Node> createObjectNodeComparator(Topic topic, Operation operation) {
        switch (topic) {
            case TYPE:
                return createObjectTypeNodeComparator(operation);
            case VISIBILITY:
                return creatObjectVisibilityNodeComparator(operation);
            case MODIFIER_ABSTRACT:
                return createObjectModifierAbstractNodeComparator(operation);
            default:
                throw new RuleException(String.format("Unknown topic: %s.", topic));
        }
    }

    private static Comparator<Node> createObjectTypeNodeComparator(Operation operation) {
        switch (operation) {
            case EQUALS:
                return createObjectTypeEqualsNodeComparator();
            case NOT_EQUALS:
                return createObjectTypeNotEqualsNodeComparator();
            case EXISTS:
                return createObjectTypeExistsNodeComparator();
            case NOT_EXISTS:
                return createObjectTypeNotExistsNodeComparator();
            default:
                throw new RuleException(String.format("Unknown operation: %s.", operation));
        }
    }

    private static Comparator<Node> createObjectTypeEqualsNodeComparator() {
        return null;
    }

    private static Comparator<Node> createObjectTypeNotEqualsNodeComparator() {
        return null;
    }

    private static Comparator<Node> createObjectTypeExistsNodeComparator() {
        return null;
    }

    private static Comparator<Node> createObjectTypeNotExistsNodeComparator() {
        return null;
    }

    private static Comparator<Node> creatObjectVisibilityNodeComparator(Operation operation) {
        switch (operation) {
            case EQUALS:
                return createObjectVisibilityEqualsNodeComparator();
            case NOT_EQUALS:
                return createObjectVisibilityNotEqualsNodeComparator();
            case EXISTS:
                return createObjectVisibilityExistsNodeComparator();
            case NOT_EXISTS:
                return createObjectVisibilityNotExistsNodeComparator();
            default:
                throw new RuleException(String.format("Unknown operation: %s.", operation));
        }
    }

    private static Comparator<Node> createObjectVisibilityEqualsNodeComparator() {
        return new Comparator<Node>() {
            @Override
            public int compare(Node systemNode, Node patternNode) {
                return systemNode.getVisibility() == patternNode.getVisibility() ? 0 : 1;
            }
        };
    }

    private static Comparator<Node> createObjectVisibilityNotEqualsNodeComparator() {
        return new Comparator<Node>() {
            @Override
            public int compare(Node systemNode, Node patternNode) {
                return systemNode.getVisibility() != patternNode.getVisibility() ? 0 : 1;
            }
        };
    }

    private static Comparator<Node> createObjectVisibilityExistsNodeComparator() {
        return new Comparator<Node>() {
            @Override
            public int compare(Node systemNode, Node patternNode) {
                return systemNode.getVisibility() != null ? 0 : 1;
            }
        };
    }

    private static Comparator<Node> createObjectVisibilityNotExistsNodeComparator() {
        return new Comparator<Node>() {
            @Override
            public int compare(Node systemNode, Node patternNode) {
                return systemNode.getVisibility() == null ? 0 : 1;
            }
        };
    }

    private static Comparator<Node> createObjectModifierAbstractNodeComparator(Operation operation) {
        switch (operation) {
            case EQUALS:
                return createObjectModifierAbstractEqualsNodeComparator();
            case NOT_EQUALS:
                return createObjectModifierAbstractNotEqualsNodeComparator();
            case EXISTS:
                return createObjectModifierAbstractExistsNodeComparator();
            case NOT_EXISTS:
                return createObjectModifierAbstractNotExistsNodeComparator();
            default:
                throw new RuleException(String.format("Unknown operation: %s.", operation));
        }
    }

    private static Comparator<Node> createObjectModifierAbstractEqualsNodeComparator() {
        return new Comparator<Node>() {
            @Override
            public int compare(Node systemNode, Node patternNode) {
                return systemNode.isAbstract() == patternNode.isAbstract() ? 0 : 1;
            }
        };
    }

    private static Comparator<Node> createObjectModifierAbstractNotEqualsNodeComparator() {
        return new Comparator<Node>() {
            @Override
            public int compare(Node systemNode, Node patternNode) {
                return systemNode.isAbstract() != patternNode.isAbstract() ? 0 : 1;
            }
        };
    }

    private static Comparator<Node> createObjectModifierAbstractExistsNodeComparator() {
        return new Comparator<Node>() {
            @Override
            public int compare(Node systemNode, Node patternNode) {
                return systemNode.isAbstract() != null ? 0 : 1;
            }
        };
    }

    private static Comparator<Node> createObjectModifierAbstractNotExistsNodeComparator() {
        return new Comparator<Node>() {
            @Override
            public int compare(Node systemNode, Node patternNode) {
                return systemNode.isAbstract() == null ? 0 : 1;
            }
        };
    }

    private static class CompoundNodeComparator implements Comparator<Node> {
        private List<Comparator<Node>> comparators = new ArrayList<>();

        private CompoundNodeComparator(Comparator<Node>... subComparators) {
            comparators.addAll(Arrays.asList(subComparators));
        }

        @Override
        public int compare(Node systemNode, Node patternNode) {
            return (int) comparators.stream()
                    .filter(comparator -> comparator.compare(systemNode, patternNode) != 0)
                    .count();
        }
    }
}
