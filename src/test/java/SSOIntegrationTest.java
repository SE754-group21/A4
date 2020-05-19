import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@Category(UnitTests.class)
public class SSOIntegrationTest {

    @Test
    public void testSuccessfulLogIn(){
        SSOMock sso = Mockito.mock(SSOMock.class);
        AuthenticateUser auth = new AuthenticateUser(sso);
        String username = "ibea707";
        String password = "password";

        Mockito.when(sso.verify(username, password)).thenReturn("Success");

        String login = auth.verifyLogin(username, password);
        assertEquals("Login successful", login);

    }

    @Test
    public void testUnsuccessfulLogIn(){
        SSOMock sso = Mockito.mock(SSOMock.class);
        AuthenticateUser auth = new AuthenticateUser(sso);
        String username = "ibea707";
        String password = "wrongpassword";

        Mockito.when(sso.verify(username, password)).thenReturn("Unsuccessful");

        String login = auth.verifyLogin(username, password);
        assertEquals("Login unsuccessful", login);

    }
    @Test
    public void testAlreadyLoggedIn(){
        SSOMock sso = Mockito.mock(SSOMock.class);
        AuthenticateUser auth = new AuthenticateUser(sso);

        Mockito.when(sso.loggedIn()).thenReturn("Logged in");

        Boolean login = auth.alreadyLoggedIn();
        assertTrue(login);
    }
}
