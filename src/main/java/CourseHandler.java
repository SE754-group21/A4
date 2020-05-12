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
        if (courses.get(cid) == null) throw new NoSuchElementException("course with id not found");
        return courses.get(cid).getCname();
    }

    public String getCdesc(String cid) {
        if (courses.get(cid) == null) throw new NoSuchElementException("course with id not found");
        return courses.get(cid).getCdesc();
    }
}