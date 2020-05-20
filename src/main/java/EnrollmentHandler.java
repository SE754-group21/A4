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

    public ConcessionStatusEnum concessionStatus(Student student, Course course) {
        ConcessionApplication concessionApplication = student.getConcessionApplication(course);
        return concessionApplication.getConcessionStatus();
    }

    public String getConcessionStatus(String sid, String cid) {
        Student student = db.getStudent(sid);
        Course course = db.getCourse(cid);
        ConcessionStatusEnum concessionStatus = concessionStatus(student, course);
        if (concessionStatus == ConcessionStatusEnum.pending)
            return "Pending - awaiting course approval";
        else if (concessionStatus == ConcessionStatusEnum.denied)
            return "Denied - concession not approved";
        else if (concessionStatus == ConcessionStatusEnum.approved)
            return "Approved - concession accepted and enrollment complete";
        else
            return "The student has no concession for this course";
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

    private void setEnrollmentStatusForCourse(Student student, Course course, EnrollmentStatusEnum status) {
        student.setEnrollmentStatusForCourse(course, status);
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
        return null;
    }
}
