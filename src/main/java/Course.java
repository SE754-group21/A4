import java.util.ArrayList;
import java.util.List;

public class Course {

    private String cid;
    private List<Course> prerequisites;
    int capacity = 1000;
    private List<Student> enrolledList;
    private List<Student> waitingList;


    public Course() {
        enrolledList = new ArrayList<>();
        waitingList = new ArrayList<>();
    }

    public Course(List<Student> wait, List<Student> en) {
        this.enrolledList = en;
        this.waitingList = wait;
    }


    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setCapacity(int capacity) { this.capacity = capacity; }

    public void setPrerequisites(List<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getCname() {
        return "";
    }

    public String getCid() {
        return this.cid;
    }

    public String getCdesc(){
        return "";
    }

    public String getCdept() {return "";}

    public List<Staff> getStaff() {
        return null;
    }

    public List<Course> getPrerequisites() {
        return prerequisites;
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

    public void addStudent(Student student) {
        if (registeredStudent(student)) return;
        if (enrolledList.size() == capacity) {
            waitingList.add(student);
        } else {
            enrolledList.add(student);
        }

    }

    private boolean registeredStudent(Student student) {
        return enrolledList.contains(student) || waitingList.contains(student);
    }

    public void removeStudent(Student student) {
        if (enrolledList.contains(student))
            enrolledList.remove(student);
        else if (waitingList.contains(student))
            waitingList.remove(student);
        if (enrolledList.size() < capacity && waitingList.size() > 0) {
            Student studentNew = waitingList.remove(0);
            enrolledList.add(studentNew);
        }

    }


}
