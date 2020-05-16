


public class ConcessionApplicationHandler {

    private Database db;

    public ConcessionApplicationHandler(Database db) {
        this.db = db;
    }

    public String submitApplication(String sid, String cid, ConcessionApplication concessionApp) {
        Course course = db.getCourse(cid);
        Student student = db.getStudent(sid);

        EnrollmentRequestStatusEnum enrolmentRequestStatus = student.getEnrollmentRequestStatusForCourse(course);

        if (enrolmentRequestStatus == EnrollmentRequestStatusEnum.prerequisites_met) {
            return "Students that meet prerequisites are not required to apply for a concession";
        }
        return null;
    }
}
