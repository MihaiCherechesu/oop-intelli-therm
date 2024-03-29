import java.util.Comparator;
import java.util.Map;

/**
 * Comparator which changes the natural insertion
 * in the TreeMap, which implements the SortedMap.
 *
 * Compares the value, instead of key.
 */
public class ValueComparator implements Comparator<Long> {

    private Map<Long, Double> map;

    public ValueComparator(Map<Long, Double> map) {
        this.map = map;
    }

    @Override
    public int compare(Long key1, Long key2) {

        if (map.containsKey(key1) && map.containsKey(key2)) {
            return map.get(key1).compareTo(map.get(key2));
        }
        return -13; // Case when the keys reference nothing (nil).
    }
}
