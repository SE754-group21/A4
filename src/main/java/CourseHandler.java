import java.util.*;

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

    public List<String> getStaff(String cid) {
        if (courses.get(cid) == null) throw new NoSuchElementException("course with id not found");
        List<String> sids = new ArrayList<>();
        List<Staff> allstaff = courses.get(cid).getStaff();
        for (Staff s : allstaff) {
            sids.add(s.getID());
        }
        return sids;
    }

    public List<String> getCHours(String cid) {
        return courses.get(cid).getCHours();
    }
}