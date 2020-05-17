import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Database {
    private Map<String, Course> courses;
    private Map<String, Student> students;

    public Database() {
        courses = new HashMap<String, Course>();
        students = new HashMap<String, Student>();
    }


    public void addCourse(String cid, Course course) {
    }

    public Course getCourse(String cid) {
        if (courses.get(cid) == null) throw new NoSuchElementException("course with id not found");
        return courses.get(cid);
    }

    public void addStudent(String sid, Student student) {
    }

    public Student getStudent(String sid) {
        if (courses.get(sid) == null) throw new NoSuchElementException("student with id not found");
        return students.get(sid);
    }

    public Map<String, Course> getAllCourses(){
        return courses;
    }

    public void addConcessionApplication(String cid, String sid, ConcessionApplication concessionApp) {
    }

}
