import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

public class EnrollmentHandler {

    private Database db;

    public EnrollmentHandler(Database db) {
        this.db = db;
    }

    public boolean enrollStudentCourse(String sid, String cid) {
        Course course = db.getCourse(cid);
        Student student = db.getStudent(sid);
        ConcessionStatusEnum status = concessionStatus(student, course);
        if (!studentMeetsPrerequisites(sid, cid) &&
                (status == ConcessionStatusEnum.denied || status == ConcessionStatusEnum.not_applied))
            return false;
        course.addStudent(student);
        boolean qualified = seatsRemaining(course) &&
                (studentMeetsPrerequisites(sid, cid) || status == ConcessionStatusEnum.approved);
        return qualified;
    }

    public ConcessionStatusEnum concessionStatus(Student student, Course course) {
        ConcessionApplication concessionApplication = student.getConcessionApplication(course);
        return concessionApplication.getConcessionStatus();
    }

    public boolean studentMeetsPrerequisites(String sid, String cid) {
        Student student = db.getStudent(sid);
        Course course = db.getCourse(cid);
        List<Course> studentTaken = student.getTakenCourses();
        List<Course> prereqs = course.getPrerequisites();
        return studentTaken.containsAll(prereqs);
    }

    public boolean seatsRemaining(Course course) {
        return course.getRemainingSeats() > 0;
    }

    public EnrollmentStatusEnum getEnrollmentStatusForCourse(String sid, String cid) {

        Student student = db.getStudent(sid);
        if (student == null)
            throw new NoSuchElementException("Student with id not found");
        Course course = db.getCourse(cid);
        EnrollmentStatusEnum status = student.getEnrollmentStatusForCourse(course);
        if (status == null)
            throw new NoSuchElementException("Student not enrolled in this course and no attempt at enrolling made");
        return status;
    }

    public void dropCourse(String sid, String cid) {
        Student student = db.getStudent(sid);
        Course course = db.getCourse(cid);
        student.setEnrollmentStatusForCourse(course, EnrollmentStatusEnum.not_enrolled);
        course.removeStudent(student);
    }

    public int getWaitingListPositionForStudent(String sid, String cid) {
        Student student = db.getStudent(sid);
        Course course = db.getCourse(cid);

        EnrollmentStatusEnum status = student.getEnrollmentStatusForCourse(course);

        if (status != EnrollmentStatusEnum.waiting_list) {
            throw new NoSuchElementException("student is not on the waiting list for this course");
        }
        int waitingListPosition = course.getWaitingListPosition(student);
        return waitingListPosition;
    }

    public List<Course> getCoursesCompletedInYear(int year, String sid) {
        Student student = db.getStudent(sid);
        List<Course> coursesCompletedInYear = new ArrayList<>();

        for (Course course :  student.getTakenCourses()) {
            int enrolledYear = student.getYearEnrolled(course);
            if (enrolledYear == year) {
                coursesCompletedInYear.add(course);
            }
        }
        return coursesCompletedInYear;
    }
}
