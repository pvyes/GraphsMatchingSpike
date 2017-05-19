package nl.ou.dpd.domain.rule;

import nl.ou.dpd.domain.edge.Relation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by martindeboer on 19-05-17.
 */
public class RelationComparatorFactory {

    public static Comparator<Relation> createRelationComparator() {
        return new CompoundRelationComparator();
    }

    private static Comparator<Relation> createCardinalityComparator() {
        return new Comparator<Relation>() {
            @Override
            public int compare(Relation systemRelation, Relation patternRelation) {
                final boolean sameCardinalityLeft = systemRelation.getCardinalityLeft().equals(patternRelation.getCardinalityLeft());
                final boolean sameCardinalityRight = systemRelation.getCardinalityRight().equals(patternRelation.getCardinalityRight());
                final boolean same = sameCardinalityLeft && sameCardinalityRight;
                return same ? 0 : 1;
            }
        };
    }

    private static Comparator<Relation> createRelationTypeComparator() {
        return new Comparator<Relation>() {
            @Override
            public int compare(Relation systemRelation, Relation patternRelation) {
                return systemRelation.getRelationType() == patternRelation.getRelationType() ? 0 : 1;
            }
        };
    }

    private static class CompoundRelationComparator implements Comparator<Relation> {
        private List<Comparator<Relation>> comparators = new ArrayList<>();

        private CompoundRelationComparator() {
            comparators.add(createCardinalityComparator());
            comparators.add(createRelationTypeComparator());
        }

        @Override
        public int compare(Relation systemRelation, Relation patternRelation) {
            return (int) comparators.stream()
                    .filter(comparator -> comparator.compare(systemRelation, patternRelation) != 0)
                    .count();
        }
    }


}
