import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class SSOIntegrationTest {
    @Test
    public void testSuccessfulLogIn(){
        AuthenticateUser auth = new AuthenticateUser();
        SSOMock sso = Mockito.mock(SSOMock.class);
        String username = "ibea707";
        String password = "password";

        Mockito.when(sso.verify(username, password)).thenReturn(true);

        String login = auth.verifyLogin(username, password);
        assertEquals("Login successful", login);

    }
    public void testUnsuccessfulLogIn(){
        AuthenticateUser auth = new AuthenticateUser();
        SSOMock sso = Mockito.mock(SSOMock.class);
        String username = "ibea707";
        String password = "wrongpassword";

        Mockito.when(sso.verify(username, password)).thenReturn(false);

        String login = auth.verifyLogin(username, password);
        assertEquals("Login unsuccessful", login);

    }
}
