import java.util.List;

public class Student {

    public Student() {

    }

    public String getSid() {
        return "";
    }

    public void addEnrolledCourse(Course course) {}

    public EnrollmentStatusEnum getEnrollmentStatusForCourse(Course course) {
        return null;
    }

    public List<Course> getTakenCourses() {
        return null;
    }

    public int getWaitingListNumber(Course course) {
        return 0;
    }

    public ConcessionApplication getConcessionApplication(Course course) {
        return null;
    }
}
