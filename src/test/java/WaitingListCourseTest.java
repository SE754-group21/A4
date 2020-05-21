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
    private Student student;
    @Before
    public void setUp(){
        waitingList = spy(new LinkedList<>());
        enrolledList = spy(new LinkedList<>());
        course = new Course(waitingList, enrolledList);
        student = Mockito.mock(Student.class);
    }
    @Test
    public void testAddStudentToCourse() {
        course.addStudent(student);
        assertTrue(enrolledList.size() == 1);
    }
    @Test
    public void testAddStudentToFullCourse() {
        course.setCapacity(0);
        course.addStudent(student);
        assertTrue(enrolledList.size() == 0);
    }

    @Test
    public void testAddDupStudent() {
        enrolledList.add(student);
        course.addStudent(student);
        assertTrue(enrolledList.size() == 1);
    }

    @Test
    public void removeStudent() {
        enrolledList.add(student);
        course.removeStudent(student);
        assertTrue(enrolledList.size() == 0);
    }

    @Test
    public void removeStudentWaitingList() {
        Student student2 = Mockito.mock(Student.class);
        course.setCapacity(1);
        enrolledList.add(student);
        waitingList.add(student2);
        course.removeStudent(student);
        assertTrue(enrolledList.size() == 1);
        assertTrue(waitingList.size() == 0);
        assertFalse(enrolledList.contains(student));
    }

    @Test
    public void removeStudentWaitingListFIFO() {
        Student student2 = Mockito.mock(Student.class);
        Student student3 = Mockito.mock(Student.class);
        course.setCapacity(1);
        enrolledList.add(student);
        waitingList.add(student2);
        waitingList.add(student3);
        course.removeStudent(student);
        assertTrue(enrolledList.contains(student2));
        assertTrue(waitingList.contains(student3));
    }

    @Test
    public void testUpdateStudentCourseEnrollmentList() {
        course.addStudent(student);
        verify(student, times(1))
                .setVirtualList(course, VirtualListEnum.enrolled_list);
    }

    @Test
    public void testUpdateStudentCourseWaitingList() {
        Student student2 = Mockito.mock(Student.class);
        course.setCapacity(1);
        enrolledList.add(student);
        course.addStudent(student2);
        verify(student2, times(1))
                .setVirtualList(course, VirtualListEnum.waiting_list);
    }






}