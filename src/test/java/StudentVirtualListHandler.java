import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class StudentVirtualListHandler {
    private Student student;

    @Before
    public void setUp(){
        student = Mockito.spy(new Student());
    }

    @Test
    public void testCheckNotApprovedConcession() {
        Course course = Mockito.mock(Course.class);
        Mockito.doReturn(ConcessionStatusEnum.pending).when(student).getConcessionStatus(course);
        Mockito.doReturn(VirtualListEnum.waiting_list).when(student).getVirtualStatus(course);
        student.updateVirtualList(course);
        verify(student, never()).setEnrollmentStatusForCourse(Mockito.any(Course.class), Mockito.any(EnrollmentStatusEnum.class));
    }
}
