public class AuthenticateUser {
    public String verifyLogin(String username, String password){
        SSOMock sso = new SSOMock();
        if (sso.verify(username, password) == true){
            return "Login successful";
        }
        return "";
    }
}
