import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class StudentVirtualListHandler {
    private Student student;
    private Course course;

    @Before
    public void setUp(){
        student = Mockito.spy(new Student());
        course = Mockito.mock(Course.class);
    }

    @Test
    public void testCheckNotApprovedConcession() {
        Mockito.doReturn(ConcessionStatusEnum.pending).when(student).getConcessionStatus(course);
        Mockito.doReturn(VirtualListEnum.waiting_list).when(student).getVirtualStatus(course);
        student.updateVirtualList(course);
        verify(student, never()).setEnrollmentStatusForCourse(Mockito.any(Course.class), Mockito.any(EnrollmentStatusEnum.class));
    }

    @Test
    public void testCheckApprovedConcessionEnrolledList() {
        Mockito.doReturn(ConcessionStatusEnum.approved).when(student).getConcessionStatus(course);
        Mockito.doReturn(VirtualListEnum.enrolled_list).when(student).getVirtualStatus(course);
        student.updateVirtualList(course);
        verify(student).setEnrollmentStatusForCourse(course, EnrollmentStatusEnum.enrolled);
        verify(student).setEnrollmentStatusForCourse(Mockito.any(Course.class), Mockito.any(EnrollmentStatusEnum.class));
    }

    @Test
    public void testCheckApprovedConcessionWaitingList() {
        Mockito.doReturn(ConcessionStatusEnum.approved).when(student).getConcessionStatus(course);
        Mockito.doReturn(VirtualListEnum.waiting_list).when(student).getVirtualStatus(course);
        student.updateVirtualList(course);
        verify(student).setEnrollmentStatusForCourse(course, EnrollmentStatusEnum.waiting_list);
        verify(student).setEnrollmentStatusForCourse(Mockito.any(Course.class), Mockito.any(EnrollmentStatusEnum.class));
    }
}
