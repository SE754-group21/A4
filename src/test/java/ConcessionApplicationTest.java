import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@Category(UnitTests.class)
public class ConcessionApplicationTest {

    String cid;
    String sid;
    Database db;
    Student student;
    Course course;

    @Before
    public void setUp() {
        cid = "SE754";
        sid = "12345";
        db = Mockito.mock(Database.class);
        student = Mockito.mock(Student.class);
        course = Mockito.mock(Course.class);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
    }

    @Test
    public void testStudentNotEnrolledConcessionApplication() {
        Mockito.when(student.getEnrollmentStatusForCourse(course)).thenReturn(EnrollmentStatusEnum.not_enrolled);

        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        ConcessionApplicationHandler handler =  new ConcessionApplicationHandler(db);

        String concessionStatus = handler.submitApplication(sid, cid, concessionApp);

        assertEquals("Student must enroll to apply for a concession", concessionStatus);
    }

    @Test
    public void testStudentPrereqsMetConcessionApplication() {
        Mockito.when(student.getEnrollmentRequestStatusForCourse(course)).thenReturn(EnrollmentRequestStatusEnum.prerequisites_met);
        ConcessionApplicationHandler handler =  new ConcessionApplicationHandler(db);
        String concessionStatus = handler.submitApplication(sid, cid);
        assertEquals("Students that meet prerequisites are not required to apply for a concession", concessionStatus);
    }

    @Test
    public void testStudentAwaitingConcessionConcessionApplication() {
        Mockito.when(student.getEnrollmentRequestStatusForCourse(course)).thenReturn(EnrollmentRequestStatusEnum.awaiting_concession);
        ConcessionApplicationHandler handler =  new ConcessionApplicationHandler(db);
        String concessionStatus = handler.submitApplication(sid, cid);
        assertEquals("Students may only submit one concession application for a course", concessionStatus);
    }

    @Test
    public void testStudentPrereqsNotMetConcessionApplication() {
        Mockito.when(student.getEnrollmentRequestStatusForCourse(course)).thenReturn(EnrollmentRequestStatusEnum.prerequisites_not_met);
        ConcessionApplicationHandler handler =  new ConcessionApplicationHandler(db);
        String concessionApplicationResult = handler.submitApplication(sid, cid);
        assertEquals("Concession application submitted", concessionApplicationResult);
    }

    @Test
    public void testAcceptConcession() {
        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        ConcessionApplicationHandler handler =  new ConcessionApplicationHandler(db);
        handler.approveConcession(concessionApp);
        verify(concessionApp, Mockito.times(1)).setConcessionStatus(ConcessionStatusEnum.approved);
    }

    @Test
    public void testDeclineConcession() {
        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        ConcessionApplicationHandler handler =  new ConcessionApplicationHandler(db);
        handler.declineConcession(concessionApp);
        verify(concessionApp, Mockito.times(1)).setConcessionStatus(ConcessionStatusEnum.denied);
    }



}
