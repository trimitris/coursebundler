package coursebundler;

import coursebundler.*;
import java.util.ArrayList;

public class Bundler {
    private Data courses;
    private ArrayList<Bundle> bundles;
    private int lenBundle;

    // Setter and getter methods
    public void setCourses(Data courses){
        this.courses = courses;
    }
    public Data getCourses(){
        return this.courses;
    }

    public ArrayList<Bundle> getBundles(){
        return this.bundles;
    }

    public void setLenBundle(int len){
        this.lenBundle = len;
    }
    public int getLenBundle(){
        return this.lenBundle;
    }

    // Interface methods
    public void printBundles(){
        for (int i=0; i<bundles.size(); i++){
            this.bundles.get(i).printBundle();
            System.out.println("-------------------------------");
        }
    }

    // Algorithm methods
    private boolean checkRules(Bundle curBundle, Course curCourse){
        // TODO incorporate other rules as well

        // current rule: must have 4 courses in M and 4 in L
        if (curBundle.getNumPerTerm(curCourse.getTerm())<4) {
            return true;
        } else {
            return false;
        }
    }

    private void findBundles(int startIdx, Bundle curBundle){
        // Base case
        if (curBundle.isFull()){ // if bundle has filled
            bundles.add(curBundle);
            return;
        }

        // Compound case
        for (int i=startIdx; i<courses.getLength(); i++){
            Course curCourse = courses.getCourse(i);
            if (checkRules(curBundle, curCourse)){
                curBundle.addCourse(curCourse);
                startIdx = i+1;
                findBundles(startIdx, curBundle);
                curBundle.removeLastCourse();
            }
        }
    }

    public void findBundles(){
        // interface class

        this.bundles = new ArrayList<Bundle>();
        findBundles(0, new Bundle(this.lenBundle));
    }

    // Constructor
    public Bundler(Data courses, int lenBundle) {
        // TODO add exceptions for lenBundle smaller than 1, empty courses object etc

        setCourses(courses);
        setLenBundle(lenBundle);
        findBundles();
    }
}

class Bundle {
    // this class represents a set of courses that a student might take for an academic year or similar
    // works like a stack. You can only push and pop courses

    private int lenBundle;
    private Course[] bundle;
    private int count;
    private int[] numPerTerm; // represents number of courses currently in the bundle per term. numPerTerm[0] is for M, and numPerTerm[1] is for L

    public Course[] getBundle(){
        return this.bundle;
    }

    public int getNumPerTerm(String term){
        return this.numPerTerm[mapFromTermToInt(term)];
    }

    public void printBundle(){
        for (int i=0; i<lenBundle; i++){
            this.bundle[i].printCourse();
        }
    }

    private int mapFromTermToInt(String term){
        if (term.equals("M")) {
            return 0;
        } else if (term.equals("L")) {
            return 1;
        } else {
            return -1;
        }
    }

    public void addCourse(Course course){
        if (isFull()){
            // TODO throw exception when bundle is full
            System.out.println("Bundle is already full");
            return;
        }
        this.bundle[count] = course;
        this.numPerTerm[mapFromTermToInt(course.getTerm())]++;
        this.count++;
    }

    public void removeLastCourse(){
        // remove last course added in the bundle
        if (count == 0){
            return;
        }
        this.count--;
        this.numPerTerm[mapFromTermToInt(this.bundle[count].getTerm())]--;
        this.bundle[count] = null;
    }

    public boolean isFull(){
        // returns if the bundle is full
        if (count == lenBundle){
            return true;
        } else {
            return false;
        }
    }
    public Bundle(int len){
        this.lenBundle = len;
        this.bundle = new Course[lenBundle];
        this.count = 0;
        this.numPerTerm = new int[2];
    }
}
