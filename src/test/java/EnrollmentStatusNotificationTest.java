import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class EnrollmentStatusNotificationTest {
    private String concessionId;
    private String cid;
    private String sid;
    private Database db;
    private Student student;
    private Course course;
    private ConcessionApplicationHandler concessionHandler;

    @Before
    public void setUp() {
        concessionId = "1829";
        cid = "SE754";
        sid = "12345";
        db = Mockito.mock(Database.class);
        student = Mockito.mock(Student.class);
        course = Mockito.mock(Course.class);
        Mockito.when(db.getCourse(cid)).thenReturn(course);
        Mockito.when(db.getStudent(sid)).thenReturn(student);
        Mockito.when(course.getCname()).thenReturn("SOFTENG 754");
        concessionHandler = new ConcessionApplicationHandler(db);
    }
    @Test
    public void testGetEnrollmentStatusNotificationConcessionApproved() {
        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        Mockito.when(db.getConcessionApplication(concessionId)).thenReturn(concessionApp);
        Mockito.when(concessionApp.getStudent()).thenReturn(student);
        Mockito.when(concessionApp.getCourse()).thenReturn(course);
        NotificationEvent notificationEvent = concessionHandler.approveConcession(concessionId);
        String notification = notificationEvent.notifyChange();
        Assert.assertEquals("Your concession for course SOFTENG 754 has been approved.",
                notification );
    }

    @Test
    public void testGetEnrollmentStatusNotificationConcessionDeclined() {
        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        ConcessionApplicationHandler handler =  new ConcessionApplicationHandler(db);
        Mockito.when(db.getConcessionApplication(concessionId)).thenReturn(concessionApp);

        Mockito.when(concessionApp.getStudent()).thenReturn(student);
        Mockito.when(concessionApp.getCourse()).thenReturn(course);
        NotificationEvent notificationEvent = concessionHandler.declineConcession(concessionId, "Don't meet prerequsities");
        String notification = notificationEvent.notifyChange();
        Assert.assertEquals("Your concession for course SOFTENG 754 " +
                "has been declined.", notification );
    }

    @Test
    public void testGetEnrollmentStatusNotificationWaitingListEnrollment() {
        Student student = Mockito.spy(new Student());
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCname()).thenReturn("SOFTENG 754");
        Mockito.doReturn(ConcessionStatusEnum.approved).when(student)
                .getConcessionStatus(course);
        Mockito.doReturn(VirtualListEnum.enrolled_list).when(student)
                .getVirtualStatus(course);
        NotificationEvent notificationEvent = student.updateVirtualList(course);
        String notification = notificationEvent.notifyChange();
        Assert.assertEquals("You have been moved off the waiting list for " +
                "SOFTENG 754 and are now enrolled.", notification );
    }

    @Test
    public void testDefaultEnrollmentNotificationSetting() {
        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        Mockito.when(db.getConcessionApplication(concessionId)).thenReturn(concessionApp);
        Mockito.when(concessionApp.getStudent()).thenReturn(student);
        Mockito.when(concessionApp.getCourse()).thenReturn(course);
        NotificationEvent notificationEvent = concessionHandler.approveConcession(concessionId);
        NotificationSettingEnum defaultSetting = notificationEvent.getNotificationSetting();
        Assert.assertEquals(NotificationSettingEnum.email, defaultSetting);
    }


    @Test
    public void testChangeEnrollmentNotificationSettingsMobile() {
        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        Mockito.when(db.getConcessionApplication(concessionId)).thenReturn(concessionApp);
        Mockito.when(concessionApp.getStudent()).thenReturn(student);
        Mockito.when(concessionApp.getCourse()).thenReturn(course);
        NotificationEvent notificationEvent = concessionHandler.approveConcession(concessionId);
        notificationEvent.changeNotificationSetting(NotificationSettingEnum.mobile);
        NotificationSettingEnum newSetting = notificationEvent.getNotificationSetting();
        Assert.assertEquals(NotificationSettingEnum.mobile, newSetting);
    }

    @Test
    public void testChangeEnrollmentNotificationSettingsNone() {
        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        Mockito.when(db.getConcessionApplication(concessionId)).thenReturn(concessionApp);
        Mockito.when(concessionApp.getStudent()).thenReturn(student);
        Mockito.when(concessionApp.getCourse()).thenReturn(course);
        NotificationEvent notificationEvent = concessionHandler.approveConcession(concessionId);
        notificationEvent.changeNotificationSetting(NotificationSettingEnum.none);
        NotificationSettingEnum newSetting = notificationEvent.getNotificationSetting();
        String notification = notificationEvent.notifyChange();
        Assert.assertEquals(null, notification);
        Assert.assertEquals(NotificationSettingEnum.none, newSetting);
    }



}
