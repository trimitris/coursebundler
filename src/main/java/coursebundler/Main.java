package coursebundler;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        // Build a data object
        String fileName = "4th-year-modules-eng.csv";
        Data courses;
        try {
            courses = new Data(fileName);
            courses.printAllCourses();
            System.out.println("");
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            return;
        }


        // Generate and print all course bundles that obey the rules
        BundleBuilder bundler = new BundleBuilder(courses, 8);
        bundler.printBundles();
        System.out.println("Number of bundles: " + bundler.getBundles().size());

    }
}
