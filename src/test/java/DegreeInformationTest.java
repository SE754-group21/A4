import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
public class DegreeInformationTest {

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
        List<String> courses = handler.getCompulsoryCourses(did);
        assertEquals(compulsoryCourses.get(0).getCid(), courses.get(0));
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetCompulsoryCoursesInvalidID() {
        String did = "SOFTENG";
        String cid = "ENGGEN303";
        List<Course> compulsoryCourses = new ArrayList<Course>();

        Course compulsoryCourse1 = Mockito.mock(Course.class);
        Mockito.when(compulsoryCourse1.getCid()).thenReturn(cid);
        compulsoryCourses.add(compulsoryCourse1);

        Degree degree = Mockito.mock(Degree.class);
        Mockito.when(degree.getDid()).thenReturn(did);
        Mockito.when(degree.getCompulsoryCourses()).thenReturn(compulsoryCourses);

        DegreeHandler handler = new DegreeHandler();
        List<String> courses = handler.getCompulsoryCourses(did);
    }

    @Test
    public void testGetElectiveCourses() {
        String did = "SOFTENG";
        String dname = "Software Engineering";
        String cid = "COMPSCI373";

        List<Course> electiveCourses = new ArrayList<Course>();
        Course electiveCourse1 = Mockito.mock(Course.class);
        Mockito.when(electiveCourse1.getCid()).thenReturn(cid);
        electiveCourses.add(electiveCourse1);

        DegreeHandler handler = new DegreeHandler();
        Degree degree = Mockito.mock(Degree.class);
        Mockito.when(degree.getDid()).thenReturn(did);
        Mockito.when(degree.getElectiveCourses()).thenReturn(electiveCourses);
        handler.addDegree(degree);
        List<Course> courses = handler.getElectiveCourses(did);
        assertEquals(electiveCourses, courses);
    }

    @Test (expected = NoSuchElementException.class)
    public void testGetElectiveCoursesInvalidID() {
        String did = "SOFTENG";
        String dname = "Software Engineering";
        String cid = "COMPSCI373";

        List<Course> electiveCourses = new ArrayList<Course>();
        Course electiveCourse1 = Mockito.mock(Course.class);
        Mockito.when(electiveCourse1.getCid()).thenReturn(cid);
        electiveCourses.add(electiveCourse1);

        DegreeHandler handler = new DegreeHandler();
        Degree degree = Mockito.mock(Degree.class);
        Mockito.when(degree.getDid()).thenReturn(did);
        Mockito.when(degree.getElectiveCourses()).thenReturn(electiveCourses);

        List<Course> courses = handler.getElectiveCourses(did);
    }


}
