import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

public class NumberofSeatsTest {
    private Database db;

    @Before
    public void setUp() {
        db = Mockito.mock(Database.class);
    }

    @Test
    public void testGetTotalNumberOfSeats() {
        String cid = "SOFTENG351";
        int numSeats = 120;

        CourseHandler handler = new CourseHandler(db);
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getTotalSeats()).thenReturn(numSeats);
        Mockito.when(db.getCourse(cid)).thenReturn(course);

        int actualNumSeats = handler.getTotalSeats(cid);
        assertEquals(numSeats, actualNumSeats);
    }

    @Test (expected = NoSuchElementException.class)
    public void testGetTotalNumberOfSeatsInvalidCourse() {
        CourseHandler handler = new CourseHandler(db);

        int actualNumSeats = handler.getTotalSeats(null);
    }


    @Test
    public void testGetNumberOfAvailableSeats() {
        String cid = "SOFTENG351";
        int numSeats = 36;

        CourseHandler handler = new CourseHandler(db);
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCid()).thenReturn(cid);
        Mockito.when(course.getRemainingSeats()).thenReturn(numSeats);
        Mockito.when(db.getCourse(cid)).thenReturn(course);

        int actualNumSeats = handler.getRemainingSeats(cid);
        assertEquals(numSeats, actualNumSeats);
    }


}
