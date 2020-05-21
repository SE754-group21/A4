import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

@Category(UnitTests.class)
public class ConcessionApplicationStatusTest {
    private Course course;
    private Student student;
    private ConcessionApplicationHandler concessionHandler;
    private Database db;

    @Before
    public void setUp() {
        course = Mockito.mock(Course.class);
        student = Mockito.mock(Student.class);
        db = Mockito.mock(Database.class);
        concessionHandler  =  new ConcessionApplicationHandler(db);
    }

    @Test
    public void testGetAwaitingConcessionApplicationStatus() {

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE701";
        Mockito.when(course.getCid()).thenReturn(cid);

        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        Mockito.when(student.getConcessionApplication(course)).thenReturn(concessionApp);

        Mockito.when(concessionApp.getConcessionReason()).thenReturn("Class is full");
        Mockito.when(concessionApp.getConcessionStatus()).thenReturn(ConcessionStatusEnum.pending);

        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
        String concessionReason = concessionHandler.getConcessionReason(sid, cid);
        String concessionStatus = concessionHandler.getConcessionStatus(sid, cid);

        Assert.assertEquals("Class is full", concessionReason);
        Assert.assertEquals("Pending - awaiting course approval", concessionStatus);
    }

    @Test
    public void testGetDeniedConcessionApplicationStatusWithStatusReason() {

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE701";
        Mockito.when(course.getCid()).thenReturn(cid);

        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        Mockito.when(student.getConcessionApplication(course)).thenReturn(concessionApp);

        Mockito.when(concessionApp.getConcessionStatus()).thenReturn(ConcessionStatusEnum.denied);
        Mockito.when(concessionApp.getStatusReason()).thenReturn("Denied because the prerequisites do not match");

        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
        String statusReason = concessionHandler.getStatusReason(sid, cid);
        String concessionStatus = concessionHandler.getConcessionStatus(sid, cid);

        Assert.assertEquals("Denied - concession not approved", concessionStatus);
        Assert.assertEquals("Denied because the prerequisites do not match", statusReason);
    }

    @Test
    public void testGetApprovedConcessionApplicationStatus() {

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE701";
        Mockito.when(course.getCid()).thenReturn(cid);

        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        Mockito.when(student.getConcessionApplication(course)).thenReturn(concessionApp);

        Mockito.when(concessionApp.getConcessionStatus()).thenReturn(ConcessionStatusEnum.approved);
        Mockito.when(concessionApp.getStatusReason()).thenReturn("Approved because the faculty agrees this covers the prerequisite");

        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
        String statusReason = concessionHandler.getStatusReason(sid, cid);
        String concessionStatus = concessionHandler.getConcessionStatus(sid, cid);

        Assert.assertEquals("Approved - concession accepted and enrollment complete", concessionStatus);
        Assert.assertEquals("Approved because the faculty agrees this covers the prerequisite", statusReason);
    }

    @Test
    public void testInvalidConcessionStatus() {

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE701";
        Mockito.when(course.getCid()).thenReturn(cid);

        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        Mockito.when(student.getConcessionApplication(course)).thenReturn(concessionApp);

        Mockito.when(concessionApp.getConcessionStatus()).thenReturn(null);

        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
        String concessionStatus = concessionHandler.getConcessionStatus(sid, cid);

        Assert.assertEquals("The student has no concession for this course", concessionStatus);
    }

}
