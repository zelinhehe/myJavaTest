import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class TreeMapTest {

    public static void main(String[] args) {
        SortedMap<Integer, Integer> sortedMap = new TreeMap<>();
        for (int i = 0; i < 10; i++) {
            sortedMap.put(i, i);
        }
        System.out.println(sortedMap.toString());

        System.out.println(sortedMap.headMap(6));
        System.out.println(sortedMap.tailMap(6));
        System.out.println(sortedMap.subMap(2, 5));
        System.out.println(sortedMap.subMap(2, 5).firstKey());
        System.out.println(sortedMap.subMap(2, 5).lastKey());

        System.out.println();
        NavigableMap<Integer, Integer> navigableMap = new TreeMap<>();
        for (int i = 0; i < 10; i++) {
            navigableMap.put(i, i);
        }
        System.out.println(navigableMap.toString());

        System.out.println(navigableMap.ceilingEntry(6));
        System.out.println(navigableMap.floorEntry(6));
        System.out.println(navigableMap.higherEntry(6));
        System.out.println(navigableMap.descendingKeySet());
        System.out.println(navigableMap.descendingMap());
    }
}
