import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
public class testDegreeInformation {

    @Test
    public void testGetDegreeName() {
        String did = "SOFTENG";
        String dname = "Software Engineering";
        DegreeHandler handler = new DegreeHandler();
        Degree degree = Mockito.mock(Degree.class);
        Mockito.when(degree.getDid()).thenReturn(did);
        Mockito.when(degree.getDname()).thenReturn(dname);
        handler.addDegree(degree);
        String name = handler.getDname(did);
        assertEquals(dname, name);
    }


    @Test(expected = NoSuchElementException.class)
    public void testGetDegreeNameInvalidID() {
        String did = "SOFTENG";
        String dname = "Software Engineering";
        DegreeHandler handler = new DegreeHandler();
        Degree degree = Mockito.mock(Degree.class);
        Mockito.when(degree.getDid()).thenReturn(did);
        Mockito.when(degree.getDname()).thenReturn(dname);
        String name = handler.getDname(did);
    }

    @Test
    public void testGetCompulsoryCourses() {
        String did = "SOFTENG";
        String dname = "Software Engineering";
        String cid = "ENGGEN303";

        List<Course> compulsoryCourses = new ArrayList<Course>();
        Course compulsoryCourse1 = Mockito.mock(Course.class);
        Mockito.when(compulsoryCourse1.getCid()).thenReturn(cid);
        compulsoryCourses.add(compulsoryCourse1);

        DegreeHandler handler = new DegreeHandler();
        Degree degree = Mockito.mock(Degree.class);
        Mockito.when(degree.getDid()).thenReturn(did);
        Mockito.when(degree.getCompulsoryCourses()).thenReturn(compulsoryCourses);
        handler.addDegree(degree);
        List<Course> courses = handler.getCompulsoryCourses(did);
        assertEquals(compulsoryCourses, courses);
    }

}
