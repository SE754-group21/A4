import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class EnrollmentHandler {

    private Database db;


    public EnrollmentHandler(Database db) {
        this.db = db;
    }

    public boolean enrollStudentCourse(String sid, String cid) {
        Course course = db.getCourse(cid);
        return seatsRemaining(course) && studentMeetsPrerequisites(sid, cid);
    }

    public boolean studentMeetsPrerequisites(String sid, String cid) {
        Student student = db.getStudent(sid);
        Course course = db.getCourse(cid);
        List<Course> studentTaken = student.getTakenCourses();
        List<Course> prereqs = course.getPrerequisites();
        return studentTaken.containsAll(prereqs);
    }

    public boolean seatsRemaining(Course course) {
        return true;
    }

    public EnrollmentStatusEnum getEnrollmentStatusForCourse(String sid, String cid) {

        Student student = db.getStudent(sid);
        if (student == null) {
            throw new NoSuchElementException("Student with id not found");
        }

        Course course = db.getCourse(cid);

        EnrollmentStatusEnum status = student.getEnrollmentStatusForCourse(course);

        if (status == null) {
            throw new NoSuchElementException("Student not enrolled in this course");
        }

        return status;

    }

    public int getWaitingListPositionForStudent(String sid, String cid) {
        Student student = db.getStudent(sid);
        Course course = db.getCourse(cid);

        EnrollmentStatusEnum status = student.getEnrollmentStatusForCourse(course);

        if (status != EnrollmentStatusEnum.waiting_list) {
            throw new NoSuchElementException("student is not on the waiting list for this course");
        }
        int waitingListPosition = student.getWaitingListNumber(course);

        return waitingListPosition;
    }

    public String getConcessionStatus(String sid, String cid) {
        Student student = db.getStudent(sid);
        Course course = db.getCourse(cid);

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
        Student student = db.getStudent(sid);
        Course course = db.getCourse(cid);

        ConcessionApplication concessionApplication = student.getConcessionApplication(course);
        return concessionApplication.getConcessionReason();
    }

    public String getStatusReason(String sid, String cid) {
        Student student = db.getStudent(sid);
        Course course = db.getCourse(cid);

        ConcessionApplication concessionApplication = student.getConcessionApplication(course);
        return concessionApplication.getStatusReason();
    }
}
