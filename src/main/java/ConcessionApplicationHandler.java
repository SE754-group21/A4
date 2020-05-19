


public class ConcessionApplicationHandler {

    private Database db;

    public ConcessionApplicationHandler(Database db) {
        this.db = db;
    }

    public String submitApplication(String sid, String cid) {
        Course course = db.getCourse(cid);
        Student student = db.getStudent(sid);

        EnrollmentStatusEnum enrollmentStatus = student.getEnrollmentStatusForCourse(course);

        if(enrollmentStatus == EnrollmentStatusEnum.not_enrolled){
            return "Student must enroll to apply for a concession";
        }

        EnrollmentRequestStatusEnum enrolmentRequestStatus = student.getEnrollmentRequestStatusForCourse(course);
        if (enrolmentRequestStatus == EnrollmentRequestStatusEnum.prerequisites_met) {
            return "Students that meet prerequisites are not required to apply for a concession";
        } else if (enrolmentRequestStatus == EnrollmentRequestStatusEnum.awaiting_concession) {
            return "Students may only submit one concession application for a course";
        }
        //Submit concession application
        ConcessionApplication concessionApp = new ConcessionApplication();
        db.addConcessionApplication(cid, sid, concessionApp);
        student.setEnrollmentRequestStatusForCourse(course, EnrollmentRequestStatusEnum.awaiting_concession);
        concessionApp.setConcessionStatus(ConcessionStatusEnum.pending);
        return "Concession application submitted";
    }

    public NotificationEvent approveConcession(ConcessionApplication app) {
        app.setConcessionStatus(ConcessionStatusEnum.approved);
        Student student = app.getStudent();
        Course course = app.getCourse();
        student.updateConcession(course);
        NotificationEvent notificationEvent = new NotificationEvent(student, course, NotificationEventTypeEnum.concession_approved);
        return notificationEvent;
    }

    public NotificationEvent declineConcession(ConcessionApplication app) {
        app.setConcessionStatus(ConcessionStatusEnum.denied);
        Student student = app.getStudent();
        Course course = app.getCourse();
        student.updateConcession(course);
        NotificationEvent notificationEvent = new NotificationEvent(student, course, NotificationEventTypeEnum.concession_denied);
        return notificationEvent;
    }


}
