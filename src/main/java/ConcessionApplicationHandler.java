import java.util.NoSuchElementException;

public class ConcessionApplicationHandler {

    private Database db;

    public ConcessionApplicationHandler(Database db) {
        this.db = db;
    }

    public String submitApplication(String sid, String cid) {
        Course course = db.getCourse(cid);
        Student student = db.getStudent(sid);

        EnrollmentRequestStatusEnum enrolmentRequestStatus = student.getEnrollmentRequestStatusForCourse(course);
        if (enrolmentRequestStatus == EnrollmentRequestStatusEnum.prerequisites_met) {
            throw new IllegalStateException("Students that meet prerequisites are not required to apply for a concession");
        } else if (enrolmentRequestStatus == EnrollmentRequestStatusEnum.awaiting_concession) {
            throw new IllegalStateException("Students may only submit one concession application for a course");
        }
        //Submit concession application
        ConcessionApplication concessionApp = new ConcessionApplication(student, course);
        concessionApp.addInfo(course, student);

        String concessionID = db.addConcessionApplication(concessionApp);
        student.addConcession(course, concessionApp);
        student.setEnrollmentStatusForCourse(course, EnrollmentStatusEnum.awaiting_concession);
        concessionApp.setConcessionStatus(ConcessionStatusEnum.pending);
        return concessionID;
    }


    public NotificationEvent approveConcession(String cid) {
        ConcessionApplication app = db.getConcessionApplication(cid);
        app.setConcessionStatus(ConcessionStatusEnum.approved);
        Student student = app.getStudent();
        Course course = app.getCourse();
        student.updateConcession(course);
        NotificationEvent notificationEvent = new NotificationEvent(student, course, NotificationEventTypeEnum.concession_approved);
        return notificationEvent;
    }

    public NotificationEvent declineConcession(String cid, String reason) {
        ConcessionApplication app = db.getConcessionApplication(cid);
        app.setConcessionStatus(ConcessionStatusEnum.denied);
        app.setStatusReason(reason);
        Student student = app.getStudent();
        Course course = app.getCourse();
        student.setEnrollmentStatusForCourse(course, EnrollmentStatusEnum.concession_denied);
        student.updateConcession(course);
        NotificationEvent notificationEvent = new NotificationEvent(student, course, NotificationEventTypeEnum.concession_denied);
        return notificationEvent;
    }

    public String getConcessionReason(String sid, String cid) {
        Student student = db.getStudent(sid);
        Course course = db.getCourse(cid);

        ConcessionApplication concessionApplication = student.getConcessionApplication(course);
        return concessionApplication.getConcessionReason();
    }

    public String getStatusReason(String sid, String cid) {
        Student student = db.getStudent(sid);
        Course course = db.getCourse(cid);

        ConcessionApplication concessionApplication = student.getConcessionApplication(course);
        return concessionApplication.getStatusReason();
    }

    public String getConcessionStatus(String sid, String cid) {
        Student student = db.getStudent(sid);
        Course course = db.getCourse(cid);
        ConcessionStatusEnum concessionStatus = concessionStatus(student, course);
        if (concessionStatus == ConcessionStatusEnum.pending)
            return "Pending - awaiting course approval";
        else if (concessionStatus == ConcessionStatusEnum.denied)
            return "Denied - concession not approved";
        else if (concessionStatus == ConcessionStatusEnum.approved)
            return "Approved - concession accepted and enrollment complete";
        else
            return "The student has no concession for this course";
    }

    public ConcessionStatusEnum concessionStatus(Student student, Course course) {
        ConcessionApplication concessionApplication = student.getConcessionApplication(course);
        return concessionApplication.getConcessionStatus();
    }

    public ConcessionApplication getConcession(String cid) {
        ConcessionApplication app = db.getConcessionApplication(cid);
        if (app == null)
            throw new NoSuchElementException("Invalid cid");
        return app;
    }



}
