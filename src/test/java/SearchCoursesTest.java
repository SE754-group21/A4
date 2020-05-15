import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SearchCoursesTest {
    private Database db;
    @Before
    public void setUp() {
        db = Mockito.mock(Database.class);
    }

    @Test
    public void testSearchCourseName(){
        CourseHandler courseHandler = new CourseHandler(db);
        Course course = Mockito.mock(Course.class);
        String cid = "SOFTENG 701";
        String cName = "Requirements Engineering";
        Map<String, Course> courses = new HashMap<String, Course>();
        courses.put(cid, course);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getCname()).thenReturn(cName);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getAllCourses()).thenReturn(courses);

        List<Course> searchedCoursesCorrectSearch = courseHandler.search("requirements");
        List<Course> searchedCoursesIncorrectSearch = courseHandler.search("reqirments");
        assertEquals(0, searchedCoursesIncorrectSearch.size());
        assertEquals(1, searchedCoursesCorrectSearch.size());
    }
}
