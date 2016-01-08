package scheduleserver.bins;

public class Login {

    private boolean correct;
    private String email;
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getEmail() {

        return email;
    }

    public String getPassword() {
        return password;
    }

    public Login(boolean correct) {
        this.correct = correct;
    }

    public boolean isCorrect()
    {
        return correct;
    }
}
