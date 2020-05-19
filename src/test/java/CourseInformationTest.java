import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Category(UnitTests.class)
public class CourseInformationTest {
    private Database db;
    @Before
    public void setUp() {
        db = Mockito.mock(Database.class);
    }

    @Test
    public void testGetCourseName() {
        String cid = "SOFTENG754";
        String cname = "Requirements";
        CourseHandler handler = new CourseHandler(db);
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getCname()).thenReturn(cname);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        String name = handler.getCname(cid);
        assertEquals(name, cname);
    }

    @Test
    public void testGetCourseDescription() {
        String cid = "SOFTENG754";
        String cdesc = "Description of course";
        CourseHandler handler = new CourseHandler(db);
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getCdesc()).thenReturn(cdesc);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        String name = handler.getCdesc(cid);
        assertEquals(name, cdesc);
    }

    @Test
    public void testGetStaff() {
        String cid = "SOFTENG754";
        String sid = "KEJ372";
        List<Staff> staffList = new ArrayList<Staff>();
        Staff staff = Mockito.mock(Staff.class);
        Mockito.when(staff.getSid()).thenReturn(sid);
        staffList.add(staff);

        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getStaff()).thenReturn(staffList);

        CourseHandler handler = new CourseHandler(db);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        List<String> staffnames = handler.getStaff(cid);
        assertEquals(staffnames.get(0), sid);
    }

    @Test
    public void testGetCourseHours() {
        String cid = "SOFTENG754";
        List<String> hours = new ArrayList<>();
        CourseHandler handler = new CourseHandler(db);
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getCHours()).thenReturn(hours);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        List<String> h = handler.getCHours(cid);
        assertEquals(h.size(), 0);
    }

    @Test
    public void testGetTotalSets() {
        String cid = "SOFTENG754";
        int total = 1000;
        CourseHandler handler = new CourseHandler(db);
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getTotalSeats()).thenReturn(total);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        int name = handler.getTotalSeats(cid);
        assertEquals(name, total);
    }

    @Test
    public void testGetSeatsRemaining() {
        String cid = "SOFTENG754";
        int total = 1000;
        CourseHandler handler = new CourseHandler(db);
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getRemainingSeats()).thenReturn(total);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        int name = handler.getRemainingSeats(cid);
        assertEquals(name, total);
    }

    @Test
    public void testGetPrerequisites() {
        String cid = "SOFTENG754";
        String sid = "KEJ372";
        List<Course> prereqlist = new ArrayList<Course>();
        Course prereq = Mockito.mock(Course.class);
        Mockito.when(prereq.getCid()).thenReturn(sid);
        prereqlist.add(prereq);

        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getPrerequisites()).thenReturn(prereqlist);

        CourseHandler handler = new CourseHandler(db);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        List<String> cids = handler.getPrerequisites(cid);
        assertEquals(cids.get(0), sid);
    }

    @Test
    public void testAddCourseSeats() {
        String cid = "SOFTENG754";
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.setCapacity()).thenReturn(true);
        Mockito.when(course.getCid()).thenReturn(cid);
        CourseHandler handler = new CourseHandler(db);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        boolean success = handler.setCapacity(cid, 1000);
        assertTrue(success);
    }

}