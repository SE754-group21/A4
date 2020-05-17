import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class SSOIntegrationTest {

    @Category(UnitTests.class)
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
}
