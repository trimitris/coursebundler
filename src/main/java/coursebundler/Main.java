package coursebundler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import coursebundler.engine.BundleBuilder;
import coursebundler.engine.Data;
import coursebundler.utilities.*;

public class Main {
    public static void main(String[] args){
        // Build a data object
        String fileName = "4th-year-modules-eng.csv";
//        String fileName = "sample.csv";
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
        bundler.printAllBundles();
        System.out.println("Number of bundles: " + bundler.getBundles().size());
        Integer targetBundle = 0;
        System.out.println("Target bundle: ");
        System.out.println(Arrays.toString(bundler.getBundles().get(targetBundle)));

        System.out.println("Similar bundles (by id)");
        ArrayList<Integer> similarList = bundler.findSimilarBundlesTo(targetBundle,1);
        System.out.println(Arrays.toString(similarList.toArray()));
//        bundler.printBundlesFromBundleIds(similarList);

        for (int i=0; i<bundler.getBundles().size(); i++){
            bundler.printBundleDifferencesColor(i, targetBundle);
        }
    }
}
