import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;

public class ConcessionApplicationTest {


    @Test
    public void testStudentPrereqsMetConcessionApplication() {
        String cid = "SE754";
        String sid = "12345";
        Database db = Mockito.mock(Database.class);

        Student student = Mockito.mock(Student.class);
        Course course = Mockito.mock(Course.class);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
        Mockito.when(student.getEnrollmentRequestStatusForCourse(course)).thenReturn(EnrollmentRequestStatusEnum.prerequisites_met);

        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        ConcessionApplicationHandler handler =  new ConcessionApplicationHandler(db);

        String concessionStatus = handler.submitApplication(sid, cid, concessionApp);

        assertEquals("Students that meet prerequisites are not required to apply for a concession", concessionStatus);
    }

    @Test
    public void testStudentAwaitingConcessionConcessionApplication() {
        String cid = "SE754";
        String sid = "12345";
        Database db = Mockito.mock(Database.class);

        Student student = Mockito.mock(Student.class);
        Course course = Mockito.mock(Course.class);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
        Mockito.when(student.getEnrollmentRequestStatusForCourse(course)).thenReturn(EnrollmentRequestStatusEnum.awaiting_concession);

        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        ConcessionApplicationHandler handler =  new ConcessionApplicationHandler(db);

        String concessionStatus = handler.submitApplication(sid, cid, concessionApp);

        assertEquals("Students may only submit one concession application for a course", concessionStatus);
    }

    @Test
    public void testStudentPrereqsNotMetConcessionApplication() {
        String cid = "SE754";
        String sid = "12345";
        ConcessionStatusEnum concessionStatus = ConcessionStatusEnum.pending;
        Database db = Mockito.mock(Database.class);

        Student student = Mockito.mock(Student.class);
        Course course = Mockito.mock(Course.class);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
        Mockito.when(student.getEnrollmentRequestStatusForCourse(course)).thenReturn(EnrollmentRequestStatusEnum.prerequisites_not_met);

        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        Mockito.when(concessionApp.setConcessionStatus(concessionStatus)).thenCallRealMethod();
        ConcessionApplicationHandler handler =  new ConcessionApplicationHandler(db);

        String concessionApplicationResult = handler.submitApplication(sid, cid, concessionApp);

        assertEquals("Concession application submitted", concessionApplicationResult);
    }

}
