package coursebundler;

// TODO add documentation and unit tests
// TODO sort the courses list

public class Data {
    private int len;
    private Course[] courses;

    public Data(int len){
        this.len = len;
        this.courses = new Course[len];
        generate();
    }

    public void generate(){
        String[] codes = {"4e1", "4e4", "4e11", "4e12", "4f5","4f7", "4f8", "4f10", "4f12", "4m12", "4m17", "4m21"};
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
            "Practical optimozation",
            "Software engineering and design"};
        String[] term = {"M","M","L","L","L","L","L","M","M","L","M","L"};

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
