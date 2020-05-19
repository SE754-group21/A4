import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class WaitingListCourseTest {

    @Mock(name = "waitingList") private List<Student> waitingList = new ArrayList<>();
    @Mock(name = "enrolledList") private List<Student> enrolledList = new ArrayList<>();

    @InjectMocks
    private Course course;

    @Mock
    Student student;

    @Test
    public void testAddStudent() {
        course.addStudent(student);
        Mockito.verify(enrolledList, times(1)).add(student);
    }

    @Test
    public void testAddStudentAtCapacity() {
        course.setCapacity(0);
        course.addStudent(student);
        Mockito.verify(enrolledList, times(0)).add(student);
        Mockito.verify(waitingList, times(1)).add(student);
    }



}