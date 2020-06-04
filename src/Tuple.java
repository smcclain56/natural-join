import java.lang.reflect.Array;
import java.util.*;

/**
 * A class representing a tuple. The tuple holds
 * a Hash map which maps the name of the attribute
 * to it's value
 *
 * @author Sarah McClain
 * @version November 18, 2019
 */
public class Tuple  {
    private int numAttributes;
    private Map<String, String> attributes;


    /*********************************************************
     * Constructors
     *********************************************************/
    /**
     * Default constructor. Initialized number of attributes
     * to zero.
     */
    public Tuple(){
        numAttributes = 0;
        attributes = new HashMap<String, String>();
    }


    /*********************************************************
     * Getter Methods
     *********************************************************/

    /**
     * Returns the number of attributes in the tuple
     *
     * @return the number of attributes in the tuple
     */
    public int getNumberOfAttributes(){
        return numAttributes;
    }

    /**
     * Returns the value of an attribute given the name
     * of the attribute
     *
     * @param name of the attribute
     * @return value of the attribute
     */
    public String getAttributeValue(String name){
        return attributes.get(name);
    }

    /**
     * Returns the hashmap representing all the
     * attributes
     *
     * @return a hashmap returning all the attributes in
     *         the tuple
     */
    public Map<String, String> getAttributes(){
        return attributes;
    }


    /*********************************************************
     * Setter Methods
     *********************************************************/

    /**
     * Sets the number of attributes in the tuple to the
     * number inputted
     *
     * @param numAttributes to be set in the tuple
     */
    public void setNumberOfAttributes(int numAttributes){
        this.numAttributes = numAttributes;
    }

    /**
     * Adds an attribute with name of name and value of
     * value to the tuple
     *
     * @param value of the attribute to be added
     * @param name of the attribute to be added
     */
    public void addAttribute(String value, String name){
        attributes.put(name, value);
        numAttributes++;
    }

    /**
     * Checks if an attribute exists in the tuple
     *
     * @param name of attribute to check
     * @return true if attribute exists in tuple
     *         false if attribute doesn't exist in tuple
     */
    public boolean attributeExists(String name){
        return attributes.containsKey(name);
    }

    /**
     * Returns a string representing the tuple
     *
     * @return a string representing the tuple
     */
    public String toString() {
        return attributes.toString();
    }

}
