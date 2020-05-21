import java.time.LocalDate;
import java.util.*;

public class Student extends User {

    private String sid;
    private List<Course> courses;
    private List<Course> enrollmentCart = new ArrayList<>();
    private Map<Course, ConcessionApplication> applications;
    Map<Course, VirtualListEnum> queues;
    private Map<Course, EnrollmentStatusEnum> enrollment = new HashMap<>();
    private Map<Course, LocalDate> enrolledDates = new HashMap<>();

    public Student() {
        applications = new HashMap<>();
        courses = new ArrayList<>();
        queues = new HashMap<>();
    }

    public void addConcession(Course course, ConcessionApplication app) {
        applications.put(course, app);
    }

    public void setSid(String sid){
        this.sid = sid;
    }

    public String getSid(){
        return this.sid;
    }

    public void setTakenCourses(List<Course> courses){
        this.courses = courses;
    }

    public EnrollmentRequestStatusEnum getEnrollmentRequestStatusForCourse(Course course){
        return null;
    }

    public EnrollmentStatusEnum getEnrollmentStatusForCourse(Course course) {
        if (enrollment.get(course) == null)
            return EnrollmentStatusEnum.not_enrolled;
        return enrollment.get(course);
    }

    public List<Course> getTakenCourses() {
        return courses;
    }

    public int getWaitingListNumber(Course course) {
        return 0;
    }

    public ConcessionApplication getConcessionApplication(Course course) {
        return applications.get(course);
    }

    public void setEnrollmentRequestStatusForCourse(Course course, EnrollmentRequestStatusEnum enrollmentRequestStatus){
    }

    public void setEnrollmentStatusForCourse(Course course, EnrollmentStatusEnum status) {
        enrollment.put(course, status);

    }

    public void setVirtualList(Course course, VirtualListEnum status) {
        queues.put(course, status);
        updateVirtualList(course);
    }

    public ConcessionStatusEnum getConcessionStatus(Course course) {
        ConcessionApplication app = applications.get(course);
        if (app == null) return ConcessionStatusEnum.not_applied;
        return app.getConcessionStatus();
    }

    public VirtualListEnum getVirtualStatus(Course course) {
        return queues.get(course);
    }

    public boolean meetsPrereqs(Course course) {
        List<Course> studentTaken = getTakenCourses();
        List<Course> prereqs = course.getPrerequisites();
        return studentTaken.containsAll(prereqs);
    }

    public void updateConcession(Course course) {
        ConcessionStatusEnum concessionEnum = getConcessionStatus(course);
        VirtualListEnum listEnum = getVirtualStatus(course);
        if (concessionEnum == ConcessionStatusEnum.denied)
            setEnrollmentStatusForCourse(course, EnrollmentStatusEnum.concession_denied);
        else if (concessionEnum == ConcessionStatusEnum.pending)
            setEnrollmentStatusForCourse(course, EnrollmentStatusEnum.awaiting_concession);
            //if success
        else {
            if (listEnum == VirtualListEnum.enrolled_list) {
                setEnrollmentStatusForCourse(course, EnrollmentStatusEnum.enrolled);
            } else {
                setEnrollmentStatusForCourse(course, EnrollmentStatusEnum.waiting_list);
            }
        }
    }

    public NotificationEvent updateVirtualList(Course course) {
        NotificationEvent event = null;
        ConcessionStatusEnum concessionEnum = getConcessionStatus(course);
        boolean prereqs = meetsPrereqs(course);
        VirtualListEnum listEnum = getVirtualStatus(course);
        if (prereqs || concessionEnum == ConcessionStatusEnum.approved) {
            if (listEnum == VirtualListEnum.enrolled_list) {
                setEnrollmentStatusForCourse(course, EnrollmentStatusEnum.enrolled);
                event = new NotificationEvent(this, course, NotificationEventTypeEnum.moved_off_waiting_list);
            } else {
                setEnrollmentStatusForCourse(course, EnrollmentStatusEnum.waiting_list);
            }
        }
        return event;
    }


    public int getYearEnrolled(Course course) {
        LocalDate date = this.enrolledDates.get(course);
        date.getYear();
        return 0;
    }

    public void addCourseToEnrollmentCart(Course course){
        enrollmentCart.add(course);
    }

    public List<Course> getEnrollmentCart(){return enrollmentCart;};

    public void setDateEnrolled(Course course, LocalDate date) {
        this.enrolledDates.put(course, date);
    }
}
