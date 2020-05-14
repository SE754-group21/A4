import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;

public class CourseEnrollmentTest {
    @Test
    public void testStudentMeetsPrerequisites() {
        String cid = "SE754";
        String sid = "12345";
        Student student = Mockito.mock(Student.class);
        Course course = Mockito.mock(Course.class);
        List<Course> courses = new ArrayList<>();
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(student.getSid()).thenReturn(sid);
        Mockito.when(student.getTakenCourses()).thenReturn(courses);
        Mockito.when(course.getPrerequisites()).thenReturn(courses);
        EnrollmentHandler handler = new EnrollmentHandler();
        boolean meets = handler.studentMeetsPrerequisites(sid, cid);
        assertTrue(meets);
    }
}
