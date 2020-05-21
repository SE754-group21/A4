import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

public class Database {
    private Map<String, Course> courses;
    private Map<String, Degree> degrees;
    private Map<String, Student> students;
    private Map<String, ConcessionApplication> concessions;
    private int concessionVal;

    public Database() {
        courses = new HashMap<String, Course>();
        degrees = new HashMap<String, Degree>();
        students = new HashMap<String, Student>();
        concessions = new HashMap<String, ConcessionApplication>();
    }


    public void addCourse(String cid, Course course) {
        courses.put(cid, course);
    }

    public Course getCourse(String cid) {
        if (courses.get(cid) == null) throw new NoSuchElementException("course with id not found");
        return courses.get(cid);
    }

    public Degree getDegree(String did) {
        if (degrees.get(did) == null) throw new NoSuchElementException("course with id not found");
        return degrees.get(did);
    }

    public void addStudent(String sid, Student student) {
        students.put(sid, student);
    }

    public Student getStudent(String sid) {
        if (students.get(sid) == null) throw new NoSuchElementException("student with id not found");
        return students.get(sid);
    }

    public Map<String, Course> getAllCourses(){
        return courses;
    }

    public String addConcessionApplication(ConcessionApplication concessionApp) {
        String uniqueID = UUID.randomUUID().toString();
        concessions.put(uniqueID, concessionApp);
        return uniqueID;
    }


}
