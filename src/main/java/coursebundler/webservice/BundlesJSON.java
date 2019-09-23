package coursebundler.webservice;

import java.util.ArrayList;
import coursebundler.engine.*;

public class BundlesJSON {
    private Course[] coursesList;
    private BundleAndID[] bundles;

    public void setCoursesList(Course[] list){
        this.coursesList = list;
    }

    public Course[] getCoursesList(){
        return this.coursesList;
    }

    public BundleAndID[] getBundles() {
        return bundles;
    }

    public void setBundles(ArrayList<String[]> bundles) {
        this.bundles = new BundleAndID[bundles.size()];
        for (int i=0; i<bundles.size(); i++){
            this.bundles[i] = new BundleAndID(i, bundles.get(i));
        }
    }

    public BundlesJSON(SelectedData data, ArrayList<String[]> bundles){
        setCoursesList(data.getAllCourses());
        setBundles(bundles);
    }
}

class BundleAndID {
    private int id;
    private String[] courses;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String[] getCourses() {
        return courses;
    }
    public void setCourses(String[] courses) {
        this.courses = courses;
    }

    public BundleAndID(int id, String[] courses){
        setId(id);
        setCourses(courses);
    }
}
