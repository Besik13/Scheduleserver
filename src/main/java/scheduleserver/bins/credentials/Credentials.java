package scheduleserver.bins.credentials;

public class Credentials {
    private String email;
    private String password;

    public Credentials() {

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {

        return email;
    }

    public String getPassword() {
        return password;
    }
}
