import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.NoSuchElementException;

public class testCourseEnrollmentStatus {
    Course course;
    Student student;
    EnrollmentHandler enrollmentHandler;

    @Before
    public void setUp() {
        course = Mockito.mock(Course.class);
        student = Mockito.mock(Student.class);
        enrollmentHandler =  new EnrollmentHandler();
    }

    @Test
    public void testGetEnrolledCourseEnrollmentStatus() {

        Mockito.when(student.getEnrollmentStatusForCourse(course)).thenReturn(EnrollmentStatusEnum.enrolled);

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE754";
        Mockito.when(course.getCid()).thenReturn(cid);

        enrollmentHandler.addCourse(course);
        enrollmentHandler.addStudent(student);
        EnrollmentStatusEnum status = enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);

        Assert.assertEquals(EnrollmentStatusEnum.enrolled, status);
    }


    @Test (expected = NoSuchElementException.class)
    public void testGetCourseEnrollmentStatusInvalidCourse() {

        Mockito.when(student.getEnrollmentStatusForCourse(course)).thenReturn(null);

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE867";
        Mockito.when(course.getCid()).thenReturn(cid);

        enrollmentHandler.addStudent(student);
        enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);
    }

    @Test (expected = NoSuchElementException.class)
    public void testGetCourseEnrollmentStatusInvalidStudent() {

        String sid = "oldStduent";
        String cid = "SE754";
        Mockito.when(course.getCid()).thenReturn(cid);

        enrollmentHandler.addCourse(course);
        enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);
    }

    @Test
    public void testGetAwaitingCourseEnrollmentStatus() {
        Mockito.when(student.getEnrollmentStatusForCourse(course)).thenReturn(EnrollmentStatusEnum.waiting_list);

        Mockito.when(student.getWaitingListNumber(course)).thenReturn(62);

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE701";
        Mockito.when(course.getCid()).thenReturn(cid);

        enrollmentHandler.addCourse(course);
        enrollmentHandler.addStudent(student);
        EnrollmentStatusEnum status = enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);
        int waitingListPosition = enrollmentHandler.getWaitingListPositionForStudent(sid, cid);

        Assert.assertEquals(EnrollmentStatusEnum.waiting_list, status);
        Assert.assertEquals(62, waitingListPosition);
    }

    @Test (expected = NoSuchElementException.class)
    public void testGetWaitingListPositionInvalidCourseEnrollmentStatus() {
        Mockito.when(student.getEnrollmentStatusForCourse(course)).thenReturn(EnrollmentStatusEnum.enrolled);
        Mockito.when(student.getWaitingListNumber(course)).thenReturn(-1);

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE701";
        Mockito.when(course.getCid()).thenReturn(cid);

        enrollmentHandler.addCourse(course);
        enrollmentHandler.addStudent(student);
        enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);
        enrollmentHandler.getWaitingListPositionForStudent(sid, cid);
    }
}
