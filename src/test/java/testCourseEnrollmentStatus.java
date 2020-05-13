import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.NoSuchElementException;

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


    @Test (expected = NoSuchElementException.class)
    public void testGetCourseEnrollmentStatusInvalidCourse() {

        Course course = Mockito.mock(Course.class);
        Student student = Mockito.mock(Student.class);
        Mockito.when(student.getEnrollmentStatusForCourse(course)).thenReturn(null);

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE867";
        Mockito.when(course.getCid()).thenReturn(cid);

        EnrollmentHandler enrollmentHandler = new EnrollmentHandler();
        enrollmentHandler.addStudent(student);
        EnrollmentStatusEnum status = enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);
    }

    @Test (expected = NoSuchElementException.class)
    public void testGetCourseEnrollmentStatusInvalidStudent() {

        Course course = Mockito.mock(Course.class);

        String sid = "oldStduent";
        String cid = "SE754";
        Mockito.when(course.getCid()).thenReturn(cid);

        EnrollmentHandler enrollmentHandler = new EnrollmentHandler();
        enrollmentHandler.addCourse(course);
        EnrollmentStatusEnum status = enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);
    }

    @Test
    public void testGetAwaitingCourseEnrollmentStatus() {
        Course course = Mockito.mock(Course.class);
        Student student = Mockito.mock(Student.class);
        Mockito.when(student.getEnrollmentStatusForCourse(course)).thenReturn(EnrollmentStatusEnum.waiting_list);

        Mockito.when(student.getWaitingListNumber(course)).thenReturn(62);

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE701";
        Mockito.when(course.getCid()).thenReturn(cid);

        EnrollmentHandler enrollmentHandler = new EnrollmentHandler();
        enrollmentHandler.addCourse(course);
        enrollmentHandler.addStudent(student);
        EnrollmentStatusEnum status = enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);
        int waitingListPosition = enrollmentHandler.getWaitingListPositionForStudent(sid, cid);

        Assert.assertEquals(EnrollmentStatusEnum.waiting_list, status);
        Assert.assertEquals(62, waitingListPosition);
    }

    @Test (expected = NoSuchElementException.class)
    public void testGetWaitingListPositionInvalidCourseEnrollmentStatus() {
        Course course = Mockito.mock(Course.class);
        Student student = Mockito.mock(Student.class);
        Mockito.when(student.getEnrollmentStatusForCourse(course)).thenReturn(EnrollmentStatusEnum.enrolled);

        Mockito.when(student.getWaitingListNumber(course)).thenReturn(-1);

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE701";
        Mockito.when(course.getCid()).thenReturn(cid);

        EnrollmentHandler enrollmentHandler = new EnrollmentHandler();
        enrollmentHandler.addCourse(course);
        enrollmentHandler.addStudent(student);
        EnrollmentStatusEnum status = enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);
        int waitingListPosition = enrollmentHandler.getWaitingListPositionForStudent(sid, cid);
    }

    @Test
    public void testGetConcessionCourseEnrollmentStatus() {
        Course course = Mockito.mock(Course.class);
        Student student = Mockito.mock(Student.class);

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE701";
        Mockito.when(course.getCid()).thenReturn(cid);

        Mockito.when(student.getEnrollmentStatusForCourse(course)).thenReturn(EnrollmentStatusEnum.awaiting_concession);

        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        Mockito.when(student.getConcessionApplication(course)).thenReturn(concessionApp);

        Mockito.when(concessionApp.isConcessionApproved(sid, cid)).thenReturn(false);
        Mockito.when(concessionApp.getConcessionReason(sid, cid)).thenReturn("Class is full");

        EnrollmentHandler enrollmentHandler = new EnrollmentHandler();
        enrollmentHandler.addCourse(course);
        enrollmentHandler.addStudent(student);
        EnrollmentStatusEnum status = enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);
        String concessionReason = enrollmentHandler.getConcessionReason(sid, cid);
        String concessionStatus = enrollmentHandler.getConcessionStatus(sid, cid);


        Assert.assertEquals(EnrollmentStatusEnum.awaiting_concession, status);
        Assert.assertEquals("Class is full", concessionReason);
        Assert.assertEquals("Pending - awaiting course approval ", concessionStatus);

    }





}
