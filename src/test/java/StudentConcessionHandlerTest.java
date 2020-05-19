import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class StudentConcessionHandlerTest {

    private Student student;

    @Before
    public void setUp(){
        student = Mockito.spy(new Student());
    }

    @Test
    public void testcheckDenied() {
        Course course = Mockito.mock(Course.class);
        Mockito.doReturn(ConcessionStatusEnum.denied).when(student).getConcessionStatus(course);
        Mockito.doReturn(VirtualListEnum.waiting_list).when(student).getVirtualStatus(course);
        student.updateConcession(course);
        verify(student).setEnrollmentStatusForCourse(course, EnrollmentStatusEnum.concession_denied);
        verify(student).setEnrollmentStatusForCourse(Mockito.any(Course.class), Mockito.any(EnrollmentStatusEnum.class));
    }

    @Test
    public void testcheckPending() {
        Course course = Mockito.mock(Course.class);
        Mockito.doReturn(ConcessionStatusEnum.pending).when(student).getConcessionStatus(course);
        Mockito.doReturn(VirtualListEnum.waiting_list).when(student).getVirtualStatus(course);
        student.updateConcession(course);
        verify(student).setEnrollmentStatusForCourse(course, EnrollmentStatusEnum.awaiting_concession);
        verify(student).setEnrollmentStatusForCourse(Mockito.any(Course.class), Mockito.any(EnrollmentStatusEnum.class));
    }
}
