package coursebundler;

import java.util.EnumMap;

public class Bundle {
    // this class represents a set of courses that a student might take for an academic year or similar
    // works like a stack. You can only push and pop courses

    private final int lenBundle;
    private Course[] bundle;
    private int count; // shows number of courses currently in the bundle
    private EnumMap<Term, Integer> numPerTerm; // represents number of courses currently in the bundle per term
    private int numManagerial; // number of managerial courses

    // Constructor
    public Bundle(int len){
        // initialize attributes
        this.lenBundle = len;
        this.bundle = new Course[lenBundle];
        this.count = 0;
        initializeNumPerTerm();
        this.numManagerial = 0;
    }

    public Course[] getBundle(){
        return this.bundle;
    }

    public Integer getNumPerTerm(Term term){
        return this.numPerTerm.get(term);
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

    private void initializeNumPerTerm(){
        this.numPerTerm = new EnumMap<Term, Integer>(Term.class);
        for (Term term: Term.values()){
            this.numPerTerm.put(term, 0);
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
        Term term = course.getTerm();
        this.numPerTerm.put(term, this.numPerTerm.get(term)+1);
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
        Term term = bundle[count].getTerm();
        this.numPerTerm.put(term, this.numPerTerm.get(term)-1);
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
