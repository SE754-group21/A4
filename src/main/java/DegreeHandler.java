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
        return degrees.get(did).getDname();
    }

}
