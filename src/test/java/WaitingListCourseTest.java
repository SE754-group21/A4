import org.junit.Before;
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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

public class WaitingListCourseTest {

    private List<Student> waitingList;
    private List<Student> enrolledList;
    private Course course;

    @Before
    public void setUp(){
        waitingList = spy(new ArrayList<>());
        enrolledList = spy(new ArrayList<>());
        course = new Course(waitingList, enrolledList);
    }

    @Test
    public void testAddDupStudent() {
        Student student = Mockito.mock(Student.class);
        course.addStudent(student);
        course.addStudent(student);
        assert(enrolledList.size() == 1);
    }

    @Test
    public void removeStudent() {
        Student student = Mockito.mock(Student.class);
        course.addStudent(student);
        course.removeStudent(student);
        assert(enrolledList.size() == 0);
    }


}