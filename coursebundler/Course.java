package coursebundler;

/**
 * Represents an academic course that can take place over a single term.
 */
public class Course {
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

    /**
     * Prints the atttributes of the course class to stdout.
     */
    public void printCourse(){
        System.out.println("Code: " + getCode() + ", " + "Name: " + getName() + ", " + "Term: " + getTerm() + ", " + "Managerial: " + getManagerial());
    }
}

/**
 * Represents the Cambridge terms (Michaelmas, Lent, Easter)
 */
enum Term {
    M, // Michaelmas
    L, // Lent
    E // Easter
}
