import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class EnrollmentStatusNotificationTest {
    String cid;
    String sid;
    Database db;
    Student student;
    Course course;
    EnrollmentHandler enrollmentHandler;

    @Before
    public void setUp() {
        cid = "SE754";
        sid = "12345";
        db = Mockito.mock(Database.class);

        student = Mockito.mock(Student.class);
        course = Mockito.mock(Course.class);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
        enrollmentHandler =  new EnrollmentHandler(db);
    }
    @Test
    public void testGetEnrollmentStatusNotification() {

        EnrollmentEvent enrollmentEvent = enrollmentHandler.concessionGetsApproved(student.getSid(), course.getCid());
        EnrollmentStatusEnum newStatus = enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);
        String notification = enrollmentHandler.notifyChange(student.getSid(), course.getCid(), enrollmentEvent);

        Assert.assertEquals(EnrollmentStatusEnum.enrolled, newStatus );

        Assert.assertEquals("Your concession for course SOFTENG 754 has been approved.", notification );

    }


}
