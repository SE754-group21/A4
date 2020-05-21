import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

    @Test (expected = IllegalStateException.class)
    public void testStudentPrereqsMetConcessionApplication() {
        Mockito.when(student.getEnrollmentRequestStatusForCourse(course)).
                thenReturn(EnrollmentRequestStatusEnum.prerequisites_met);
        ConcessionApplicationHandler handler =
                new ConcessionApplicationHandler(db);
        handler.submitApplication(sid, cid);
    }

    @Test (expected = IllegalStateException.class)
    public void testStudentAwaitingConcessionConcessionApplication() {
        Mockito.when(student.getEnrollmentRequestStatusForCourse(course)).
                thenReturn(EnrollmentRequestStatusEnum.awaiting_concession);
        ConcessionApplicationHandler handler =
                new ConcessionApplicationHandler(db);
        handler.submitApplication(sid, cid);
    }

    @Test
    public void testStudentPrereqsNotMetConcessionApplication() {
        String concessionID = "12345";
        Mockito.when(db.addConcessionApplication(Mockito.any())).thenReturn(concessionID);
        Mockito.when(student.getEnrollmentRequestStatusForCourse(course)).
                thenReturn(EnrollmentRequestStatusEnum.prerequisites_not_met);
        ConcessionApplicationHandler handler =
                new ConcessionApplicationHandler(db);
        String result = handler.submitApplication(sid, cid);
        assertEquals(concessionID, result);
    }

    @Test
    public void testAcceptConcession() {
        String cid = "24";
        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        ConcessionApplicationHandler handler =  new ConcessionApplicationHandler(db);
        Mockito.when(db.getConcessionApplication(cid)).thenReturn(concessionApp);
        Mockito.when(concessionApp.getStudent()).thenReturn(student);
        Mockito.when(concessionApp.getCourse()).thenReturn(course);
        handler.approveConcession(cid);
        verify(concessionApp, Mockito.times(1)).setConcessionStatus(ConcessionStatusEnum.approved);
    }

    @Test
    public void testDeclineConcession() {

        String cid = "24";
        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        ConcessionApplicationHandler handler =  new ConcessionApplicationHandler(db);
        Mockito.when(db.getConcessionApplication(cid)).thenReturn(concessionApp);
        Mockito.when(concessionApp.getStudent()).thenReturn(student);
        Mockito.when(concessionApp.getCourse()).thenReturn(course);
        handler.declineConcession(cid, "Don't meet prerequsities");
        verify(concessionApp,
                Mockito.times(1)).
                setConcessionStatus(ConcessionStatusEnum.denied);
    }

    @Test
    public void addConcessionStudent() {
        ConcessionApplication concessionApp =
                Mockito.mock(ConcessionApplication.class);
        Student student = new Student();
        student.addConcession(course, concessionApp);
        ConcessionApplication app =
                student.getConcessionApplication(course);
        assertEquals(concessionApp, app);
    }

    @Test
    public void getConcessionFromID() {
        String cid = "24";
        ConcessionApplication app = Mockito.mock(ConcessionApplication.class);
        Mockito.when(db.getConcessionApplication(cid)).thenReturn(app);
        ConcessionApplicationHandler handler = new ConcessionApplicationHandler(db);
        assertEquals(app, handler.getConcession(cid));
    }

    @Test (expected = NoSuchElementException.class)
    public void getConcessionFromIDInvalidID() {
        String cid = "24";
        Mockito.when(db.getConcessionApplication(cid)).thenReturn(null);
        ConcessionApplicationHandler handler = new ConcessionApplicationHandler(db);
        handler.getConcession(cid);
    }

}
