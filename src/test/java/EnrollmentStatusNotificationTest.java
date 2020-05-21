import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class EnrollmentStatusNotificationTest {
    private String cid;
    private String sid;
    private Database db;
    private Student student;
    private Course course;
    private ConcessionApplicationHandler concessionHandler;

    @Before
    public void setUp() {
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
        String cid = "24";
        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        Mockito.when(db.getConcessionApplication(cid)).thenReturn(concessionApp);
        Mockito.when(concessionApp.getStudent()).thenReturn(student);
        Mockito.when(concessionApp.getCourse()).thenReturn(course);
        NotificationEvent notificationEvent = concessionHandler.approveConcession(cid);
        String notification = notificationEvent.notifyChange();
        Assert.assertEquals("Your concession for course SOFTENG 754 has been approved.", notification );

    }

    @Test
    public void testGetEnrollmentStatusNotificationConcessionDeclined() {

        String cid = "24";
        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        ConcessionApplicationHandler handler =  new ConcessionApplicationHandler(db);
        Mockito.when(db.getConcessionApplication(cid)).thenReturn(concessionApp);

        Mockito.when(concessionApp.getStudent()).thenReturn(student);
        Mockito.when(concessionApp.getCourse()).thenReturn(course);
        NotificationEvent notificationEvent = concessionHandler.declineConcession(cid);
        String notification = notificationEvent.notifyChange();
        Assert.assertEquals("Your concession for course SOFTENG 754 has been declined.", notification );
    }

    @Test
    public void testGetEnrollmentStatusNotificationWaitingListEnrollment() {
        Student student = Mockito.spy(new Student());
        Course course = Mockito.mock(Course.class);
        Mockito.when(course.getCname()).thenReturn("SOFTENG 754");
        Mockito.doReturn(ConcessionStatusEnum.approved).when(student).getConcessionStatus(course);
        Mockito.doReturn(VirtualListEnum.enrolled_list).when(student).getVirtualStatus(course);
        NotificationEvent notificationEvent = student.updateVirtualList(course);
        String notification = notificationEvent.notifyChange();
        Assert.assertEquals("You have been moved off the waiting list for SOFTENG 754 and are now enrolled.", notification );
    }

    @Test
    public void testDefaultEnrollmentNotificationSetting() {
        ConcessionApplication concessionApplication = Mockito.mock(ConcessionApplication.class);
        Mockito.when(concessionApplication.getStudent()).thenReturn(student);
        Mockito.when(concessionApplication.getCourse()).thenReturn(course);
        NotificationEvent notificationEvent = concessionHandler.approveConcession(concessionApplication);
        NotificationSettingEnum setting = notificationEvent.getNotificationSetting();
        Assert.assertEquals(NotificationSettingEnum.email, setting);
    }


    @Test
    public void testChangeEnrollmentNotificationSettingsMobile() {
        String cid = "24";
        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        Mockito.when(db.getConcessionApplication(cid)).thenReturn(concessionApp);
        Mockito.when(concessionApp.getStudent()).thenReturn(student);
        Mockito.when(concessionApp.getCourse()).thenReturn(course);
        NotificationEvent notificationEvent = concessionHandler.approveConcession(cid);
        notificationEvent.changeNotificationSetting(NotificationSettingEnum.mobile);
        NotificationSettingEnum newSetting = notificationEvent.getNotificationSetting();
        Assert.assertEquals(NotificationSettingEnum.mobile, newSetting);
    }

    @Test
    public void testChangeEnrollmentNotificationSettingsNone() {
        String cid = "24";
        ConcessionApplication concessionApp = Mockito.mock(ConcessionApplication.class);
        Mockito.when(db.getConcessionApplication(cid)).thenReturn(concessionApp);
        Mockito.when(concessionApp.getStudent()).thenReturn(student);
        Mockito.when(concessionApp.getCourse()).thenReturn(course);
        NotificationEvent notificationEvent = concessionHandler.approveConcession(cid);
        notificationEvent.changeNotificationSetting(NotificationSettingEnum.none);
        NotificationSettingEnum newSetting = notificationEvent.getNotificationSetting();
        String notification = notificationEvent.notifyChange();
        Assert.assertEquals(null, notification);
        Assert.assertEquals(NotificationSettingEnum.none, newSetting);
    }



}
