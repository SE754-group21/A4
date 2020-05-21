import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

@Category(UnitTests.class)
public class DegreeInformationTest {
    private Database db;
    private DegreeHandler handler;
    private Degree degree;
    private String did;
    private String dname;

    @Before
    public void setUp() {
        did = "SOFTENG";
        db = Mockito.mock(Database.class);
        handler = new DegreeHandler(db);
        degree = Mockito.mock(Degree.class);
        Mockito.when(degree.getDid()).thenReturn(did);
        Mockito.when(degree.getDname()).thenReturn(dname);
        Mockito.when(db.getDegree(did)).thenReturn(degree);
    }


    @Test
    public void testGetDegreeName() {
        String dname = "Software Engineering";
        Mockito.when(degree.getDname()).thenReturn(dname);
        String name = handler.getDname(did);
        assertEquals(dname, name);
    }


    @Test
    public void testGetCompulsoryCourses() {
        String cid = "ENGGEN303";

        List<Course> compulsoryCourses = new ArrayList<Course>();
        Course compulsoryCourse1 = Mockito.mock(Course.class);
        Mockito.when(compulsoryCourse1.getCid()).thenReturn(cid);
        compulsoryCourses.add(compulsoryCourse1);

        DegreeHandler handler = new DegreeHandler(db);

        Mockito.when(degree.getCompulsoryCourses()).thenReturn(compulsoryCourses);
        List<String> courses = handler.getCompulsoryCourses(did);
        assertEquals(compulsoryCourses.get(0).getCid(), courses.get(0));
    }


    @Test
    public void testGetElectiveCourses() {
        String cid = "COMPSCI373";

        List<Course> electiveCourses = new ArrayList<Course>();
        Course electiveCourse1 = Mockito.mock(Course.class);
        Mockito.when(electiveCourse1.getCid()).thenReturn(cid);
        electiveCourses.add(electiveCourse1);

        DegreeHandler handler = new DegreeHandler(db);

        Mockito.when(degree.getElectiveCourses()).thenReturn(electiveCourses);
        List<String> courses = handler.getElectiveCourses(did);
        assertEquals(electiveCourses.get(0).getCid(), courses.get(0));
    }

}
