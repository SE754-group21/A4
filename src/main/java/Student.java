import java.util.List;

public class Student extends User {

    String sid;
    List<Course> courses;

    public Student() {

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
        return null;
    }

    public void removeCourse(Course course) {

    }

    public void setEnrollmentRequestStatusForCourse(Course course, EnrollmentRequestStatusEnum enrollmentRequestStatus){

    }

    public void setEnrollmentStatusForCourse(Course course, EnrollmentStatusEnum status) {

    }
}
