package coursebundler;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args){
        // Build a data object
        String fileName = "4th-year-modules-eng.csv";
        Data courses;
        try {
            courses = new Data(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File can't be parsed due to above exception, hence the program terminates");
            return;
        }


        // Generate and print all course bundles that obey the rules
        BundleBuilder bundler = new BundleBuilder(courses, 8);
        bundler.printBundles();
        System.out.println("Number of bundles: " + bundler.getBundles().size());

//        courses.printAllCourses();
    }
}
