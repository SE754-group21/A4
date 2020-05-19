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
    public void testStudentPrereqsMetConcessionApplication() {
        Mockito.when(student.getEnrollmentRequestStatusForCourse(course)).thenReturn(EnrollmentRequestStatusEnum.prerequisites_met);

        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        ConcessionApplicationHandler handler =  new ConcessionApplicationHandler(db);

        String concessionStatus = handler.submitApplication(sid, cid, concessionApp);

        assertEquals("Students that meet prerequisites are not required to apply for a concession", concessionStatus);
    }

    @Test
    public void testStudentAwaitingConcessionConcessionApplication() {
        Mockito.when(student.getEnrollmentRequestStatusForCourse(course)).thenReturn(EnrollmentRequestStatusEnum.awaiting_concession);

        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        ConcessionApplicationHandler handler =  new ConcessionApplicationHandler(db);

        String concessionStatus = handler.submitApplication(sid, cid, concessionApp);

        assertEquals("Students may only submit one concession application for a course", concessionStatus);
    }

    @Test
    public void testStudentPrereqsNotMetConcessionApplication() {
        ConcessionStatusEnum concessionStatus = ConcessionStatusEnum.pending;
        Mockito.when(student.getEnrollmentRequestStatusForCourse(course)).thenReturn(EnrollmentRequestStatusEnum.prerequisites_not_met);

        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        Mockito.when(concessionApp.setConcessionStatus(concessionStatus)).thenCallRealMethod();
        ConcessionApplicationHandler handler =  new ConcessionApplicationHandler(db);

        String concessionApplicationResult = handler.submitApplication(sid, cid, concessionApp);

        assertEquals("Concession application submitted", concessionApplicationResult);
    }

    @Test
    public void testAcceptConcession() {
        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        ConcessionApplicationHandler handler =  new ConcessionApplicationHandler(db);
        handler.approveConcession(concessionApp);
        verify(concessionApp, Mockito.times(1)).setConcessionStatus(ConcessionStatusEnum.approved);
    }

}
