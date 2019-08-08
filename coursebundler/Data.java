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

class Course {
    private String code;
    private String name;
    private Term term;
    private Boolean managerial;

    public Course(String code, String name, Term term, Boolean managerial){
        setCode(code);
        setName(name);
        setTerm(term);
        setManagerial(managerial);
    }

    // Setter and getter methods
    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void setTerm(Term term){
        this.term = term;
    }
    public Term getTerm(){
        return this.term;
    }

    public void setManagerial(Boolean managerial){
        this.managerial = managerial;
    }
    public Boolean getManagerial(){
        return this.managerial;
    }

    public void printCourse(){
        System.out.println("Code: " + getCode() + ", " + "Name: " + getName() + ", " + "Term: " + getTerm() + ", " + "Managerial: " + getManagerial());
    }
}

// Represents the Cambridge terms (when the different modules take place)
enum Term {
    M, // Michaelmas
    L, // Lent
    E // Easter
}
