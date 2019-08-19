package coursebundler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import coursebundler.engine.BundleBuilder;
import coursebundler.engine.Data;

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
        System.out.println("Number of bundles: " + bundler.getBundles().size());

        // find bundles similar to a target bundle

        Integer targetBundle = 0;
        System.out.println("Target bundle: ");
        System.out.println(Arrays.toString(bundler.getBundles().get(targetBundle)));
        System.out.println("Similar bundles (by id)");
        ArrayList<Integer> similarList = bundler.findSimilarBundlesTo(targetBundle,1);
        System.out.println(Arrays.toString(similarList.toArray()));
        // verify method findSimilarBundlesTo
        for (int i=0; i<bundler.getBundles().size(); i++){
            bundler.printBundleHighlightDifferences(i, targetBundle);
        }
        System.out.println("");

        // find bundles that contain certain courses
        ArrayList<String> requiredCodes = new ArrayList<>();
        requiredCodes.add("4f10 ");
        requiredCodes.add("4f5 ");
        requiredCodes.add("4m21 ");
        // TODO test the below function
        ArrayList<Integer> requestedBundles = bundler.findBundlesWithCourses(requiredCodes);
        System.out.println(Arrays.toString(requestedBundles.toArray()));
        for (int i=0; i<bundler.getBundles().size(); i++){
            bundler.printBundleHilghlightCourses(i, requiredCodes);
        }
    }
}
