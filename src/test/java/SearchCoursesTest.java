import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@Category(UnitTests.class)
public class SearchCoursesTest {
    private Database db;
    private Course course;
    private CourseHandler courseHandler;
    private String cid = "SOFTENG 701";
    private Map<String, Course> courses;

    @Before
    public void setUp() {
        db = Mockito.mock(Database.class);
        course = Mockito.mock(Course.class);
        courseHandler = new CourseHandler(db);
        courses = new HashMap<String, Course>();
        courses.put(cid, course);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getAllCourses()).thenReturn(courses);
    }

    @Test
    public void testSearchCourseName(){
        String cName = "Requirements Engineering";
        Mockito.when(course.getCname()).thenReturn(cName);
        List<Course> searchedCoursesCorrectSearch = courseHandler.search("requirements");
        List<Course> searchedCoursesIncorrectSearch = courseHandler.search("reqirments");
        assertEquals(0, searchedCoursesIncorrectSearch.size());
        assertEquals(1, searchedCoursesCorrectSearch.size());
    }

    @Test
    public void testSearchCourseDescription(){
        String cDesc = "A paper about Requirements Engineering";
        Mockito.when(course.getCdesc()).thenReturn(cDesc);
        List<Course> searchedCoursesCorrectSearch = courseHandler.search("engineering");
        List<Course> searchedCoursesIncorrectSearch = courseHandler.search("enginering");
        assertEquals(0, searchedCoursesIncorrectSearch.size());
        assertEquals(1, searchedCoursesCorrectSearch.size());
    }

    @Test
    public void testSearchCourseDegree(){
        String cDept = "Software Engineering";
        Mockito.when(course.getCdept()).thenReturn(cDept);
        List<Course> searchedCoursesCorrectSearch = courseHandler.search("software engineering");
        List<Course> searchedCoursesIncorrectSearch = courseHandler.search("softwar enginering");
        assertEquals(0, searchedCoursesIncorrectSearch.size());
        assertEquals(1, searchedCoursesCorrectSearch.size());
    }

    @Test
    public void testSearchCourseStaffFirstName(){
        Staff staff = Mockito.mock(Staff.class);
        String firstName = "Kelly";
        List<Staff> staffList = new ArrayList<>();
        staffList.add(staff);
        Mockito.when(course.getStaff()).thenReturn(staffList);
        Mockito.when(staff.getFirst()).thenReturn(firstName);

        List<Course> searchedCoursesCorrectSearch = courseHandler.search("Kelly");
        List<Course> searchedCoursesIncorrectSearch = courseHandler.search("klly");

        assertEquals(0, searchedCoursesIncorrectSearch.size());
        assertEquals(1, searchedCoursesCorrectSearch.size());
    }

    @Test
    public void testSearchCourseStaffLastName(){
        Staff staff = Mockito.mock(Staff.class);
        String lastName = "Blincoe";
        List<Staff> staffList = new ArrayList<>();
        staffList.add(staff);
        Mockito.when(course.getStaff()).thenReturn(staffList);
        Mockito.when(staff.getLast()).thenReturn(lastName);

        List<Course> searchedCoursesCorrectSearch = courseHandler.search("BLincoe");
        List<Course> searchedCoursesIncorrectSearch = courseHandler.search("Blince");

        assertEquals(0, searchedCoursesIncorrectSearch.size());
        assertEquals(1, searchedCoursesCorrectSearch.size());
    }

    @Test
    public void testSearchCourseStaffFullName(){
        Staff staff = Mockito.mock(Staff.class);
        String firstName = "Kelly";
        String lastName = "Blincoe";
        List<Staff> staffList = new ArrayList<>();
        staffList.add(staff);
        Mockito.when(course.getStaff()).thenReturn(staffList);
        Mockito.when(staff.getFirst()).thenReturn(firstName);
        Mockito.when(staff.getLast()).thenReturn(lastName);

        List<Course> searchedCoursesCorrectSearch = courseHandler.search("Kelly Blincoe");
        List<Course> searchedCoursesIncorrectSearch = courseHandler.search("Kelly Blince");

        assertEquals(0, searchedCoursesIncorrectSearch.size());
        assertEquals(1, searchedCoursesCorrectSearch.size());
    }

    @Test
    public void testAddCourseToEnrolmentCart(){
        EnrollmentHandler enrollmentHandler = new EnrollmentHandler(db);
        Student student = Mockito.mock(Student.class);
        String sid = "12345";
        Mockito.when(db.getStudent(sid)).thenReturn(student);
        enrollmentHandler.addCourseToEnrollmentCart(sid, cid);
        verify(student).addCourseToEnrollmentCart(course);
    }

    @Test
    public void testCourseAddedToEnrolmentCart(){
        Student student = new Student();
        student.addCourseToEnrollmentCart(course);
        List<Course> courses = student.getEnrollmentCart();
        assertEquals(1, courses.size());
    }
}
