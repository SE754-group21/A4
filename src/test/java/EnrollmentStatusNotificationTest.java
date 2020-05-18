import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
    public void testGetEnrollmentStatusNotificationConcessionApproved() {

        Mockito.when(student.getEnrollmentStatusForCourse(course)).thenReturn(EnrollmentStatusEnum.awaiting_concession);

        ConcessionApplication concessionApplication = new ConcessionApplication();
        Mockito.when(student.getConcessionApplication(course)).thenReturn(concessionApplication);

        NotificationEvent notificationEvent = enrollmentHandler.concessionGetsApproved(student, course);
        String notification = notificationEvent.notifyChange();

        Assert.assertEquals("Your concession for course SOFTENG 754 has been approved.", notification );

    }

    @Test
    public void testGetEnrollmentStatusNotificationConcessionDeclined() {

        Mockito.when(student.getEnrollmentStatusForCourse(course)).thenReturn(EnrollmentStatusEnum.awaiting_concession);

        ConcessionApplication concessionApplication = new ConcessionApplication();
        Mockito.when(student.getConcessionApplication(course)).thenReturn(concessionApplication);

        NotificationEvent notificationEvent = enrollmentHandler.concessionGetsDeclined(student, course);
        String notification = notificationEvent.notifyChange();

        Assert.assertEquals("Your concession for course SOFTENG 754 has been declined.", notification );

    }


}
