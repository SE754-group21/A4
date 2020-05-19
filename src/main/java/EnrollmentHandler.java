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
        boolean qualified = seatsRemaining(course) && (studentMeetsPrerequisites(sid, cid) || status == ConcessionStatusEnum.approved);
        if (!qualified) return false;
        //enroll student
        course.addStudent(student);
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
        int waitingListPosition = student.getWaitingListNumber(course);

        return waitingListPosition;
    }






    public NotificationEvent concessionGetsDeclined(Student student, Course course) {
        ConcessionApplication concessionApplication = student.getConcessionApplication(course);
        concessionApplication.setConcessionStatus(ConcessionStatusEnum.denied);
        setEnrollmentStatusForCourse(student, course, EnrollmentStatusEnum.concession_denied);

        NotificationEvent notificationEvent = new NotificationEvent(student, course, NotificationEventTypeEnum.concession_denied);

        return notificationEvent;
    }

    public NotificationEvent moveOffWaitingList(Student student, Course course) {

        setEnrollmentStatusForCourse(student, course, EnrollmentStatusEnum.enrolled);
        NotificationEvent notificationEvent = new NotificationEvent(student, course, NotificationEventTypeEnum.moved_off_waiting_list);

        return notificationEvent;
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
