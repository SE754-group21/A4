import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Course {

    private String cid;
    private List<Course> prerequisites;
    private int capacity = 1000;
    private Queue<Student> enrolledList;
    private Queue<Student> waitingList;


    public Course() {
        enrolledList = new LinkedList<>();
        waitingList = new LinkedList<>();
    }

    public Course(Queue<Student> wait, Queue<Student> en) {
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
            updateStudent(false, student);
        } else {
            enrolledList.add(student);
            updateStudent(true, student);
        }

    }

    private void updateStudent(boolean enrolled, Student student) {
        EnrollmentStatusEnum status = enrolled ? EnrollmentStatusEnum.enrolled : EnrollmentStatusEnum.waiting_list;
        student.setEnrollmentStatusForCourse(this, status);
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
            Student studentNew = waitingList.poll();
            enrolledList.add(studentNew);
            updateStudent(true, studentNew);
        }
    }


}
