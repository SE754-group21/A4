public class ConcessionApplication {

    ConcessionStatusEnum concessionStatus;

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

    public String getStatusReason() {
        return "";
    }
}
