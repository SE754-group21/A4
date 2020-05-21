public class ConcessionApplication {

    private Student student;
    private Course course;

    private ConcessionStatusEnum concessionStatus;

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
        return "";
    }
}
