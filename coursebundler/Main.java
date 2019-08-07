package coursebundler;

import coursebundler.*;

public class Main {
    public static void main(String[] args){
        Data courses = new Data(12);

        Bundler bundler = new Bundler(courses, 8);
        bundler.printBundles();
        System.out.println("Number of bundles: " + bundler.getBundles().size());

        for (int i=0; i<courses.getLength(); i++){
            courses.getCourse(i).printCourse();
        }
    }
}
