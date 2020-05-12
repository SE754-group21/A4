import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class testCourseEnrollmentStatus {

    @Test
    public void testGetEnrolledCourseEnrollmentStatus() {

        Course course = Mockito.mock(Course.class);
        Student student = Mockito.mock(Student.class);
        Mockito.when(student.getEnrollmentStatusForCourse(course)).thenReturn(EnrollmentStatusEnum.enrolled);

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE754";
        Mockito.when(course.getCid()).thenReturn(cid);

        EnrollmentHandler enrollmentHandler = new EnrollmentHandler();
        enrollmentHandler.addCourse(course);
        enrollmentHandler.addStudent(student);
        EnrollmentStatusEnum status = enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);

        Assert.assertEquals(EnrollmentStatusEnum.enrolled, status);
    }




}
