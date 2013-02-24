import java.util.HashMap;
import java.util.*;

//@SuppressWarnings("unchecked") // for JDK 1.5 and above
public class SortedHashmap {
    public static void main(String[] args) {
        Map<String, String> m = new HashMap<String, String>();
        m.put("a", "some");
        m.put("b", "random");
        m.put("c", "words");
        m.put("d", "to");
        m.put("e", "be");
        m.put("f", "sorted");
        m.put("g", "by");
        m.put("h", "value");
        for (Iterator i = sortByValue(m).iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            System.out.printf("key: %s, value: %s\n", key, m.get(key));
        }
    }

    public static List sortByValue(final Map m) {
        List keys = new ArrayList();
        keys.addAll(m.keySet());
        Collections.sort(keys, new Comparator() {
            public int compare(Object o1, Object o2) {
                Object v1 = m.get(o1);
                Object v2 = m.get(o2);
                if (v1 == null) {
                    return (v2 == null) ? 0 : 1;
                }
                else if (v1 instanceof Comparable) {
                    return ((Comparable) v1).compareTo(v2);
                }
                else {
                    return 0;
                }
            }
        });
        return keys;
    }
}