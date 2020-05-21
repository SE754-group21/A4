import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@Category(UnitTests.class)
public class CourseInformationTest {
    private Database db;
    private CourseHandler handler;
    private Course course;
    private String cid;

    @Before
    public void setUp() {
        cid = "SOFTENG754";
        db = Mockito.mock(Database.class);
        handler = new CourseHandler(db);
        course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
    }

    @Test
    public void testGetCourseName() {
        String cname = "Requirements";
        Mockito.when(course.getCname()).thenReturn(cname);
        String name = handler.getCname(cid);
        assertEquals(name, cname);
    }

    @Test
    public void testGetCourseDescription() {
        String cdesc = "Description of course";
        Mockito.when(course.getCdesc()).thenReturn(cdesc);
        String name = handler.getCdesc(cid);
        assertEquals(name, cdesc);
    }

    @Test
    public void testGetStaff() {
        String sid = "KEJ372";
        List<Staff> staffList = new ArrayList<Staff>();
        Staff staff = Mockito.mock(Staff.class);
        Mockito.when(staff.getSid()).thenReturn(sid);
        staffList.add(staff);
        Mockito.when(course.getStaff()).thenReturn(staffList);
        List<String> staffnames = handler.getStaff(cid);
        assertEquals(staffnames.get(0), sid);
    }

    @Test
    public void testGetCourseHours() {
        List<String> hours = new ArrayList<>();
        Mockito.when(course.getCHours()).thenReturn(hours);
        List<String> h = handler.getCHours(cid);
        assertEquals(h.size(), 0);
    }

    @Test
    public void testGetTotalSets() {
        int total = 1000;
        Mockito.when(course.getTotalSeats()).thenReturn(total);
        int name = handler.getTotalSeats(cid);
        assertEquals(name, total);
    }

    @Test
    public void testGetSeatsRemaining() {
        int total = 1000;
        Mockito.when(course.getRemainingSeats()).thenReturn(total);
        int name = handler.getRemainingSeats(cid);
        assertEquals(name, total);
    }

    @Test
    public void testGetCoursePoints() {
        int points = 15;
        Mockito.when(course.getCoursePoints()).thenReturn(points);
        int returnedPoints = handler.getCoursePoints(cid);
        assertEquals(points, returnedPoints);
    }

    @Test
    public void testGetPrerequisites() {
        String sid = "KEJ372";
        List<Course> prereqlist = new ArrayList<Course>();
        Course prereq = Mockito.mock(Course.class);
        Mockito.when(prereq.getCid()).thenReturn(sid);
        prereqlist.add(prereq);
        Mockito.when(course.getPrerequisites()).thenReturn(prereqlist);
        List<String> cids = handler.getPrerequisites(cid);
        assertEquals(cids.get(0), sid);
    }

    @Test
    public void testAddCourseSeats() {
        int capacity = 1000;
        boolean success = handler.setCapacity(cid, capacity);
        assertTrue(success);
    }

    @Test
    public void testAddCourseSeatsNegative() {
        int capacity = -1;
        boolean success = handler.setCapacity(cid, capacity);
        assertFalse(success);
    }

    @Test
    public void testAddCourseSeatsPast1000() {
        int capacity = 1001;
        boolean success = handler.setCapacity(cid, capacity);
        assertFalse(success);
    }

    @Test
    public void testSetCoursePrerequisites() {
        String prereqid = "KEJ372";
        Course prereq = Mockito.mock(Course.class);
        Mockito.when(db.getCourse(prereqid)).thenReturn(prereq);
        List<Course> cprereq = new ArrayList<Course>();
        cprereq.add(prereq);
        List<String> prereqlist = new ArrayList<>();
        prereqlist.add(prereqid);
        handler.setPrerequisites(cid, prereqlist);
        verify(course).setPrerequisites(cprereq);
    }

    @Test (expected = NoSuchElementException.class)
    public void testSetCoursePrerequisitesInvalidPrereq() {
        String prereqid = "KEJ372";
        List<String> prereqlist = new ArrayList<>();
        prereqlist.add(prereqid);
        handler.setPrerequisites(cid, prereqlist);
    }

}