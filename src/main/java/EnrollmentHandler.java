import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class EnrollmentHandler {

    private Map<String, Student> students ;
    private Map<String, Course> courses;


    public EnrollmentHandler() {
        students = new HashMap<String, Student>();
        courses = new HashMap<String, Course>();

    }

    public void addCourse(Course course) {
        courses.put(course.getCid(), course);
    }

    public void addStudent(Student student) {
        students.put(student.getSid(), student);
    }


    public EnrollmentStatusEnum getEnrollmentStatusForCourse(String sid, String cid) {

        Student student = students.get(sid);
        Course course = courses.get(cid);

        EnrollmentStatusEnum status = student.getEnrollmentStatusForCourse(course);

        if (status == null) {
            throw new NoSuchElementException("course with id not found");
        }

        return status;

    }
}
