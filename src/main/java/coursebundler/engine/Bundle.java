package coursebundler.engine;

import java.util.EnumMap;

/**
 * Represents a possible combination of courses that a student might take for an academic year.
 * The course bundle has a fixed number of courses, defined in the constructor.
 * It works in a similar manner to a stack. You can only push and pop courses into the bundle.
 */
public class Bundle {
    private final int lenBundle;
    private Course[] bundle;
    private int count; // shows number of courses currently in the bundle
    private EnumMap<Term, Integer> numPerTerm; // represents number of courses currently in the bundle per term
    private int numManagerial; // number of managerial courses

    // Constructor

    /**
     * Creates a bundle object with specified number of courses
     * @param len number of courses in bundle
     */
    public Bundle(int len){
        if (len < 1) throw new IllegalArgumentException("Length of bundle shouldn't be less than 1");

        // initialize attributes
        this.lenBundle = len;
        this.bundle = new Course[lenBundle];
        this.count = 0;
        initializeNumPerTerm();
        this.numManagerial = 0;
    }

    /**
     * Returns an array of all the courses in the bundle
     * @return array of course objects
     */
    public Course[] getBundle(){
        return this.bundle;
    }

    /**
     * Returns number of courses currently in the bundle for specified term
     * @param term academic term
     * @return number of courses currently in the bundle for specified term
     */
    public Integer getNumForTerm(Term term){
        return this.numPerTerm.get(term);
    }

    /**
     * Returns number of managerial courses currently in the bundle
     * @return number of managerial courses currently in the bundle
     */
    public int getNumManagerial(){
        return this.numManagerial;
    }

    /**
     * Prints all the courses currently in the bundle to stdout
     */
    public void printBundle(){
        if (this.count == 0) return;

        for (int i=0; i<this.count; i++){
            this.bundle[i].printCourse();
        }
    }

    /**
     * Returns the codes of all the courses currently in the bundle
     * @return codes of all the courses currently in the bundle. Returns null if no courses in the bundle
     */
    public String[] getBundleCourseCodes(){
        if (this.count == 0) return null;

        String[] codes = new String[this.lenBundle];

        // notice that for loop goes until count instead of lenBundle
        for (int i=0; i<this.count; i++){
            codes[i] = this.bundle[i].getCode();
        }
        return codes;
    }

    /**
     * Adds given course to the bundle, and updates other class attributes.
     * @param course academic course
     * @throws IllegalStateException if bundle is already full
     */
    public void addCourse(Course course) throws IllegalStateException{
        // push course at end of the "stack"

        if (isFull()){
            throw new IllegalStateException("Can't add course to bundle, it is already full");
        }
        this.bundle[count] = course;

        // Update the rest of the class attributes
        Term term = course.getTerm();
        this.numPerTerm.put(term, this.numPerTerm.get(term)+1);
        if (course.getManagerial()){
            this.numManagerial++;
        }
        this.count++;
    }

    /**
     * Removes last course added to the bundle and updates class attributes
     */
    public void removeLastCourse(){
        // remove last course in the "stack" (bundle)
        if (count == 0){
            return; // return if no courses in the bundle
        }
        this.count--;
        Term term = bundle[count].getTerm();
        this.numPerTerm.put(term, this.numPerTerm.get(term)-1);
        if (this.bundle[count].getManagerial()){
            this.numManagerial--;
        }
        this.bundle[count] = null;
    }

    /**
     * Checks if the bundle has filled
     * @return true or false if the bundle is full
     */
    public boolean isFull(){
        // returns if the bundle is full
        return count >= lenBundle;
    }

    private void initializeNumPerTerm(){
        this.numPerTerm = new EnumMap<Term, Integer>(Term.class);
        for (Term term: Term.values()){
            this.numPerTerm.put(term, 0);
        }
    }
}
