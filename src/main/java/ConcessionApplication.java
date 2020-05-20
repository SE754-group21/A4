public class ConcessionApplication {

    private ConcessionStatusEnum concessionStatus;

    public String getConcessionReason() {
        return "";
    }

    public boolean isConcessionApproved() {
        return false;
    }

    public ConcessionStatusEnum getConcessionStatus() {
        return null;
    }

    public boolean setConcessionStatus(ConcessionStatusEnum concessionStatus) {
        this.concessionStatus = concessionStatus;
        return true;
    }

    public Student getStudent() {return null; }
    public Course getCourse() {return null; }

    public String getStatusReason() {
        return "";
    }
}
