package coursebundler;

import java.util.ArrayList;
import java.util.Arrays;

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
    public void printBundles(){
        // prints course codes in each bundle
        for (int i=0; i<this.bundles.size(); i++){
            System.out.println(Arrays.toString(this.bundles.get(i)));
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
        // TODO incorporate other rules as well

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


