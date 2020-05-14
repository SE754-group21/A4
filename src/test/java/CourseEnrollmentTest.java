import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Handler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CourseEnrollmentTest {
    private Database db;
    @Before
    public void setUp() {
        db = Mockito.mock(Database.class);
    }
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
        EnrollmentHandler handler = new EnrollmentHandler(db);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
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
        EnrollmentHandler handler = new EnrollmentHandler(db);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
        boolean meets = handler.studentMeetsPrerequisites(sid, cid);
        assertFalse(meets);
    }

    @Test
    public void testEnrolPrerequisitesMetSeatsAvailable() {
        String cid = "SE754";
        String sid = "12345";
        Student student = Mockito.mock(Student.class);
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(student.getSid()).thenReturn(sid);
        EnrollmentHandler h = new EnrollmentHandler(db);
        EnrollmentHandler handler = Mockito.spy(h);
        Mockito.doReturn(true).when(handler).seatsRemaining(course);
        Mockito.doReturn(true).when(handler).studentMeetsPrerequisites(sid, cid);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
        boolean success = handler.enrollStudentCourse(sid, cid);
        assertTrue(success);
    }

    @Test
    public void testEnrolPrerequisitesMetNoSeatsAvailable() {
        String cid = "SE754";
        String sid = "12345";
        Student student = Mockito.mock(Student.class);
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(student.getSid()).thenReturn(sid);
        EnrollmentHandler h = new EnrollmentHandler(db);
        EnrollmentHandler handler = Mockito.spy(h);
        Mockito.doReturn(false).when(handler).seatsRemaining(course);
        Mockito.doReturn(true).when(handler).studentMeetsPrerequisites(sid, cid);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
        boolean success = handler.enrollStudentCourse(sid, cid);
        assertFalse(success);
    }

    @Test
    public void testEnrolPrerequisitesNotMetSeatsAvailable() {
        String cid = "SE754";
        String sid = "12345";
        Student student = Mockito.mock(Student.class);
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(student.getSid()).thenReturn(sid);
        EnrollmentHandler h = new EnrollmentHandler(db);
        EnrollmentHandler handler = Mockito.spy(h);
        Mockito.doReturn(true).when(handler).seatsRemaining(course);
        Mockito.doReturn(false).when(handler).studentMeetsPrerequisites(sid, cid);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
        boolean success = handler.enrollStudentCourse(sid, cid);
        assertFalse(success);
    }

    @Test
    public void testGetSeatsAvailableNone() {
        String cid = "SE754";
        String sid = "12345";
        Student student = Mockito.mock(Student.class);
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(student.getSid()).thenReturn(sid);
        EnrollmentHandler handler = new EnrollmentHandler(db);
        Mockito.when(course.getRemainingSeats()).thenReturn(0);
        boolean remaining = handler.seatsRemaining(course);
        assertFalse(remaining);
    }

    @Test
    public void testGetSeatsAvailable() {
        String cid = "SE754";
        String sid = "12345";
        Student student = Mockito.mock(Student.class);
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(student.getSid()).thenReturn(sid);
        EnrollmentHandler handler = new EnrollmentHandler(db);
        Mockito.when(course.getRemainingSeats()).thenReturn(1);
        boolean remaining = handler.seatsRemaining(course);
        assertTrue(remaining);
    }
}
