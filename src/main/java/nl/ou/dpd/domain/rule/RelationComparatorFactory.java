package nl.ou.dpd.domain.rule;

import nl.ou.dpd.domain.edge.Relation;

import java.util.Comparator;

/**
 * Created by martindeboer on 19-05-17.
 */
public class RelationComparatorFactory {

    public static Comparator<Relation> createCompoundRelationComparator() {
        return new CompoundRelationComparator();
    }

    public static Comparator<Relation> createCompoundRelationComparator(Comparator<Relation>... subComparators) {
        final CompoundRelationComparator compoundRelationComparator = new CompoundRelationComparator();
        for (Comparator<Relation> subComparator: subComparators) {
            compoundRelationComparator.addComparator(subComparator);
        }
        return compoundRelationComparator;
    }

    public static Comparator<Relation> createRelationComparator(Scope scope, Topic topic, Operation operation) {
        if (scope != Scope.RELATION) {
            throw new RuleException(String.format("Unknown scope: %s.", scope));
        }
        return createRelationScopeComparator(topic, operation);
    }

    private static Comparator<Relation> createRelationScopeComparator(Topic topic, Operation operation) {
        switch (topic) {
            case TYPE:
                return createRelationTypeComparator(operation);
            case CARDINALITY_LEFT:
                return createCardinalityLeftComparator(operation);
            case CARDINALITY_RIGHT:
                return createCardinalityRightComparator(operation);
            default:
                throw new RuleException(String.format("Unknown topic: %s.", topic));
        }
    }

    private static Comparator<Relation> createRelationTypeComparator(Operation operation) {
        if (operation != Operation.EQUALS) {
            throw new RuleException(String.format("Unknown operation: %s.", operation));
        }
        return createRelationTypeEqualsComparator();
    }

    private static Comparator<Relation> createCardinalityLeftComparator(Operation operation) {
        if (operation != Operation.EQUALS) {
            throw new RuleException(String.format("Unknown operation: %s.", operation));
        }
        return createCardinalityLeftEqualsComparator();
    }

    private static Comparator<Relation> createCardinalityRightComparator(Operation operation) {
        if (operation != Operation.EQUALS) {
            throw new RuleException(String.format("Unknown operation: %s.", operation));
        }
        return createCardinalityRightEqualsComparator();
    }

    private static Comparator<Relation> createRelationTypeEqualsComparator() {
        return new Comparator<Relation>() {
            @Override
            public int compare(Relation systemRelation, Relation patternRelation) {
                return systemRelation.getRelationType() == patternRelation.getRelationType() ? 0 : 1;
            }
        };
    }

    private static Comparator<Relation> createCardinalityLeftEqualsComparator() {
        return new Comparator<Relation>() {
            @Override
            public int compare(Relation systemRelation, Relation patternRelation) {
                return systemRelation.getCardinalityLeft().equals(patternRelation.getCardinalityLeft()) ? 0 : 1;
            }
        };
    }

    private static Comparator<Relation> createCardinalityRightEqualsComparator() {
        return new Comparator<Relation>() {
            @Override
            public int compare(Relation systemRelation, Relation patternRelation) {
                return systemRelation.getCardinalityRight().equals(patternRelation.getCardinalityRight()) ? 0 : 1;
            }
        };
    }

    private static class CompoundRelationComparator extends CompoundComparator<Relation> {

    }


}
