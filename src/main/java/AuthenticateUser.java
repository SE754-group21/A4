public class AuthenticateUser {
    private SSOMock sso;

    public AuthenticateUser(SSOMock sso){
        this.sso = sso;
    }

    public String verifyLogin(String username, String password){
        if(sso.verify(username, password).equals("Success")){
            return "Login successful";
        }
        if(sso.verify(username, password).equals("Unsuccessful")){
            return "Login unsuccessful";
        }
        return "";
    }
    public boolean alreadyLoggedIn(){
        if(sso.loggedIn() == "Logged in"){
            return true;
        }
        return false;
    }
}
