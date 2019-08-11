package coursebundler;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Arrays;

// TODO read files from external file (e.g. CSV)

/**
 * Retrieves and stores the data of all available courses for the academic year.
 * The data have information for each course as per the attributes of the Course class.
 * They are retrieved from an external source.
 * This source might be a CSV file, or an API. Currently the "source" is hardcoded data.
 */
public class Data {
    private int numCourses; // has no setter function
    private HashMap<String, Course> courses; // key: course code
    private String[] courseCodes;

    // Constructor
    public Data(){
        this.numCourses = 0;
        this.courses = new HashMap<String, Course>();
        generate();
    }

    public static void readDataLineByLine(String file) {
        try {
            // Create an object of filereader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(file);

            // create csvReader object passing
            // file reader as a parameter
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generate(){
        String[] codes = {"4e1", "4e4", "4e11", "4e12", "4f5","4f7", "4f8",
            "4f10", "4f12", "4m12", "4m17", "4m21"};
        String[] names = {
            "Innovation and strategic management of intellectual property",
            "management of technology",
            "Strategic management",
            "project management",
            "advanced information theory and coding",
            "statistical signal analysis",
            "image processing and image coding",
            "Deep learning and structured data",
            "Computer vision",
            "Pdes and variational methods",
            "Practical optimization",
            "Software engineering and design"};
        Term[] term = {Term.M,Term.M,Term.L,Term.L,Term.L,Term.L,Term.L,Term.M,Term.M,Term.L,Term.M,Term.L};
        Boolean[] managerial = {true, true, true, true, false, false, false,
            false, false, false, false, false};

        for (int i=0; i<12; i++){
            Course course = new Course(codes[i], names[i], term[i], managerial[i]);

            // check for duplicate course codes
            if (this.courses.containsKey(course.getCode())){
                System.out.println("The course shown right below has the same code with another course: ");
                course.printCourse();
                continue;
            }
            this.courses.put(course.getCode(), course);
            this.numCourses++;
        }

        // Collect course codes for sorting
        this.courseCodes = new String[this.numCourses];
        int idx = 0;
        for (String code: courses.keySet()){
            this.courseCodes[idx] = code;
            idx++;
        }
        Arrays.sort(this.courseCodes);
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
}
