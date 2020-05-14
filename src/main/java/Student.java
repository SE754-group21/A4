import java.util.List;

public class Student extends User {

    public Student() {

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
