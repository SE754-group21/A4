public class ConcessionApplication {

    private Student student;
    private Course course;
    private String statusReason;
    private ConcessionStatusEnum concessionStatus;

    public ConcessionApplication(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.concessionStatus = ConcessionStatusEnum.pending;
        this.student.setEnrollmentStatusForCourse(course, EnrollmentStatusEnum.awaiting_concession);
    }

    public String getConcessionReason() {
        return "";
    }

    public ConcessionStatusEnum getConcessionStatus() {
        return concessionStatus;
    }

    public boolean setConcessionStatus(ConcessionStatusEnum concessionStatus) {
        this.concessionStatus = concessionStatus;
        return true;
    }

    public void addInfo(Course c, Student s){
        this.student = s;
        this.course = c;
        this.concessionStatus = ConcessionStatusEnum.pending;
    }

    public Student getStudent() {return student; }
    public Course getCourse() {return course; }

    public String getStatusReason() {
        return this.statusReason;
    }

    public void setStatusReason(String s) {
        this.statusReason = s;
    }
}
