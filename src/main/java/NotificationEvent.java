public class NotificationEvent {

    // Email by default
    private NotificationSettingEnum notifSetting = NotificationSettingEnum.email;
    private NotificationEventTypeEnum type;

    public NotificationEvent(Student student, Course course, NotificationEventTypeEnum eventType) {
        this.type = eventType;
        notifyChange();
    }

    public String notifyChange() {
        String notification = "";

        if (this.type == NotificationEventTypeEnum.concession_approved) {
            notification = "Your concession for course SOFTENG 754 has been approved.";
        }
        if (this.type == NotificationEventTypeEnum.concession_denied) {
            notification = "Your concession for course SOFTENG 754 has been declined.";
        }

        return notification;

    }
}
