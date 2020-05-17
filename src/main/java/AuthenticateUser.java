public class AuthenticateUser {
    private SSOMock sso;

    public AuthenticateUser(SSOMock sso){
        this.sso = sso;
    }

    public String verifyLogin(String username, String password){
        if(sso.verify(username, password).equals("Success")){
            return "Login successful";
        }
        return "";
    }
}
