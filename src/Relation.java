import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class representing a relation. The relation holds
 * a list of attributes, a list of tuples and the
 * attribute the relation is sorted on
 *
 * @author Sarah McClain
 * @version November 18, 2019
 */
public class Relation {
    private int numAttributes;
    private int numTuples;
    private String relationName;
    private List<String> attributeNames;
    private List<Tuple> tuples;
    private String sortedOn;

    /*********************************************************
     * Constructors
     *********************************************************/

    /**
     * Default constructor. Initialized number of attributes and
     * the number of tuples to zero
     */
    public Relation(){
        numAttributes = 0;
        numTuples = 0;
        relationName = "";
        attributeNames = new ArrayList<String>();
        tuples = new ArrayList<Tuple>();
    }
    /**
     * Constructor that requires the name of the relation to
     * be created. Initialized number of attributes and tuples
     * to zero
     *
     * @param relationName name of relation to create
     */
    public Relation(String relationName){
        this.relationName = relationName;
        attributeNames = new ArrayList<String>();
        tuples = new ArrayList<Tuple>();
        numAttributes = 0;
        numTuples = 0;
    }



    /*********************************************************
     * Getter Methods
     *********************************************************/
    /**
     * Returns the name of the relation
     *
     * @return the name of the relation
     */
    public String getRelationName(){
        return relationName;
    }

    /**
     * Returns the name of the attribute name at a given index
     * in the relation
     *
     * @param index of attribute to return
     * @return attribute name at index
     */
    public String getAttributeName(int index){
        return attributeNames.get(index);
    }

    /**
     * Returns the tuple at a given index in the relation
     *
     * @param index of tuple to return
     * @return tuple at index
     */
    public Tuple getTuple(int index){
        return tuples.get(index);
    }

    /**
     * Returns the number of attributes in the relation
     *
     * @return number of attributes in the relation
     */
    public int getNumAttributes(){
        return numAttributes;
    }

    /**
     * Returns a list of the tuples in the relation
     *
     * @return list of tuples in the relation
     */
    public List<Tuple> getTuples(){
        return tuples;
    }

    /**
     * Returns the number of tuples in the relations
     *
     * @return the number of tuples in the relation
     */
    public int getNumTuples(){
        return tuples.size();
    }



    /*********************************************************
     * Setter/Adding Methods
     *********************************************************/

    /**
     * Adds a new attribute name to the list of attributes
     * in the relation
     *
     * @param name to be added to the relation
     */
    public void addAttributeName(String name){
        attributeNames.add(name);
        numAttributes++;
    }

    /**
     * Adds a tuple to the list of tuples
     * in the relation
     *
     * @param t tuple to be added to the relation
     */
    public void addTuple(Tuple t){
        tuples.add(t);
        numTuples++;
    }

    /**
     * Adds all the attributes from the data files into
     * the relation
     *
     * @param names attribute names to be added to the relation
     */
    public void addAllAttributeNames(String[] names){
        for(String name : names){
            attributeNames.add(name);
            numAttributes++;
        }
    }

    /**
     * Sets the number of attributes in the relation
     *
     * @param numAttributes number of attributes to set relation to
     */
    public void setNumAttributes(int numAttributes){
        this.numAttributes = numAttributes;
    }

    /**
     * Sets the attribute which the relation is
     * sorted by
     *
     * @param sortedOn name of attribute the relation
     *        is sorted on
     */
    public void setSortedOn(String sortedOn){
        this.sortedOn = sortedOn;
    }



    /*********************************************************
     * Boolean Methods
     *********************************************************/

    /**
     * Checks to see if an attribute exists in the
     * relation
     *
     * @param atr the name of the attribute to check
     *         if exists in relation
     * @return true if attribute exists in relation
     *         false if attribute doesn't exist in relation
     */
    public boolean attributeExists(String atr){
        if(attributeNames.contains(atr)){
            return true;
        }
        return false;
    }

    /**
     * Checks if the relation is sorted by a certain attribute
     *
     * @param sort the attribute to check if the relation is
     *             sorted by
     * @return true if relation is sorted by the attribute
     *         false if relation is not sorted by the attribute
     */
    public boolean isSorted(String sort){
        return sort.equals(sortedOn);
    }

    /**
     * Returns a string representing the tuples and
     * their attributes in the relation
     *
     * @return string representing the relation
     */
    public String toString(){
        String str = "";

        //print attribute names
        for(int i=0; i<numAttributes; i++){
            str += attributeNames.get(i) + "\t";
        }
        str += "\n";

        //print tuples
        for(int i=0; i<numTuples; i++) {
            for(int j =0; j< numAttributes; j++){
                str += tuples.get(i).getAttributeValue(attributeNames.get(j)) + "\t";
            }
            str += "\n";
        }
        return str;
    }

    /*********************************************************
     * Private Helper Methods
     *********************************************************/
    /**
     * Prints the attributes in the relation
     */
    private void printRelationAttributes(){
        String attributes="";
        for(String name : attributeNames){
            attributes += name + ", ";
        }
        System.out.println(attributes);
    }

    /**
     * Prints the tuples in the relation
     */
    private void printRelationTuples(){
        String str = "";
        int counter = 0;

        //print attribute names
        for(int i=0; i<numAttributes; i++){
            System.out.print(attributeNames.get(i) + "\t" );
        }
        System.out.println();
        //print tuples
        for(int i=0; i<numTuples; i++) {
            for(int j =0; j< numAttributes; j++){
                System.out.print(tuples.get(i).getAttributeValue(attributeNames.get(j)) + "\t");
            }
            System.out.println();
        }
    }
}
