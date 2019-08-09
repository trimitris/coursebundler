package coursebundler;

// TODO sort the courses list

public class Data {
    private final int len; // has no setter function, can only be set once
    private Course[] courses;

    public Data(int len){
        this.len = len;
        this.courses = new Course[len];
        generate();
    }

    // TODO make sure all the course codes are unique
    public void generate(){
        // TODO read files from external file
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

        for (int i=0; i<this.len; i++){
            Course course = new Course(codes[i], names[i], term[i], managerial[i]);
            this.courses[i] = course;
        }
    }

    public Course getCourse(int i){
        // get individual courses
        if ((i<0) || (i>=this.len)) throw new ArrayIndexOutOfBoundsException();
        return this.courses[i];
    }

    public void printAllCourses(){
        for (int i=0; i<this.len; i++) {
            getCourse(i).printCourse();
        }
    }

    // TODO get courses by id

    public int getLength(){
        return this.len;
    }
}
