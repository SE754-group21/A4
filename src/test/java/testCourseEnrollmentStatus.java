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

    @Test
    public void testGetAwaitingConcessionCourseEnrollmentStatus() {

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE701";
        Mockito.when(course.getCid()).thenReturn(cid);

        Mockito.when(student.getEnrollmentStatusForCourse(course)).thenReturn(EnrollmentStatusEnum.awaiting_concession);

        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        Mockito.when(student.getConcessionApplication(course)).thenReturn(concessionApp);

        Mockito.when(concessionApp.getConcessionReason()).thenReturn("Class is full");
        Mockito.when(concessionApp.getConcessionStatus()).thenReturn(ConcessionStatusEnum.pending);

        enrollmentHandler.addCourse(course);
        enrollmentHandler.addStudent(student);
        EnrollmentStatusEnum status = enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);
        String concessionReason = enrollmentHandler.getConcessionReason(sid, cid);
        String concessionStatus = enrollmentHandler.getConcessionStatus(sid, cid);

        Assert.assertEquals(EnrollmentStatusEnum.awaiting_concession, status);
        Assert.assertEquals("Class is full", concessionReason);
        Assert.assertEquals("Pending - awaiting course approval", concessionStatus);
    }

    @Test
    public void testGetConcessionDeniedCourseEnrollmentStatusWithStatusReason() {

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE701";
        Mockito.when(course.getCid()).thenReturn(cid);

        Mockito.when(student.getEnrollmentStatusForCourse(course)).thenReturn(EnrollmentStatusEnum.concession_denied);
        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        Mockito.when(student.getConcessionApplication(course)).thenReturn(concessionApp);

        Mockito.when(concessionApp.getConcessionStatus()).thenReturn(ConcessionStatusEnum.denied);
        Mockito.when(concessionApp.getStatusReason()).thenReturn("Denied because the prerequisites do not match");

        enrollmentHandler.addCourse(course);
        enrollmentHandler.addStudent(student);
        EnrollmentStatusEnum status = enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);
        String statusReason = enrollmentHandler.getStatusReason(sid, cid);
        String concessionStatus = enrollmentHandler.getConcessionStatus(sid, cid);

        Assert.assertEquals("Denied - concession not approved", concessionStatus);
        Assert.assertEquals(EnrollmentStatusEnum.concession_denied, status);
        Assert.assertEquals("Denied because the prerequisites do not match", statusReason);
    }
}
