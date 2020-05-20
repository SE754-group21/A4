import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

@Category(UnitTests.class)
public class SSOIntegrationTest {
    private SSOMock sso;
    private AuthenticateUser auth;
    private String username = "ibea707";

    @Before
    public void setUp(){
        sso = Mockito.mock(SSOMock.class);
        auth = new AuthenticateUser(sso);
    }

    @Test
    public void testSuccessfulLogIn(){
        String password = "password";

        Mockito.when(sso.verify(username, password)).thenReturn("Success");

        String login = auth.verifyLogin(username, password);
        assertEquals("Login successful", login);

    }

    @Test
    public void testUnsuccessfulLogIn(){
        String password = "wrongpassword";

        Mockito.when(sso.verify(username, password)).thenReturn("Unsuccessful");

        String login = auth.verifyLogin(username, password);
        assertEquals("Login unsuccessful", login);

    }
    @Test
    public void testAlreadyLoggedIn(){
        Mockito.when(sso.loggedIn()).thenReturn("Logged in");

        Boolean login = auth.alreadyLoggedIn();
        assertTrue(login);
    }
}
