package coursebundler.engine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Stores the data of the courses for the academic year, selected by the user.
 * The data have information for each course as per the attributes of the Course class.
 */
public class SelectedData {
    private int numCourses;
    private HashMap<String, Course> courses; // key: course code
    private String[] courseCodes;

    // Constructor

    /**
     * Constructor collects all courses selected by the user, so that they can be used by the BundleBuilder class
     * to build the bundles.
     *
     * @param data instance of Data class containing all available courses for an academic year
     * @param selectedCourses list of course codes of the coureses selected by the user
     */
    public SelectedData(Data data, List<String> selectedCourses){
        // initialize attributes
        this.numCourses = 0;
        this.courses = new HashMap<String, Course>();

        for (int i=0; i<selectedCourses.size(); i++){
            String code = selectedCourses.get(i);
            this.courses.put(code, data.getCourseByCode(code));
            this.numCourses++;
        }

        // Collect course codes for sorting
        this.courseCodes = new String[this.numCourses];
        int idx = 0;
        for (String code: this.courses.keySet()){
            this.courseCodes[idx] = code;
            idx++;
        }
        Arrays.sort(this.courseCodes);

        if (this.numCourses<1) throw new RuntimeException("Either no courses selected or no valid courses were found in the CSV file");
    }

    /**
     * Retrieves information about a particular course at an index
     * @param i index of course in courseCodes array
     * @return the course object at the specified index
     */
    public Course getCourseByIdx(int i){
        // get individual courses
        if ((i<0) || (i>=this.numCourses)) throw new ArrayIndexOutOfBoundsException();

        return this.courses.get(this.courseCodes[i]);
    }

    /**
     * Prints all courses data to stdout
     */
    public void printAllCourses(){
        for (int i=0; i<this.numCourses; i++) {
            getCourseByIdx(i).printCourse();
        }
    }

    /**
     * Returns an array of all the courses objects
     * @return Array of Course objects
     */
    public Course[] getAllCourses(){
        Course[] allCourses = new Course[this.numCourses];
        for (int i=0; i<this.numCourses; i++) {
            allCourses[i] = getCourseByIdx(i);
        }
        return allCourses;
    }

    /**
     * Returns course object with specified course code. If code does not exist, returns null.
     * @param code Course code (unique course id given by university usually)
     * @return Course object with specified code. Returns null if course with given code does not exist.
     */
    public Course getCourseByCode(String code){
        return this.courses.get(code);
    }

    /**
     * Returns the number of courses in the data set
     * @return number of courses in data set
     */
    public int getNumCourses(){
        return this.numCourses;
    }

    public String[] getCourseCodes(){ return this.courseCodes; }
}