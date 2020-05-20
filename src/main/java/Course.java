import java.util.Date;
import java.util.List;

public class Course {

    String cid;
    List<Course> prerequisites;
    Date enrolledDate;

    public Course() {

    }

    public String getCname() {
        return "";
    }

    public String getCid() {
        return "";
    }

    public String getCdesc(){
        return "";
    }

    public String getCdept() {return "";}

    public List<Staff> getStaff() {
        return null;
    }

    public List<Course> getPrerequisites() {
        return null;
    }

    public List<String> getCHours() {
        return null;
    }

    public int getTotalSeats() {
        return 0;
    }

    public int getRemainingSeats() {
        return 0;
    }

    public void addStudent() {}

}
