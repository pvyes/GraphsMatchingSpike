package nl.ou.dpd.domain.rule;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by martindeboer on 19-05-17.
 */
public abstract class CompoundComparator<T> implements Comparator<T> {

    private Set<Comparator<T>> subComparators;

    public void addComparator(Comparator<T> subComparator) {
        if (subComparators == null) {
            subComparators = new HashSet<>();
        }
        subComparators.add(subComparator);
    }

    @Override
    public int compare(T systemObject, T patternObject) {
        return (int) subComparators.stream()
                .filter(comparator -> comparator.compare(systemObject, patternObject) != 0)
                .count();
    }
}

