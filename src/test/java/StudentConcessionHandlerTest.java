import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class StudentConcessionHandlerTest {
    private Student student;
    private Course course;

    @Before
    public void setUp(){
        student = Mockito.spy(new Student());
        course = Mockito.mock(Course.class);
    }

    @Test
    public void testcheckDenied() {
        Mockito.doReturn(ConcessionStatusEnum.denied).when(student).getConcessionStatus(course);
        Mockito.doReturn(VirtualListEnum.waiting_list).when(student).getVirtualStatus(course);
        student.updateConcession(course);
        verify(student).setEnrollmentStatusForCourse(course, EnrollmentStatusEnum.concession_denied);
        verify(student).setEnrollmentStatusForCourse(Mockito.any(Course.class), Mockito.any(EnrollmentStatusEnum.class));
    }

    @Test
    public void testcheckPending() {
        Mockito.doReturn(ConcessionStatusEnum.pending).when(student).getConcessionStatus(course);
        Mockito.doReturn(VirtualListEnum.waiting_list).when(student).getVirtualStatus(course);
        student.updateConcession(course);
        verify(student).setEnrollmentStatusForCourse(course, EnrollmentStatusEnum.awaiting_concession);
        verify(student).setEnrollmentStatusForCourse(Mockito.any(Course.class), Mockito.any(EnrollmentStatusEnum.class));
    }

    @Test
    public void testcheckConcessionSuccessAndEnrolledList() {
        Mockito.doReturn(ConcessionStatusEnum.approved).when(student).getConcessionStatus(course);
        Mockito.doReturn(VirtualListEnum.enrolled_list).when(student).getVirtualStatus(course);
        student.updateConcession(course);
        verify(student).setEnrollmentStatusForCourse(course, EnrollmentStatusEnum.enrolled);
        verify(student).setEnrollmentStatusForCourse(Mockito.any(Course.class), Mockito.any(EnrollmentStatusEnum.class));
    }

    @Test
    public void testcheckConcessionSuccessAndWaitingList() {
        Mockito.doReturn(ConcessionStatusEnum.approved).when(student).getConcessionStatus(course);
        Mockito.doReturn(VirtualListEnum.waiting_list).when(student).getVirtualStatus(course);
        student.updateConcession(course);
        verify(student).setEnrollmentStatusForCourse(course, EnrollmentStatusEnum.waiting_list);
        verify(student).setEnrollmentStatusForCourse(Mockito.any(Course.class), Mockito.any(EnrollmentStatusEnum.class));
    }
}
