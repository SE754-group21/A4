import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class EnrollmentStatusNotificationTest {
    private Course course;
    private Student student;
    private EnrollmentHandler enrollmentHandler;
    private Database db;

    @Before
    public void setUp() {
        course = Mockito.mock(Course.class);
        student = Mockito.mock(Student.class);
        db = Mockito.mock(Database.class);
        enrollmentHandler =  new EnrollmentHandler(db);
    }

    @Test
    public void testGetEnrollmentStatusNotification() {

        Mockito.when(enrollmentHandler.getEnrollmentStatusForCourse(student.getSid(), course.getCid())).thenReturn(EnrollmentStatusEnum.awaiting_concession);
        String cid = "SE754";
        String sid = "12345";
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(student.getSid()).thenReturn(sid);

        EnrollmentEvent enrollmentEvent = enrollmentHandler.concessionGetsApproved(student.getSid(), course.getCid());
        EnrollmentStatusEnum newStatus = enrollmentHandler.getEnrollmentStatusForCourse(student.getSid(), course.getCid());
        String notification = enrollmentHandler.notifyChange(student.getSid(), course.getCid(), enrollmentEvent);

        Assert.assertEquals("Your concession for course SOFTENG 754 has been approved.", notification );
        Assert.assertEquals(EnrollmentStatusEnum.enrolled, newStatus );

    }


}
