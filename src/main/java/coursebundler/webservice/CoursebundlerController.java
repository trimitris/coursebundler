package coursebundler.webservice;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import coursebundler.engine.*;


@Controller
public class CoursebundlerController {
    private Data allCourses;
    private SelectedData selectedData;
    private BundleBuilder bundler;

    public CoursebundlerController(){
        // import courses
        String fileName = "4th-year-modules-eng.csv";
        try {
            this.allCourses = new Data(fileName);
            this.allCourses.printAllCourses();
            System.out.println("");
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/select-courses")
    public String selectCoursesForm(Model model) {

        // Set model attributes
        List<String> courseCodes = Arrays.asList(this.allCourses.getCourseCodes());
        model.addAttribute("course_codes", courseCodes);
        model.addAttribute("selected_courses", new SelectedCourses());

        return "select-courses";
    }

    @PostMapping("/select-courses")
    public String selectCoursesSubmit(@ModelAttribute("selected_courses") SelectedCourses selectedCourses) {

        // TODO check if the course codes in selectedCourses exist in the keySet of allCourses

        // collect only the courses the user has selected into a SelectedData object
        this.selectedData = new SelectedData(this.allCourses, selectedCourses.getSelectedCourses());

        // Generate all course bundles that obey the rules

        this.bundler = new BundleBuilder(this.selectedData, 8);
        System.out.println("Number of bundles: " + this.bundler.getBundles().size());

        return "present-bundles";
    }

    @GetMapping("/get-bundles")
    public ResponseEntity<BundlesJSON> getBundles(Model model) {
        System.out.println("Trying to return bundles!"); // TODO remove
        BundlesJSON bundlesJSON = new BundlesJSON(this.selectedData, this.bundler.getBundles());
        if (bundlesJSON == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(bundlesJSON);
        }
    }
}
