package coursebundler.engine;

import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Arrays;


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

    /**
     * Constructor accepts a fileName in CSV format. It processes the file and creates a data set in memory
     * with the course information found in the file. Any course entries in the CSV that have improper format
     * are reported to stdout and are not parsed to the data set.
     * @param fileName name of CSV file (file should be in Data/ directory)
     * @throws FileNotFoundException if the fileName does not exist in Data/ directory
     */
    public Data(String fileName) throws IOException, RuntimeException {
        String wd = System.getProperty("user.dir"); // root directory of project
        String pathTofilename = wd+ "/data/" + fileName;
        if (!checkFileExists(pathTofilename)) throw new FileNotFoundException("File " + fileName + " not found in Data/ directory");

        // initialize attributes
        this.numCourses = 0;
        this.courses = new HashMap<String, Course>();

        try {
            parseCSV(pathTofilename);
        } catch (Exception e) {
            throw e;
        }

        if (this.numCourses<1) throw new RuntimeException("No valid courses were found in the CSV file");
    }

    private static boolean checkFileExists(String pathToFileName){
        File f = new File(pathToFileName);
        return f.exists() && !f.isDirectory();
    }

    private void parseCSV(String file) throws IOException {
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReader(filereader);

            // read and validate header of table in CSV file
            String[] header = csvReader.readNext();
            if (header == null) throw new RuntimeException("File at " + file + " is empty");
            if (header.length != 4) throw new RuntimeException("Header of table in CSV file does not have 4 fields");
            String[] expectedHeader = {"Code", "Module", "Term", "Cw"};
            for (int i=0; i<header.length; i++){
                if (!header[i].equals(expectedHeader[i])) throw new RuntimeException("Header does not have proper format" + expectedHeader);
            }

            // read data line by line
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                // check line has 4 arguments
                if (nextLine.length != 4){
                    System.out.println("Following line in CSV does not have 4 fields as required");
                    printLineFromFile(nextLine);
                    continue;
                }
                parseLine(nextLine);
            }

            // Collect course codes for sorting
            this.courseCodes = new String[this.numCourses];
            int idx = 0;
            for (String code: courses.keySet()){
                this.courseCodes[idx] = code;
                idx++;
            }
            Arrays.sort(this.courseCodes);

        } catch (Exception e) {
            throw e;
        }
    }

    private void parseLine(String[] line){
        // the line array argument represents a course in the CSV file

        // Validate CSV entries and transform to proper representations for Term and Assessment
        Term term;
        try {
            term = parseTerm(line[2]);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            printLineFromFile(line);
            return;
        }

        Assessment assess;
        try {
            assess = parseAssessment(line[3]);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            printLineFromFile(line);
            return;
        }

        boolean managerial;
        managerial = parseManagerial(line[0]);

        Course course = new Course(line[0], line[1], term, managerial, assess);

        // check for duplicate course codes
        if (this.courses.containsKey(course.getCode())){
            System.out.println("The course shown right below has the same code with another course, and so it was skipped: ");
            course.printCourse();
            return;
        }
        // add course
        this.courses.put(course.getCode(), course);
        this.numCourses++;
    }

    /**
     * Parses the third column (term) of a line of the CSV from its String representaiton to the enum type Term
     * @param field third column of a line of the CSV
     * @return Term object equivalent of the field
     * @throws IllegalStateException if the field is not one of the following: M, L, E
     */
    private Term parseTerm(String field) throws IllegalStateException{
        switch (field){
            case "M":
                return Term.M;
            case "L":
                return Term.L;
            case "E":
                return Term.E;
            default:
                throw new IllegalArgumentException("The field term for this course is invalid - it should be one of the following: M,L,E");
        }
    }

    private boolean parseManagerial(String field){
        // assume if course code string has length less than 3, it is not a management course
        if (field.length()>=3){
            // check if course is of group E or is course with code 4I1
            if ((field.toUpperCase().equals("4I1")) || (field.toUpperCase().charAt(1) == 'E')){
                return true;
            }
        }
        return false;
    }

    /**
     * Parses the fourth column (cw) of a line of the CSV from its String representation to its boolean representation.
     * True if the course is only coursework, and false if it is only exams.
     * @param field fourth column of a line of the CSV
     * @return if the course is only coursework
     * @throws IllegalArgumentException if the field is not one of the following: yes, no
     */
    private Assessment parseAssessment(String field) throws IllegalArgumentException{
        switch (field){
            case "ex":
                return Assessment.ex;
            case "cw":
                return Assessment.cw;
            case "both":
                return Assessment.both;
            default:
                throw new IllegalArgumentException("The field assessment for this course is invalid - it should be one of the following: ex, cw, both");
        }
    }

    private void printLineFromFile(String[] line){
        for (int i=0; i<line.length-1; i++){
            System.out.print(line[i]+ ",");
        }
        System.out.print(line[line.length-1]);
        System.out.println("\n");
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

    public String[] getCourseCodes(){ return this.courseCodes; }
}
