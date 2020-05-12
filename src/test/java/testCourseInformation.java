import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
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
        List<Staff> staff = new ArrayList<Staff>();
        CourseHandler handler = new CourseHandler();
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getStaff()).thenReturn(staff);
        handler.addCourse(course);
        List<Staff> s = handler.getStaff(cid);
        assertEquals(s.size(), 0);
    }


}