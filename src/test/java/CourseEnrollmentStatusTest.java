import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Category(UnitTests.class)
public class CourseEnrollmentStatusTest {
    private Course course;
    private Course course2;
    private Student student;
    private EnrollmentHandler enrollmentHandler;
    private Database db;

    @Before
    public void setUp() {
        course = Mockito.mock(Course.class);
        course2 = Mockito.mock(Course.class);
        student = Mockito.mock(Student.class);
        db = Mockito.mock(Database.class);
        enrollmentHandler =  new EnrollmentHandler(db);
    }

    @Test
    public void testGetEnrolledCourseEnrollmentStatus() {

        Mockito.when(student.getEnrollmentStatusForCourse(course)).thenReturn(EnrollmentStatusEnum.enrolled);

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE754";
        Mockito.when(course.getCid()).thenReturn(cid);

        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
        EnrollmentStatusEnum status = enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);

        Assert.assertEquals(EnrollmentStatusEnum.enrolled, status);
    }

    @Test
    public void testGetConcessionDeniedCourseEnrollmentStatus() {
        Student student = new Student();
        Course course = new Course();
        ConcessionApplicationHandler handler = new ConcessionApplicationHandler(db);
        String concessionid = "124342";
        ConcessionApplication ca = new ConcessionApplication(student, course);
        Mockito.when(db.getConcessionApplication(concessionid)).thenReturn(ca);

        String sid = "A123";
        String cid = "SE754";

        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);

        ca.addInfo(course, student);
        student.addConcession(course, ca);
        handler.declineConcession(concessionid, "Don't meet prerequsities");
        EnrollmentStatusEnum status = enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);
        Assert.assertEquals(EnrollmentStatusEnum.concession_denied, status);
    }

    @Test
    public void testGetAwaitingConcessionCourseEnrollmentStatus() {
        Student student = new Student();
        Course course = new Course();
        String concessionid = "124342";
        ConcessionApplication ca = new ConcessionApplication(student, course);
        Mockito.when(db.getConcessionApplication(concessionid)).thenReturn(ca);

        String sid = "A123";
        String cid = "SE754";
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);

        ca.addInfo(course, student);
        student.addConcession(course, ca);
        EnrollmentStatusEnum status = enrollmentHandler
                .getEnrollmentStatusForCourse(sid, cid);
        Assert.assertEquals(EnrollmentStatusEnum.awaiting_concession, status);
    }

    @Test
    public void testGetCompletedCourseEnrollmentStatus() {
        Student student = new Student();
        Course course = new Course();

        String sid = "A123";
        String cid = "SE754";
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);

        List<Course> courses = new ArrayList<>();
        courses.add(course);
        student.setTakenCourses(courses);

        EnrollmentStatusEnum status = enrollmentHandler
                .getEnrollmentStatusForCourse(sid, cid);
        Assert.assertEquals(EnrollmentStatusEnum.completed, status);
    }

    @Test (expected = NoSuchElementException.class)
    public void testGetCourseEnrollmentStatusInvalidCourse() {

        Mockito.when(student.getEnrollmentStatusForCourse(course)).thenReturn(null);

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE867";
        Mockito.when(course.getCid()).thenReturn(cid);

        Mockito.when(db.getStudent(sid)).thenReturn(student);
        enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);
    }

    @Test (expected = NoSuchElementException.class)
    public void testGetCourseEnrollmentStatusInvalidStudent() {

        String sid = "oldStduent";
        String cid = "SE754";
        Mockito.when(course.getCid()).thenReturn(cid);

        Mockito.when(db.getCourse(cid)).thenReturn(course);
        enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);
    }

    @Test
    public void testGetWaitingListEnrollmentStatus() {
        Mockito.when(student.getEnrollmentStatusForCourse(course)).thenReturn(EnrollmentStatusEnum.waiting_list);

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE701";
        Mockito.when(course.getCid()).thenReturn(cid);

        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
        EnrollmentStatusEnum status = enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);

        Assert.assertEquals(EnrollmentStatusEnum.waiting_list, status);
    }

    @Test
    public void testGetWaitingListPosition() {
        Mockito.when(student.getEnrollmentStatusForCourse(course))
                .thenReturn(EnrollmentStatusEnum.waiting_list);
        Mockito.when(course.getWaitingListPosition(student)).thenReturn(62);

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE701";
        Mockito.when(course.getCid()).thenReturn(cid);

        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
        int pos = enrollmentHandler.getWaitingListPositionForStudent(sid, cid);

        Assert.assertEquals(62, pos);
    }

    @Test (expected = NoSuchElementException.class)
    public void testGetWaitingListPositionInvalidCourseEnrollmentStatus() {
        Mockito.when(student.getEnrollmentStatusForCourse(course)).thenReturn(EnrollmentStatusEnum.enrolled);
        Mockito.when(student.getWaitingListNumber(course)).thenReturn(-1);

        String sid = "A123";
        Mockito.when(student.getSid()).thenReturn(sid);
        String cid = "SE701";
        Mockito.when(course.getCid()).thenReturn(cid);

        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
        enrollmentHandler.getEnrollmentStatusForCourse(sid, cid);
        enrollmentHandler.getWaitingListPositionForStudent(sid, cid);
    }

    @Test
    public void testSortByYearEnrolled() {
        List<Course> coursesTaken = new ArrayList<>();
        coursesTaken.add(course);
        coursesTaken.add(course2);
        Mockito.when(student.getTakenCourses()).thenReturn(coursesTaken);

        Mockito.when(student.getYearEnrolled(course)).thenReturn(2016);
        Mockito.when(student.getYearEnrolled(course2)).thenReturn(2017);

        String sid = "A123";
        String cid = "SE701";
        String cid2 = "SE754";
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getCourse(cid2)).thenReturn(course2);
        Mockito.when(db.getStudent(sid)).thenReturn(student);

        int year = 2016;
        List<Course> courses = enrollmentHandler.getCoursesCompletedInYear(year, sid);
        Assert.assertEquals(1, courses.size());
        Assert.assertEquals(courses.get(0), course);
    }
}
