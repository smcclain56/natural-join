import javax.sound.midi.SysexMessage;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * A JoinEngine class that takes in a data directory and adds the information
 * from each file into the appropriate relations. Files in the data directory
 * cannot start with a period. The user then inputs two relations and what type
 * of join they wish to complete. The appropriate join is done and the results
 * along with the amount of time it took to compute are printed.
 *
 * @author Sarah McClain
 * @version November 18, 2019
 */
public class JoinEngine {
    public static void main(String[] args) {
        // when program starts you should ready every file in the data/ directory into a relation
        ArrayList<String> relationNames = new ArrayList<String>(); // an arraylist of the file names
        Map<String, Relation> relations = new HashMap<String, Relation>();

        try {
            // Create a file object
            String currentDir = new File("").getAbsolutePath();
            File f = new File(currentDir + "/data"); //TODO IS IT OKAY THAT SRC IS HARDCODED?

            // Get all the names of the files present in the given directory
            File[] files = f.listFiles();
            Scanner scan;
            String relationName;
            String fileLine;
            Relation relation;
            int lineNum;
            Tuple tmpTuple;
            for (File file : files) {
                lineNum = 0;

                // ignore any file names that start with "."
                if(file.getName().startsWith(".")){
                    continue;
                }
                scan = new Scanner(file);

                //create a relation for each file
                relationName = file.getName().substring(0, file.getName().lastIndexOf('.'));
                relationNames.add(relationName.toLowerCase()); //add relation name to list (all lowercase)
                relation = new Relation(relationName);
                relations.put(relationName, relation);

                //loop through each line of the file
                while (scan.hasNextLine()) {
                    lineNum++;
                    fileLine = scan.nextLine();

                    // Add the attribute names to the relation
                    if (fileLine.startsWith("#")) {
                        fileLine = fileLine.replaceAll("\\#", ""); //get rid of #
                        if (lineNum == 1) { //attribute names
                            String[] attributes = fileLine.split("\\|");
                            relation.addAllAttributeNames(attributes);
                        } else { //sort by
                            relation.setSortedOn(fileLine);
                        }
                    } else { // Add the tuples to the relation
                        String[] tupleData = fileLine.split("\\|");
                        //make a new tuple, add its data, add to relation
                        tmpTuple = new Tuple();
                        for (int i = 0; i < relation.getNumAttributes(); i++) {
                            tmpTuple.addAttribute(tupleData[i], relation.getAttributeName(i));
                        }
                        relation.addTuple(tmpTuple);
                    }

                }
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // print out available relations
        Scanner input = new Scanner(System.in);
        System.out.println("Available relations:");
        System.out.println(relationNames.toString());

        // prompt user to pick two relations
        System.out.println("Pick your first relation: ");
        String firstRelation = input.nextLine().toLowerCase();
        System.out.println("Pick your second relation: ");
        String secondRelation = input.nextLine().toLowerCase();

        // throw error if relation entered doesn't exist
        if (!relationNames.contains(firstRelation) || !relationNames.contains(secondRelation)) {
            throw new IllegalArgumentException();
        }

        // prompt user to choose the type of joins they want to do
        System.out.println("Your selection (separated by space): " + firstRelation + " " + secondRelation);
        System.out.println("1. Nested Loop Join");
        System.out.println("2. Hash Join");
        System.out.println("3. Sort-Merge Join");
        int choice = input.nextInt();
        System.out.println("Your selection: " + choice);

        // complete joins
        Relation T = new Relation();
        long start = 0;
        long end = 0;
        double timeDiff;
        if (choice == 1) { //nested loop join
            start = System.nanoTime(); // START TIMER
            try{
                T= nestedLoopJoin(relations.get(firstRelation), relations.get(secondRelation));
            } catch (Exception e) {
                e.printStackTrace();
            }
            end = System.nanoTime();
        }

        if (choice == 2) { //hash join
            start = System.nanoTime();
            try{
                T= hashJoin(relations.get(firstRelation), relations.get(secondRelation));
            } catch (Exception e) {
                e.printStackTrace();
            }
            end = System.nanoTime();

        }

        if (choice == 3) { //sort-merge join
            start = System.nanoTime();
            try{
                T= sortMergeJoin(relations.get(firstRelation), relations.get(secondRelation));
            } catch (Exception e) {
                e.printStackTrace();
            }
            end = System.nanoTime();
        }

        System.out.println(T.toString());
        timeDiff = ((double) (end - start)/1000000.0);
        System.out.println("Time = " + timeDiff + "ms");
    }

    /**
     *  Computes a nested loop join between the relation R and S. Throws an
     *  exception when the inputted relations do not have a common attribute.
     *
     * @param R the first relation to be joined
     * @param S the second relation to be joined
     * @return The resulting relation of the join
     */
    public static Relation nestedLoopJoin(Relation R, Relation S) throws Exception {
        // make new empty relation T
        Relation T = new Relation();
        String commonAtr = "";

        // get common attribute and add all attributes to T
        for(int i =0; i < R.getNumAttributes(); i++){
            T.addAttributeName(R.getAttributeName(i));
            if(S.attributeExists(R.getAttributeName(i))){
                commonAtr = R.getAttributeName(i);
            }
        }
        for(int i=0; i < S.getNumAttributes(); i++){
            if(!T.attributeExists(S.getAttributeName(i))){
                T.addAttributeName(S.getAttributeName(i));
            }
        }

        // throw an exception if no common attribute
        if(commonAtr.equals("")){
            throw new Exception("No Common Attribute");
        }

        Tuple newTuple;
        // loop through and concatenate on the common attribute
        for (Tuple tupleR : R.getTuples()){
            for (Tuple tupleS : S.getTuples()){
                if(tupleR.getAttributeValue(commonAtr).equals(tupleS.getAttributeValue(commonAtr))){
                    newTuple = concatenateTuples(tupleR,tupleS);
                    T.setNumAttributes(newTuple.getNumberOfAttributes());
                    T.addTuple(newTuple);
                }
            }
        }
        return T;
    }

    /**
     *  Computes a hash join between the relation R and S. Throws an
     *  exception when the inputted relations do not have a common attribute
     *  or when the the common attribute is not unique.
     *
     * @param R the first relation to be joined
     * @param S the second relation to be joined
     * @return The resulting relation of the join
     */
    public static Relation hashJoin(Relation R, Relation S) throws Exception{
        Relation T = new Relation();
        Map<String, Tuple> map = new HashMap<String, Tuple>();  //map value of common attribute of tuple and tuple
        String commonAtr = "";

        // get common attribute and add all attributes to T
        for(int i =0; i < R.getNumAttributes(); i++){
            T.addAttributeName(R.getAttributeName(i));
            if(S.attributeExists(R.getAttributeName(i))){
                commonAtr = R.getAttributeName(i);
            }
        }
        for(int i=0; i < S.getNumAttributes(); i++){
            if(!T.attributeExists(S.getAttributeName(i))){
                T.addAttributeName(S.getAttributeName(i));
            }
        }

        // throw an exception if no common attribute
        if(commonAtr.equals("")){
            throw new Exception("No Common Attribute");
        }

        // Phase 1: Hash every tuple of R by the value of the common attribute
        for(Tuple tupleR : R.getTuples()){
            if(!map.containsKey(tupleR.getAttributeValue(commonAtr))){
                map.put(tupleR.getAttributeValue(commonAtr), tupleR);
            }else{ //throw an exception if common attribute is not unique
                throw new Exception("Common Attribute Must Be Unique");
            }
        }

        // Phase 2: Join up with S
        Tuple temp;
        Tuple newTuple;
        for(Tuple tupleS : S.getTuples()){
            if(map.containsKey(tupleS.getAttributeValue(commonAtr))){
                temp = map.get(tupleS.getAttributeValue(commonAtr));
                newTuple = concatenateTuples(tupleS, temp);
                T.addTuple(newTuple);
            }
        }
        return T;
    }

    /**
     *  Computes a sort-merge join between the relation R and S. Throws an
     *  exception when the inputted relations do not have a common attribute
     *
     * @param R the first relation to be joined
     * @param S the second relation to be joined
     * @return The resulting relation of the join
     */
    public static Relation sortMergeJoin(Relation R, Relation S) throws Exception{
        String commonAtr = "";
        Relation T = new Relation();
        // get common attribute and add all attributes to T
        for(int i =0; i < R.getNumAttributes(); i++){
            T.addAttributeName(R.getAttributeName(i));
            if(S.attributeExists(R.getAttributeName(i))){
                commonAtr = R.getAttributeName(i);
            }
        }
        for(int i=0; i < S.getNumAttributes(); i++){
            if(!T.attributeExists(S.getAttributeName(i))){
                T.addAttributeName(S.getAttributeName(i));
            }
        }

        // throw an exception if no common attribute
        if(commonAtr.equals("")){
            throw new Exception("No Common Attribute");
        }

        // Sort R if not already sorted
        if(!R.isSorted(commonAtr)){
            TupleComparator compareEng = new TupleComparator(commonAtr);
            Collections.sort(R.getTuples(), compareEng);
        }
        // Sort S if not already sorted
        if(!S.isSorted(commonAtr)){
            TupleComparator compareEng = new TupleComparator(commonAtr);
            Collections.sort(S.getTuples(), compareEng);
        }

        int i=0,j=0,k;
        Tuple newTuple;
        int sizeR = R.getNumTuples();
        int sizeS = S.getNumTuples();

        // Do sort-merge join between the two sorted relations
        while(i<sizeR && j<sizeS){
            //Match found, enter merge phase
            if(R.getTuple(i).getAttributeValue(commonAtr).equals(S.getTuple(j).getAttributeValue(commonAtr))){
                while(i < sizeR && R.getTuple(i).getAttributeValue(commonAtr).equals(S.getTuple(j).getAttributeValue(commonAtr))){
                    k=j;
                    while(k<sizeS && R.getTuple(i).getAttributeValue(commonAtr).equals(S.getTuple(k).getAttributeValue(commonAtr))){
                        newTuple = concatenateTuples(R.getTuple(i),S.getTuple(k));
                        T.addTuple(newTuple);
                        k++;
                    }
                    i++;
                }
            }else if(R.getTuple(i).getAttributeValue(commonAtr).compareTo(S.getTuple(j).getAttributeValue(commonAtr)) == -1){
                i++;
            }else{
                j++;
            }
        }
        return T;
    }

    /**
     *  Merges two tuples to form a new tuple concatenated
     *  on their common attribute
     *
     * @param A the first tuple
     * @param B the second tuple
     * @return The resulting tuple of the concatenation
     */
    public static Tuple concatenateTuples(Tuple A, Tuple B){

        Tuple newTuple = new Tuple();
        // add all tuples from A and B into a new tuple
        newTuple.getAttributes().putAll(A.getAttributes());
        newTuple.getAttributes().putAll(B.getAttributes());

        int size = newTuple.getAttributes().size();
        newTuple.setNumberOfAttributes(size);
        return newTuple;
    }
}
