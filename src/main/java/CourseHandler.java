import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class CourseHandler {
    private Map<String, Course> courses;
    public CourseHandler() {
        courses = new HashMap<String, Course>();
    }

    public void addCourse(Course course) {
        courses.put(course.getCid(), course);
    }

    public String getCname(String cid) {
        return courses.get(cid).getCname();
    }
}