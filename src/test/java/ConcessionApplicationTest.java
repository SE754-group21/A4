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

}
