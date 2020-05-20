import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@Category(IntegrationTests.class)
public class WaitingListCourseTest {

    private Queue<Student> waitingList;
    private Queue<Student> enrolledList;
    private Course course;

    @Before
    public void setUp(){
        waitingList = spy(new LinkedList<>());
        enrolledList = spy(new LinkedList<>());
        course = new Course(waitingList, enrolledList);
    }

    @Test
    public void testAddDupStudent() {
        Student student = Mockito.mock(Student.class);
        course.addStudent(student);
        course.addStudent(student);
        assertTrue(enrolledList.size() == 1);
    }

    @Test
    public void removeStudent() {
        Student student = Mockito.mock(Student.class);
        course.addStudent(student);
        course.removeStudent(student);
        assertTrue(enrolledList.size() == 0);
    }

    @Test
    public void removeStudentWaitingList() {
        Student student1 = Mockito.mock(Student.class);
        Student student2 = Mockito.mock(Student.class);
        course.setCapacity(1);
        course.addStudent(student1);
        course.addStudent(student2);
        course.removeStudent(student1);
        assertTrue(enrolledList.size() == 1);
        assertTrue(waitingList.size() == 0);
        assertFalse(enrolledList.contains(student1));
    }

    @Test
    public void testUpdateStudentCourseEnrollmentList() {
        Student student = Mockito.mock(Student.class);
        course.addStudent(student);
        verify(student, times(1)).setVirtualList(course, VirtualListEnum.enrolled_list);
    }

    @Test
    public void testUpdateStudentCourseWaitingList() {
        Student student1 = Mockito.mock(Student.class);
        Student student2 = Mockito.mock(Student.class);
        course.setCapacity(1);
        course.addStudent(student1);
        course.addStudent(student2);
        verify(student2, times(1)).setVirtualList(course, VirtualListEnum.waiting_list);
    }



}