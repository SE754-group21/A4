import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Category(IntegrationTests.class)
public class EnrollmentIntegrationTest {

    @Test
    public void testStudentMeetsPrerequisitesIntegration() {
        Database db = new Database();
        String cid = "SE754";
        String sid = "12345";

        Course course = new Course();
        course.setCid(cid);
        Student student = new Student();
        student.setSid(sid);

        db.addCourse(cid, course);
        db.addStudent(sid, student);

        Course prerequisite1 = new Course();
        Course prerequisite2 = new Course();
        List<Course> courses = new ArrayList<>();
        courses.add(prerequisite1);
        courses.add(prerequisite2);

        student.setTakenCourses(courses);
        course.setPrerequisites(courses);

        EnrollmentHandler handler = new EnrollmentHandler(db);
        boolean meets = handler.studentMeetsPrerequisites(sid, cid);
        assertTrue(meets);
    }

    @Test
    public void testStudentEnrollment() {
        Database db = new Database();
        String cid = "SE754", sid = "12345";

        Course course = new Course();
        course.setCid(cid);
        Student student = new Student();
        student.setSid(sid);
        db.addCourse(cid, course);
        db.addStudent(sid, student);

        EnrollmentHandler handler = new EnrollmentHandler(db);
        boolean success = handler.enrollStudentCourse(sid, cid);
        assertTrue(success);
        EnrollmentStatusEnum status = handler.getEnrollmentStatusForCourse(sid, cid);
        assertEquals(status, EnrollmentStatusEnum.enrolled);
    }
}
