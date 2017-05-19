package nl.ou.dpd.domain.rule;

import nl.ou.dpd.domain.edge.Relation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by martindeboer on 19-05-17.
 */
public class RelationComparatorFactory {

    public static Comparator<Relation> createMasterComparator() {
        return new CompoundRelationComparator();
    }

    private static Comparator<Relation> createCardinalityComparator() {
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

    private static Comparator<Relation> createRelationTypeComparator() {
        return new Comparator<Relation>() {
            @Override
            public int compare(Relation o1, Relation o2) {
                final boolean sameRelationType = o1.getRelationType() == o2.getRelationType();
                return sameRelationType ? 0 : 1;
            }
        };
    }

    private static class CompoundRelationComparator implements Comparator<Relation> {
        private List<Comparator<Relation>> comparators = new ArrayList<>();

        public CompoundRelationComparator() {
            comparators.add(createCardinalityComparator());
            comparators.add(createRelationTypeComparator());
        }

        @Override
        public int compare(Relation o1, Relation o2) {
            return (int) comparators.stream().filter(comparator -> comparator.compare(o1, o2) != 0).count();
        }
    }


}
