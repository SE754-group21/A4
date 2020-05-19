import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class WaitingListCourseTest {

    @Spy
    private List<Student> students = new ArrayList<>();

    @InjectMocks
    private Course course;

    @Mock
    Student student;

    @Test
    public void testAddStudent() {
        course.addStudent(student);
        assertTrue(students.size() == 1);
    }
}