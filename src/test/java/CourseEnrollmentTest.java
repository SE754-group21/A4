import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@Category(UnitTests.class)
public class CourseEnrollmentTest {
    private Database db;
    private Student student;
    private Course course;
    private String cid, sid;

    @Before
    public void setUp() {
        cid = "SE754";
        sid = "12345";
        db = Mockito.mock(Database.class);
        student = Mockito.mock(Student.class);
        course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(student.getSid()).thenReturn(sid);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
    }

    @Test
    public void testStudentMeetsPrerequisites() {
        Mockito.when(student.meetsPrereqs(course)).thenReturn(true);
        EnrollmentHandler handler = new EnrollmentHandler(db);
        boolean meets = handler.studentMeetsPrerequisites(sid, cid);
        assertTrue(meets);
    }

    @Test
    public void testStudentNotMeetPrerequisites() {
        Mockito.when(student.meetsPrereqs(course)).thenReturn(false);
        EnrollmentHandler handler = new EnrollmentHandler(db);
        boolean meets = handler.studentMeetsPrerequisites(sid, cid);
        assertFalse(meets);
    }

    @Test
    public void testEnrolPrerequisitesMetSeatsAvailable() {
        EnrollmentHandler h = new EnrollmentHandler(db);
        EnrollmentHandler handler = Mockito.spy(h);
        Mockito.doReturn(true).when(handler).seatsRemaining(course);
        Mockito.doReturn(true).when(handler).studentMeetsPrerequisites(sid, cid);
        Mockito.doReturn(ConcessionStatusEnum.not_applied).when(handler).concessionStatus(student, course);
        boolean success = handler.enrollStudentCourse(sid, cid);
        assertTrue(success);
    }

    @Test
    public void testEnrolPrerequisitesMetNoSeatsAvailable() {
        EnrollmentHandler h = new EnrollmentHandler(db);
        EnrollmentHandler handler = Mockito.spy(h);
        Mockito.doReturn(false).when(handler).seatsRemaining(course);
        Mockito.doReturn(true).when(handler).studentMeetsPrerequisites(sid, cid);
        Mockito.doReturn(ConcessionStatusEnum.not_applied).when(handler).concessionStatus(student, course);
        boolean success = handler.enrollStudentCourse(sid, cid);
        assertFalse(success);
    }

    @Test
    public void testEnrolPrerequisitesNotMetSeatsAvailable() {
        EnrollmentHandler h = new EnrollmentHandler(db);
        EnrollmentHandler handler = Mockito.spy(h);
        Mockito.doReturn(true).when(handler).seatsRemaining(course);
        Mockito.doReturn(false).when(handler).studentMeetsPrerequisites(sid, cid);
        Mockito.doReturn(ConcessionStatusEnum.not_applied).when(handler).concessionStatus(student, course);
        boolean success = handler.enrollStudentCourse(sid, cid);
        assertFalse(success);
    }

    @Test
    public void testGetSeatsAvailableNone() {
        EnrollmentHandler handler = new EnrollmentHandler(db);
        Mockito.when(course.getRemainingSeats()).thenReturn(0);
        boolean remaining = handler.seatsRemaining(course);
        assertFalse(remaining);
    }

    @Test
    public void testGetSeatsAvailable() {
        EnrollmentHandler handler = new EnrollmentHandler(db);
        Mockito.when(course.getRemainingSeats()).thenReturn(1);
        boolean remaining = handler.seatsRemaining(course);
        assertTrue(remaining);
    }

    @Test
    public void testStudentCanEnrollIfConcession() {
        EnrollmentHandler h = new EnrollmentHandler(db);
        EnrollmentHandler handler = Mockito.spy(h);
        Mockito.doReturn(true).when(handler).seatsRemaining(course);
        Mockito.doReturn(false).when(handler).studentMeetsPrerequisites(sid, cid);
        Mockito.doReturn(ConcessionStatusEnum.approved).when(handler).concessionStatus(student, course);
        boolean success = handler.enrollStudentCourse(sid, cid);
        assertTrue(success);
    }

    @Test
    public void testStudentDropCourse() {
        EnrollmentHandler handler = new EnrollmentHandler(db);
        handler.dropCourse(sid, cid);
    }

    @Test
    public void testStudentEnrollmentConcessionPending() {
        EnrollmentHandler h = new EnrollmentHandler(db);
        EnrollmentHandler handler = Mockito.spy(h);
        Mockito.doReturn(true).when(handler).seatsRemaining(course);
        Mockito.doReturn(false).when(handler).studentMeetsPrerequisites(sid, cid);
        Mockito.doReturn(ConcessionStatusEnum.pending).when(handler).concessionStatus(student, course);
        handler.enrollStudentCourse(sid, cid);
        verify(course).addStudent(student);
    }

    @Test
    public void testStudentEnrollmentConcessionDenied() {
        EnrollmentHandler h = new EnrollmentHandler(db);
        EnrollmentHandler handler = Mockito.spy(h);
        Mockito.doReturn(true).when(handler).seatsRemaining(course);
        Mockito.doReturn(false).when(handler).studentMeetsPrerequisites(sid, cid);
        Mockito.doReturn(ConcessionStatusEnum.denied).when(handler).concessionStatus(student, course);
        handler.enrollStudentCourse(sid, cid);
        verify(course, never()).addStudent(student);
    }

    @Test
    public void testStudentEnrollmentConcessionNotApplied() {
        EnrollmentHandler h = new EnrollmentHandler(db);
        EnrollmentHandler handler = Mockito.spy(h);
        Mockito.doReturn(true).when(handler).seatsRemaining(course);
        Mockito.doReturn(false).when(handler).studentMeetsPrerequisites(sid, cid);
        Mockito.doReturn(ConcessionStatusEnum.not_applied).when(handler).concessionStatus(student, course);
        handler.enrollStudentCourse(sid, cid);
        verify(course, never()).addStudent(student);
    }

    @Test
    public void testGetPositionWaitingList() {
        EnrollmentHandler handler = new EnrollmentHandler(db);
        Mockito.when(course.getWaitingListPosition(student)).thenReturn(0);
        Mockito.when(student.getEnrollmentStatusForCourse(course)).thenReturn(EnrollmentStatusEnum.waiting_list);
        int position = handler.getWaitingListPositionForStudent(sid, cid);
        assertEquals(position, 0);
        verify(course).getWaitingListPosition(student);
    }


}


