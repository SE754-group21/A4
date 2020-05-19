import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends User {

    private String sid;
    private List<Course> courses;
    private Map<Course, ConcessionApplication> applications;
    private Map<Course, VirtualListEnum> queues = new HashMap<>();
    private Map<Course, EnrollmentStatusEnum> enrollment = new HashMap<>();

    public Student() {
        applications = new HashMap<>();
        courses = new ArrayList<>();
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

    public void addEnrolledCourse(Course course) {}

    public EnrollmentRequestStatusEnum getEnrollmentRequestStatusForCourse(Course course){
        return null;
    }

    public EnrollmentStatusEnum getEnrollmentStatusForCourse(Course course) {
        return null;
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

    public void removeCourse(Course course) {

    }

    public void setEnrollmentRequestStatusForCourse(Course course, EnrollmentRequestStatusEnum enrollmentRequestStatus){

    }

    public void setEnrollmentStatusForCourse(Course course, EnrollmentStatusEnum status) {
        enrollment.put(course, status);
    }

    public void setVirtualList(Course course, VirtualListEnum status) {
        queues.put(course, status);
    }

    public ConcessionStatusEnum getConcessionStatus(Course course) {
        return applications.get(course).getConcessionStatus();
    }

    public VirtualListEnum getVirtualStatus(Course course) {
        return queues.get(course);
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

    public void updateVirtualList(Course course) {
        ConcessionStatusEnum concessionEnum = getConcessionStatus(course);
        VirtualListEnum listEnum = getVirtualStatus(course);
    }

}
