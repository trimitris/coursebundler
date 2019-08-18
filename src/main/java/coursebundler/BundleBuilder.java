package coursebundler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Builds all possible combinations of courses (bundles) that follow the rules.
 * Examples of rules are: number of courses per term, number of management courses per academic year.
 */
public class BundleBuilder {
    private Data courses;
    private ArrayList<String[]> bundles; // list of bundles represented only by the course codes of its members, and NOT bundle objects
    private final int lenBundle;

    // Constructor

    /**
     * Creates a BundleBuilder object
     * @param courses instance of Data class, represents available courses for academic year
     * @param lenBundle is the permitted number of courses in a course combination
     */
    public BundleBuilder(Data courses, int lenBundle) {
        if (lenBundle < 1) throw new IllegalArgumentException("Length of bundle shouldn't be less than 1");
        if (courses == null) throw new NullPointerException("courses data cannot be null");

        setCourses(courses);
        this.lenBundle = lenBundle;
        findBundles();
    }

    // Setter and getter methods
    private void setCourses(Data courses){
        this.courses = courses;
    }
    public Data getCourses(){
        return this.courses;
    }

    /**
     * Returns list of bundles (in this context, bundles contain course codes and not course objects)
     * @return list of bundles
     */
    public ArrayList<String[]> getBundles(){
        return this.bundles;
    }

    public int getLenBundle(){
        return this.lenBundle;
    }

    // Interface methods

    /**
     * Prints all possible combination of courses that follow the rules
     */
    public void printAllBundles(){
        // prints course codes in each bundle
        for (int i=0; i<this.bundles.size(); i++){
            printBundleAndId(i);
        }
    }

    /**
     * Prints a bundle to stdout, given a particular bundle id
     * @param bundleId The id of the required bundle
     */
    public void printBundleById(Integer bundleId){
        if ((bundleId >= this.bundles.size()) || bundleId < 0){
            throw new ArrayIndexOutOfBoundsException("The bundleID " + bundleId + " does not exist");
        }
        System.out.println(Arrays.toString(this.bundles.get(bundleId)));
    }

    /**
     * Prints the id of the bundle and the bundle to stdout, given the bundle id
     * @param bundleId The id of the required bundle
     */
    public void printBundleAndId(Integer bundleId){
        if ((bundleId >= this.bundles.size()) || bundleId < 0){
            throw new ArrayIndexOutOfBoundsException("The bundleID " + bundleId + " does not exist");
        }
        System.out.println("Id: " + bundleId + " Bundle: " + Arrays.toString(this.bundles.get(bundleId)));
    }

    /**
     * Returns all the valid bundles that differ from the target bundle up to a certain number of courses.
     * @param targetBundleID The index of the target bundle in the ArrayList this.bundles
     * @param upToDiff Max number of courses the returned bundles differ from the target bundle
     * @return a list of valid bundle Ids that differ from the target bundle up to a certain number of courses
     */
    public ArrayList<Integer> findSimilarBundlesTo(int targetBundleID, int upToDiff){
        if ((targetBundleID >= this.bundles.size()) || targetBundleID < 0){
            throw new ArrayIndexOutOfBoundsException("The targetBundleID " + targetBundleID + " does not exist");
        }
        if ((upToDiff > this.lenBundle) || (upToDiff < 0)){
            throw new IllegalArgumentException("Difference argument must be between 0 and " + this.lenBundle);
        }

        // assuming the BundleBuilder.findBundles has already been ran once, then this.bundles is populated with bundles!

        // build HashSet with courses of targetBundle
        HashSet<String> targetCourses = new HashSet<String>();
        for (int i=0; i<this.lenBundle; i++){
            targetCourses.add(this.bundles.get(targetBundleID)[i]);
        }

        ArrayList<Integer> similarBundleIds = new ArrayList<Integer>();
        for (int i=0; i<this.bundles.size(); i++){
            if (i == targetBundleID) continue; // don't consider target bundle to the list of similar bundles
            int diffCount = 0;
            for (int j=0; j<this.lenBundle; j++){
                if (!targetCourses.contains(this.bundles.get(i)[j])) diffCount++;
            }
            if (diffCount <= upToDiff){
                similarBundleIds.add(new Integer(i));
            }
        }
        return similarBundleIds;
    }

    /**
     * Prints the given list of bundles to stdout
     * @param list ArrayList of String arrays, which represent the course codes of the courses in each bundle
     */
    public static void printCustomBundleList(ArrayList<String[]> list){
        for (int i=0; i<list.size(); i++){
            System.out.println(Arrays.toString(list.get(i)));
        }
    }

    /**
     * Prints the bundles with the given bundle Ids to stdout
     * @param bundleIds List of bundle ids (i.e. the indexes of the bundles in this.bundles)
     */
    public void printBundlesFromBundleIds(ArrayList<Integer> bundleIds){
        for (int i=0; i<bundleIds.size(); i++){
            printBundleAndId(bundleIds.get(i));
        }
    }

    private void findBundles(){
        // interface method - starts the recursive call

        this.bundles = new ArrayList<String[]>();
        findBundles(0, new Bundle(this.lenBundle));
    }

    // Algorithm methods

    private boolean checkRules(Bundle curBundle, Course curCourse){
        // Implements rules
        // Assumes curBundle obeys the rules, and checks if adding curCourse to it violates the rules.

        // first rule: must have 4 courses in M and 4 in L
        if (curBundle.getNumForTerm(curCourse.getTerm())>3) {
            return false;
        }
        // second rule: must only have 1 management module
        if ((curCourse.getManagerial()) && (curBundle.getNumManagerial()>0)){
            return false;
        }
        return true;
    }

    private void findBundles(int startIdx, Bundle curBundle){
        // Iterates through all possible combination of courses, and generates bundles if they follow the rules

        // Base case
        if (curBundle.isFull()){ // if bundle has filled
            this.bundles.add(curBundle.getBundleCourseCodes());
            return;
        }

        // Compound case
        for (int i = startIdx; i<this.courses.getNumCourses(); i++){
            Course curCourse = this.courses.getCourseByIdx(i);
            if (checkRules(curBundle, curCourse)){
                curBundle.addCourse(curCourse);
                findBundles(i+1, curBundle);
                curBundle.removeLastCourse();
            }
        }
    }
}


