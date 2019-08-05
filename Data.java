package coursebundle;

// TODO add documentation and unit tests

public class Data {
    private int len;
    private Course[] courses;

    public Data(int len){
        this.len = len;
        this.courses = new Course[len];
        generate();
    }

    public void generate(){
        String[] codes = {"4e1", "4e2", "4e3"};
        String[] names = {"Management of technology", "Project management", "Computer vision"};
        String[] term = {"M", "L", "L"};

        for (int i=0; i<this.len; i++){
            Course module = new Course(codes[i], names[i], term[i]);
            this.courses[i] = module;
        }
    }

    public Course getCourse(int i){
        // TODO validate input, check if i is between 0 and len
        return this.courses[i];
    }

    public int getLength(){
        return this.len;
    }
}

class Course {
    private String code;
    private String name;
    private String term;

    public Course(String code, String name, String term){
        setCode(code);
        setName(name);
        setTerm(term);
    }

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

    public void setTerm(String term){
        this.term = term;
    }
    public String getTerm(){
        return this.term;
    }

    public void printCourse(){
        System.out.println("Code: " + getCode() + ", " + "Name: " + getName() + ", " + "Term: " + getTerm());
    }
}
