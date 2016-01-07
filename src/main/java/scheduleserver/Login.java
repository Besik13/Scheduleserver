package scheduleserver;

public class Login {

    private boolean correct;

    public Login(boolean correct) {
        this.correct = correct;
    }

    public boolean isCorrect()
    {
        return correct;
    }
}
