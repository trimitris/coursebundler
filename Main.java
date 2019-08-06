package coursebundler;

import coursebundler.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        Data courses = new Data(12);
        for (int i=0; i<courses.getLength(); i++){
            courses.getCourse(i).printCourse();
        }

        Bundler bundler = new Bundler(courses, 8);
        bundler.printBundles();

    }
}
