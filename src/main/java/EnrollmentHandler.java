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
        if (student == null) {
            throw new NoSuchElementException("student with id not found");
        }

        Course course = courses.get(cid);

        EnrollmentStatusEnum status = student.getEnrollmentStatusForCourse(course);

        if (status == null) {
            throw new NoSuchElementException("course with id not found");
        }

        return status;

    }

    public int getWaitingListPositionForStudent(String sid, String cid) {
        Student student = students.get(sid);
        Course course = courses.get(cid);

        EnrollmentStatusEnum status = student.getEnrollmentStatusForCourse(course);

        if (status != EnrollmentStatusEnum.waiting_list) {
            throw new NoSuchElementException("student is not on the waiting list for this course");
        }
        int waitingListPosition = student.getWaitingListNumber(course);

        return waitingListPosition;
    }

    public String getConcessionStatus(String sid, String cid) {
        Student student = students.get(sid);
        Course course = courses.get(cid);

        ConcessionApplication concessionApplication = student.getConcessionApplication(course);
        ConcessionStatusEnum concessionStatus = concessionApplication.getConcessionStatus();

        if (concessionStatus == ConcessionStatusEnum.pending) {
            return "Pending - awaiting course approval";
        }
        else if (concessionStatus == ConcessionStatusEnum.denied) {
            return "Denied - concession not approved";
        }


        return "";
    }

    public String getConcessionReason(String sid, String cid) {
        Student student = students.get(sid);
        Course course = courses.get(cid);

        ConcessionApplication concessionApplication = student.getConcessionApplication(course);
        return concessionApplication.getConcessionReason();
    }

    public String getStatusReason(String sid, String cid) {
        Student student = students.get(sid);
        Course course = courses.get(cid);

        ConcessionApplication concessionApplication = student.getConcessionApplication(course);
        return concessionApplication.getStatusReason();
    }
}
