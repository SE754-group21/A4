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

    @Test
    public void testSearchCourseDescription(){
        CourseHandler courseHandler = new CourseHandler(db);
        Course course = Mockito.mock(Course.class);
        String cid = "SOFTENG 701";
        String cDesc = "A paper about Requirements Engineering";
        Map<String, Course> courses = new HashMap<String, Course>();
        courses.put(cid, course);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getCdesc()).thenReturn(cDesc);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getAllCourses()).thenReturn(courses);

        List<Course> searchedCoursesCorrectSearch = courseHandler.search("engineering");
        List<Course> searchedCoursesIncorrectSearch = courseHandler.search("enginering");
        assertEquals(0, searchedCoursesIncorrectSearch.size());
        assertEquals(1, searchedCoursesCorrectSearch.size());
    }

    @Test
    public void testSearchCourseDegree(){
        CourseHandler courseHandler = new CourseHandler(db);
        Course course = Mockito.mock(Course.class);
        String cid = "SOFTENG 701";
        String cDept = "Software Engineering";
        Map<String, Course> courses = new HashMap<String, Course>();
        courses.put(cid, course);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getCdept()).thenReturn(cDept);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getAllCourses()).thenReturn(courses);

        List<Course> searchedCoursesCorrectSearch = courseHandler.search("software engineering");
        List<Course> searchedCoursesIncorrectSearch = courseHandler.search("softwar enginering");
        assertEquals(0, searchedCoursesIncorrectSearch.size());
        assertEquals(1, searchedCoursesCorrectSearch.size());
    }

    @Test
    public void testSearchCourseStaffFirstName(){
        CourseHandler courseHandler = new CourseHandler(db);
        Course course = Mockito.mock(Course.class);
        Staff staff = Mockito.mock(Staff.class);
        String cid = "SOFTENG 701";
        String firstName = "Kelly";
        List<Staff> staffList = new ArrayList<>();
        staffList.add(staff);
        Map<String, Course> courses = new HashMap<String, Course>();
        courses.put(cid, course);

        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getStaff()).thenReturn(staffList);
        Mockito.when(staff.getFirst()).thenReturn(firstName);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getAllCourses()).thenReturn(courses);

        List<Course> searchedCoursesCorrectSearch = courseHandler.search("Kelly");
        List<Course> searchedCoursesIncorrectSearch = courseHandler.search("klly");

        assertEquals(0, searchedCoursesIncorrectSearch.size());
        assertEquals(1, searchedCoursesCorrectSearch.size());
    }
}
