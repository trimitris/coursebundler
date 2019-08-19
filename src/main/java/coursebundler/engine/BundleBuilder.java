package coursebundler.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import coursebundler.utilities.Color;

// TODO create method for printing all the bundles and highlighting the desired course codes

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


    private void findBundles(){
        // method that starts the recursive call

        this.bundles = new ArrayList<>();
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

    private void setCourses(Data courses){
        this.courses = courses;
    }

    // Public methods

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
        HashSet<String> targetCourses = new HashSet<>();
        for (int i=0; i<this.lenBundle; i++){
            targetCourses.add(this.bundles.get(targetBundleID)[i]);
        }

        ArrayList<Integer> similarBundleIds = new ArrayList<>();
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
     * Returns all valid bundles that contain all the required courses
     * @param requiredCourseCodes A list of course codes for the required courses
     * @return A list of bundleIds of the valid bundles that contain all the required courses
     */
    public ArrayList<Integer> findBundlesWithCourses(ArrayList<String> requiredCourseCodes){
        // check validity of input
        if (requiredCourseCodes.size() > this.lenBundle) throw new IllegalArgumentException("Number of required courses must not exceed number of courses in a bundle");
        // check if all given course codes exist
        for (int i=0; i<requiredCourseCodes.size(); i++){
            // TODO create a custom exception for the situation of non-existent course code
            if (this.courses.getCourseByCode(requiredCourseCodes.get(i)) == null) throw new IllegalArgumentException("The course code: " + requiredCourseCodes.get(i) + " does not exist");
        }

        ArrayList<Integer> outputIds = new ArrayList<>();
        // Loop over all valid bundles and find the ones that have all the requiredCourseCodes
        for (int i=0; i<this.bundles.size(); i++){
            // create hashSet with the courses in the current bundle for easily checking if the required ones are in it
            HashSet<String> curBundleCourses = new HashSet<>();
            for (int j=0; j<this.lenBundle; j++){
                curBundleCourses.add(this.bundles.get(i)[j]);
            }

            // check if all requiredCourseCodes are in it
            boolean isValidBundle = true;
            for (int j=0; j<requiredCourseCodes.size(); j++){
                if (!curBundleCourses.contains(requiredCourseCodes.get(j))){
                    isValidBundle = false;
                    break;
                }
            }
            // if bundle is valid, add its id
            if (isValidBundle) outputIds.add(i);
        }
        return outputIds;
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

    public int getLenBundle(){ return this.lenBundle; }


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
     * Prints the bundles with the given bundle Ids to stdout
     * @param bundleIds List of bundle ids (i.e. the indexes of the bundles in this.bundles)
     */
    public void printBundlesFromBundleIds(ArrayList<Integer> bundleIds){
        for (int i=0; i<bundleIds.size(); i++){
            printBundleAndId(bundleIds.get(i));
        }
    }

    /**
     * Prints the courses of a bundle in color, to show which courses are the same and which are different with another
     * bundle
     * @param bundleId Id of bundle being printed
     * @param differFromId Id of bundle against which the first bundle is compared
     */
    public void printBundleDifferencesColor(Integer bundleId, Integer differFromId){
        if ((bundleId >= this.bundles.size()) || bundleId < 0){
            throw new ArrayIndexOutOfBoundsException("The bundle id " + bundleId + " does not exist");
        }
        if ((differFromId >= this.bundles.size()) || differFromId < 0){
            throw new ArrayIndexOutOfBoundsException("The bundle id " + differFromId + " does not exist");
        }

        // build HashSet with courses of targetBundle
        HashSet<String> targetCourses = new HashSet<>();
        for (int i=0; i<this.lenBundle; i++){
            targetCourses.add(this.bundles.get(differFromId)[i]);
        }

        // print Bundle
        System.out.print("Id: " + bundleId + " Bundle: ");
        for (int i=0; i<this.lenBundle; i++){
            if (targetCourses.contains(this.bundles.get(bundleId)[i])){
                System.out.print(Color.applyGreen(this.bundles.get(bundleId)[i]));
            } else {
                System.out.print(Color.applyRed(this.bundles.get(bundleId)[i]));
            }
            System.out.print(" ");
        }
        System.out.println();
    }

    // Static methods
    /**
     * Prints the given list of bundles to stdout
     * @param list ArrayList of String arrays, which represent the course codes of the courses in each bundle
     */
    public static void printCustomBundleList(ArrayList<String[]> list){
        for (int i=0; i<list.size(); i++){
            System.out.println(Arrays.toString(list.get(i)));
        }
    }
}


