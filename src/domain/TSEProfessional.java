package domain;

public class TSEProfessional {
    public final String user;
    public final String password;

    public TSEProfessional(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public boolean login(String password) {
        return this.password.equals(password);
    }
}
