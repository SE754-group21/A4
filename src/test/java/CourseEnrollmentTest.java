import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertFalse;
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
        handler.addCourse(course);
        handler.addStudent(student);
        boolean meets = handler.studentMeetsPrerequisites(sid, cid);
        assertTrue(meets);
    }

    @Test
    public void testStudentNotMeetPrerequisites() {
        String cid = "SE754";
        String sid = "12345";
        Student student = Mockito.mock(Student.class);
        Course course = Mockito.mock(Course.class);
        Course prereq = Mockito.mock(Course.class);
        List<Course> courses = new ArrayList<>();
        courses.add(prereq);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(student.getSid()).thenReturn(sid);
        Mockito.when(student.getTakenCourses()).thenReturn(new ArrayList<>());
        Mockito.when(course.getPrerequisites()).thenReturn(courses);
        EnrollmentHandler handler = new EnrollmentHandler();
        handler.addCourse(course);
        handler.addStudent(student);
        boolean meets = handler.studentMeetsPrerequisites(sid, cid);
        assertFalse(meets);
    }

    @Test (expected = NoSuchElementException.class)
    public void testStudentNotMeetPrerequisitesInvalidCid() {
        String cid = "SE754";
        String sid = "12345";
        Student student = Mockito.mock(Student.class);
        Mockito.when(student.getSid()).thenReturn(sid);
        EnrollmentHandler handler = new EnrollmentHandler();
        handler.addStudent(student);
        handler.studentMeetsPrerequisites(sid, cid);
    }
}
