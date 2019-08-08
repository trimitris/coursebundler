package coursebundler;

import java.util.ArrayList;
import java.util.Arrays;

public class Bundler {
    private Data courses;
    private ArrayList<String[]> bundles; // list of bundles represented only by the course codes of its members, and NOT bundle objects
    private final int lenBundle;

    // Constructor
    public Bundler(Data courses, int lenBundle) {
        // TODO add exceptions for lenBundle smaller than 1, empty courses object etc

        setCourses(courses);
        this.lenBundle = lenBundle;
        findBundles();
    }

    // Setter and getter methods
    public void setCourses(Data courses){
        this.courses = courses;
    }
    public Data getCourses(){
        return this.courses;
    }

    public ArrayList<String[]> getBundles(){
        return this.bundles;
    }

    public int getLenBundle(){
        return this.lenBundle;
    }

    // Interface methods
    public void printBundles(){
        // prints course codes in each bundle
        for (int i=0; i<this.bundles.size(); i++){
            System.out.println(Arrays.toString(this.bundles.get(i)));
        }
    }

    public void findBundles(){
        // interface method - starts the recursive call

        this.bundles = new ArrayList<String[]>();
        findBundles(0, new Bundle(this.lenBundle));
    }

    // Algorithm methods
    private boolean checkRules(Bundle curBundle, Course curCourse){
        // TODO incorporate other rules as well

        // first rule: must have 4 courses in M and 4 in L
        if (curBundle.getNumPerTerm(curCourse.getTerm())>3) {
            return false;
        }
        // second rule: must only have 1 management module
        if ((curCourse.getManagerial()) && (curBundle.getNumManagerial()>0)){
            return false;
        }
        return true;
    }

    private void findBundles(int startIdx, Bundle curBundle){
        // Base case
        if (curBundle.isFull()){ // if bundle has filled
            this.bundles.add(curBundle.getBundleCourseCodes());
            return;
        }

        // Compound case
        for (int i=startIdx; i<this.courses.getLength(); i++){
            Course curCourse = this.courses.getCourse(i);
            if (checkRules(curBundle, curCourse)){
                curBundle.addCourse(curCourse);
                findBundles(i+1, curBundle);
                curBundle.removeLastCourse();
            }
        }
    }
}

class Bundle {
    // this class represents a set of courses that a student might take for an academic year or similar
    // works like a stack. You can only push and pop courses

    private final int lenBundle;
    private Course[] bundle;
    private int count; // shows number of courses currently in the bundle
    private int[] numPerTerm; // represents number of courses currently in the bundle per term. numPerTerm[0] is for M, and numPerTerm[1] is for L
    private int numManagerial; // number of managerial courses

    // Constructor
    public Bundle(int len){
        // initialize attributes
        this.lenBundle = len;
        this.bundle = new Course[lenBundle];
        this.count = 0;
        this.numPerTerm = new int[2];
        this.numManagerial = 0;
    }

    public Course[] getBundle(){
        return this.bundle;
    }

    public int getNumPerTerm(Term term){
        return this.numPerTerm[mapFromTermToInt(term)];
    }

    public int getNumManagerial(){
        return this.numManagerial;
    }

    public void printBundle(){
        for (int i=0; i<this.lenBundle; i++){
            this.bundle[i].printCourse();
        }
    }

    public String[] getBundleCourseCodes(){
        // returns the codes of courses currently in the bundle. returns null if no courses in the bundle
        if (this.count == 0) return null;

        String[] codes = new String[this.lenBundle];

        // notice that for loop goes until count instead of lenBundle
        for (int i=0; i<this.count; i++){
            codes[i] = this.bundle[i].getCode();
        }
        return codes;
    }

    private int mapFromTermToInt(Term term){
        // TODO add consideration for Easter term
        if (term == Term.M) {
            return 0;
        } else if (term == Term.L) {
            return 1;
        } else {
            // TODO throw exception instead
            return -1;
        }
    }

    public void addCourse(Course course){
        // push course at end of the "stack"

        if (isFull()){
            // TODO throw exception when bundle is full
            System.out.println("Bundle is already full");
            return;
        }
        this.bundle[count] = course;
        this.numPerTerm[mapFromTermToInt(course.getTerm())]++;
        if (course.getManagerial()){
            this.numManagerial++;
        }
        this.count++;
    }

    public void removeLastCourse(){
        // remove last course in the "stack" (bundle)
        if (count == 0){
            return;
        }
        this.count--;
        this.numPerTerm[mapFromTermToInt(this.bundle[count].getTerm())]--;
        if (this.bundle[count].getManagerial()){
            this.numManagerial--;
        }
        this.bundle[count] = null;
    }

    public boolean isFull(){
        // returns if the bundle is full
        if (count >= lenBundle){
            return true;
        } else {
            return false;
        }
    }
}
