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


