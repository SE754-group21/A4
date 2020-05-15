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
            sids.add(s.getSid());
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

    public List<Course> search(String search) {
        List<Course> searchedCourses = new ArrayList<>();
        Map<String, Course> courses = db.getAllCourses();
        for (Map.Entry<String, Course> e : courses.entrySet()) {
            if (e.getValue().getCname() != null && e.getValue().getCname().toLowerCase().contains(search.toLowerCase())) {
                searchedCourses.add(e.getValue());
            }
            if(e.getValue().getCdesc() != null && e.getValue().getCdesc().toLowerCase().contains(search.toLowerCase())){
                searchedCourses.add(e.getValue());
            }
            if(e.getValue().getCdept() != null && e.getValue().getCdept().toLowerCase().contains(search.toLowerCase())){
                searchedCourses.add(e.getValue());
            }
            List<Staff> staff = e.getValue().getStaff();
            for(Staff s: staff) {
                if (s.getFirst() != null && s.getFirst().toLowerCase().contains(search.toLowerCase())) {
                    searchedCourses.add(e.getValue());
                }
            }
        }
        return searchedCourses;
    }
}