public class NotificationEvent {

    // Email by default
    private NotificationSettingEnum notifSetting = NotificationSettingEnum.email;
    private NotificationEventTypeEnum type;
    private Student student;
    private Course course;

    public NotificationEvent(Student student, Course course, NotificationEventTypeEnum eventType) {
        this.student = student;
        this.course = course;
        this.type = eventType;
        notifyChange();
    }

    public String notifyChange() {
        String notification = "";

        if (this.type == NotificationEventTypeEnum.concession_approved) {
            notification = "Your concession for course " + course.getCname() +" has been approved.";
        }
        if (this.type == NotificationEventTypeEnum.concession_denied) {
            notification = "Your concession for course " + course.getCname() +" has been declined.";
        }
        if (this.type == NotificationEventTypeEnum.moved_off_waiting_list) {
            notification = "You have been moved off the waiting list for " + course.getCname() + " and are now enrolled.";
        }

        return notification;

    }
}
