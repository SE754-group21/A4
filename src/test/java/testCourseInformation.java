import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

public class testCourseInformation {
    @Test
    public void testGetCourseName() {
        String cid = "SOFTENG754";
        String cname = "Requirements";
        CourseHandler handler = new CourseHandler();
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getCname()).thenReturn(cname);
        handler.addCourse(course);
        String name = handler.getCname(cid);
        assertEquals(name, cname);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetCourseNameInvalidID() {
        String cid = "SOFTENG754";
        String cname = "Requirements";
        CourseHandler handler = new CourseHandler();
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getCname()).thenReturn(cname);
        String name = handler.getCname(cid);
    }

    @Test
    public void testGetCourseDescription() {
        String cid = "SOFTENG754";
        String cdesc = "Description of course";
        CourseHandler handler = new CourseHandler();
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getCdesc()).thenReturn(cdesc);
        handler.addCourse(course);
        String name = handler.getCdesc(cid);
        assertEquals(name, cdesc);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetCourseDescriptionInvalidID() {
        String cid = "SOFTENG754";
        String cdesc = "Description of course";
        CourseHandler handler = new CourseHandler();
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getCdesc()).thenReturn(cdesc);
        handler.getCdesc(cid);
    }

    @Test
    public void testGetStaff() {
        String cid = "SOFTENG754";
        String sid = "KEJ372";
        List<Staff> staffList = new ArrayList<Staff>();
        Staff staff = Mockito.mock(Staff.class);
        Mockito.when(staff.getID()).thenReturn(sid);
        staffList.add(staff);

        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getStaff()).thenReturn(staffList);

        CourseHandler handler = new CourseHandler();
        handler.addCourse(course);
        List<String> staffnames = handler.getStaff(cid);
        assertEquals(staffnames.get(0), sid);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetStaffInvalidID() {
        String cid = "SOFTENG754";
        CourseHandler handler = new CourseHandler();
        handler.getStaff(cid);
    }

    @Test
    public void testGetCourseHours() {
        String cid = "SOFTENG754";
        List<String> hours = new ArrayList<>();
        CourseHandler handler = new CourseHandler();
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getCHours()).thenReturn(hours);
        handler.addCourse(course);
        List<String> h = handler.getCHours(cid);
        assertEquals(h.size(), 0);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetCourseHoursInvalidID() {
        String cid = "SOFTENG754";
        CourseHandler handler = new CourseHandler();
        handler.getCHours(cid);
    }

    @Test
    public void testGetTotalSets() {
        String cid = "SOFTENG754";
        int total = 1000;
        CourseHandler handler = new CourseHandler();
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getTotalSeats()).thenReturn(total);
        handler.addCourse(course);
        int name = handler.getTotalSeats(cid);
        assertEquals(name, total);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetTotalSetsInvalidID() {
        String cid = "SOFTENG754";
        CourseHandler handler = new CourseHandler();
        handler.getTotalSeats(cid);
    }

    @Test
    public void testGetSeatsRemaining() {
        String cid = "SOFTENG754";
        int total = 1000;
        CourseHandler handler = new CourseHandler();
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getRemainingSeats()).thenReturn(total);
        handler.addCourse(course);
        int name = handler.getRemainingSeats(cid);
        assertEquals(name, total);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetSeatsRemainingInvalidID() {
        String cid = "SOFTENG754";
        CourseHandler handler = new CourseHandler();
        handler.getRemainingSeats(cid);
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

        CourseHandler handler = new CourseHandler();
        handler.addCourse(course);
        List<String> cids = handler.getPrerequisites(cid);
        assertEquals(cids.get(0), sid);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetPrerequisitesInvalidID() {
        String cid = "SOFTENG754";
        CourseHandler handler = new CourseHandler();
        handler.getPrerequisites(cid);
    }


}