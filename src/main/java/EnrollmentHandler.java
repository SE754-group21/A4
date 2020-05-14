import java.util.HashMap;
import java.util.List;
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

    public boolean enrollStudentCourse(String sid, String cid) {

    }

    public boolean studentMeetsPrerequisites(String sid, String cid) {
        if (courses.get(cid) == null) throw new NoSuchElementException("course with id not found");
        if (students.get(sid) == null) throw new NoSuchElementException("student with id not found");
        Student student = students.get(sid);
        Course course = courses.get(cid);
        List<Course> studentTaken = student.getTakenCourses();
        List<Course> prereqs = course.getPrerequisites();
        return studentTaken.containsAll(prereqs);
    }

    public boolean seatsRemaining(String cid) {
        return true;
    }



    public EnrollmentStatusEnum getEnrollmentStatusForCourse(String sid, String cid) {

        Student student = students.get(sid);
        if (student == null) {
            throw new NoSuchElementException("Student with id not found");
        }

        Course course = courses.get(cid);

        EnrollmentStatusEnum status = student.getEnrollmentStatusForCourse(course);

        if (status == null) {
            throw new NoSuchElementException("Student not enrolled in this course");
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
        else if (concessionStatus == ConcessionStatusEnum.approved) {
            return "Approved - concession accepted and enrollment complete";
        }
        else {
            return "The student has no concession for this course";
        }
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
