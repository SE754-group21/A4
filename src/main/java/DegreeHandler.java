import java.util.*;

public class DegreeHandler {
    private Database db;

    public DegreeHandler(Database db) {
        this.db = db;
    }


    public String getDname(String did){
        return db.getDegree(did).getDname();
    }

    public List<String> getCompulsoryCourses(String did) {
        List<String> courseIDs = new ArrayList<String>();
        Degree degree = db.getDegree(did);
        List<Course> courses = degree.getCompulsoryCourses();
        for (Course course: courses){
            courseIDs.add(course.getCid());
        }
        return courseIDs;
    }


    public List<String> getElectiveCourses(String did) {
        List<String> courseIDs = new ArrayList<String>();
        Degree degree = db.getDegree(did);
        List<Course> courses = degree.getElectiveCourses();
        for (Course course: courses){
            courseIDs.add(course.getCid());
        }
        return courseIDs;
    }

}
