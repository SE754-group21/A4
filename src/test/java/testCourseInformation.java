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


}