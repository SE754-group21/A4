import java.util.*;

public class CourseHandler {
    private Database db;
    public CourseHandler(Database db) {
        this.db = db;
    }

    public String getCname(String cid) {
        return db.getCourse(cid).getCname();
    }

    public String getCdesc(String cid) {
        return db.getCourse(cid).getCdesc();
    }

    public List<String> getStaff(String cid) {
        List<String> sids = new ArrayList<>();
        List<Staff> allstaff = db.getCourse(cid).getStaff();
        for (Staff s : allstaff) {
            sids.add(s.getID());
        }
        return sids;
    }

    public List<String> getCHours(String cid) {
        return db.getCourse(cid).getCHours();
    }

    public int getTotalSeats(String cid) {
        return db.getCourse(cid).getTotalSeats();
    }

    public int getRemainingSeats(String cid) {
        return db.getCourse(cid).getRemainingSeats();
    }

    public List<String> getPrerequisites(String cid) {
        List<String> sids = new ArrayList<>();
        Course course = db.getCourse(cid);
        List<Course> prereqs = course.getPrerequisites();
        for (Course s : prereqs) {
            sids.add(s.getCid());
        }
        return sids;
    }


}