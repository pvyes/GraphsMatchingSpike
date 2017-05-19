package nl.ou.dpd.domain.rule;

import nl.ou.dpd.domain.edge.Relation;

import java.util.Comparator;

/**
 * Created by martindeboer on 19-05-17.
 */
public class RelationComparatorFactory {

    public static Comparator<Relation> createCardinalityComparator() {
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

    public static Comparator<Relation> createRelationTypeComparator() {
        return new Comparator<Relation>() {
            @Override
            public int compare(Relation o1, Relation o2) {
                final boolean sameRelationType = o1.getRelationType() == o2.getRelationType();
                return sameRelationType ? 0 : 1;
            }
        };
    }

}
