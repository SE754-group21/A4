import java.util.*;

public class DegreeHandler {
    private Map<String, Degree> degrees;

    public DegreeHandler() {
        degrees = new HashMap<String, Degree>();
    }

    public void addDegree(Degree degree) {
        degrees.put(degree.getDid(), degree);
    }

    public String getDname(String did){
        if (degrees.get(did) == null) throw new NoSuchElementException("degree with id not found");
        return degrees.get(did).getDname();
    }

    public List<String> getCompulsoryCourses(String did) {
        if (degrees.get(did) == null) throw new NoSuchElementException("degree with id not found");
        List<String> courseIDs = new ArrayList<String>();
        List<Course> courses = degrees.get(did).getCompulsoryCourses();
        for (Course course: courses){
            courseIDs.add(course.getCid());
        }
        return courseIDs;
    }


    public List<String> getElectiveCourses(String did) {
        if (degrees.get(did) == null) throw new NoSuchElementException("degree with id not found");
        List<String> courseIDs = new ArrayList<String>();
        List<Course> courses = degrees.get(did).getElectiveCourses();
        for (Course course: courses){
            courseIDs.add(course.getCid());
        }
        return courseIDs;
    }

}
