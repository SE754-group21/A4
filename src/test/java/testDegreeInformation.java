import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;
public class testDegreeInformation {

    @Test
    public void testGetDegreeName() {
        String did = "SOFTENG";
        String dname = "Software Engineering";
        DegreeHandler handler = new DegreeHandler();
        Degree degree = Mockito.mock(Degree.class);
        Mockito.when(degree.getDid()).thenReturn(did);
        Mockito.when(degree.getDname()).thenReturn(dname);
        handler.addDegree(degree);
        String name = handler.getDname(did);
        assertEquals(dname, name);
    }
    

}
