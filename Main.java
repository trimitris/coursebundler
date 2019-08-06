package coursebundler;

import coursebundler.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        Data courses = new Data(12);
        // for (int i=0; i<courses.getLength(); i++){
        //     courses.getCourse(i).printCourse();
        // }

        Bundler bundler = new Bundler(courses, 8);
        bundler.printBundles();

        // Bundle bundle = new Bundle(8);
        // for (int i=0; i<9; i++){
        //     bundle.addCourse(courses.getCourse(i));
        // }
        // bundle.removeLastCourse();
        // bundle.removeLastCourse();
        //
        // bundle.addCourse(courses.getCourse(7));
        //
        // bundle.printBundle();
    }
}
