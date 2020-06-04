import java.util.Collections;
import java.util.Comparator;
/**
 * Compares Tuples based on their sort Attribute
 *
 * @author Sarah McClain
 * @version November 18, 2019
 */
class TupleComparator implements Comparator<Tuple> {

    String sortAttribute;

    /**
     * Constructor that requires the name of the attribute
     * wished to compare tuples by
     *
     * @param sortAttribute attribute to sort by
     */
    public TupleComparator(String sortAttribute){
        super();
        this.sortAttribute = sortAttribute;
    }

    /**
     *  Compares two tuples based on their common attribute
     *
     * @param t1 first tuple to compare with
     * @param t2 the second tuple to compare with
     * @return -1 if the value of t1.c < t2.c,
     *          0 if t1.c = t2.c, and 1 if t1.c > t2.c
     *          where c is attribute to be sorted by
     */
    @Override
    public int compare(Tuple t1, Tuple t2) {
        return t1.getAttributeValue(sortAttribute).compareTo(t2.getAttributeValue(sortAttribute));
    }

}