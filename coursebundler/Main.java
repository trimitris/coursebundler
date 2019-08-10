package coursebundler;

public class Main {
    public static void main(String[] args){
        // Build a data object
        Data courses = new Data();

        // Generate and print all course bundles that obey the rules
        BundleBuilder bundler = new BundleBuilder(courses, 8);
        bundler.printBundles();
        System.out.println("Number of bundles: " + bundler.getBundles().size());

        courses.printAllCourses();
    }
}
