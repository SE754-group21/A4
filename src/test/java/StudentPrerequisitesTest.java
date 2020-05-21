import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StudentPrerequisitesTest {
    private Database db;
    private Course course;
    private String cid, sid;
    @Before
    public void setUp() {
        cid = "SE754";
        sid = "12345";
        db = Mockito.mock(Database.class);
        course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
    }

    @Test
    public void testStudentMeetsPrerequisites() {
        Student s = new Student();
        Student student = Mockito.spy(s);
        List<Course> courses = new ArrayList<>();
        Mockito.doReturn(courses).when(student).getTakenCourses();
        Mockito.when(course.getPrerequisites()).thenReturn(courses);
        boolean meets = student.meetsPrereqs(course);
        assertTrue(meets);
    }

    @Test
    public void testStudentNotMeetPrerequisites() {
        Student s = new Student();
        Student student = Mockito.spy(s);
        Course prereq = Mockito.mock(Course.class);
        List<Course> courses = new ArrayList<>();
        courses.add(prereq);
        Mockito.doReturn(new ArrayList<>()).when(student).getTakenCourses();
        Mockito.when(course.getPrerequisites()).thenReturn(courses);
        boolean meets = student.meetsPrereqs(course);
        assertFalse(meets);
    }
}
