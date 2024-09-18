package springreact.practicerestapi.Exceptions;

public class BadEmail {

    private String UsedEmail;

    public String getUsedEmail() {
        return UsedEmail;
    }

    public void setUsedEmail(String usedEmail) {
        UsedEmail = usedEmail;
    }

    public BadEmail(String usedEmail) {
        UsedEmail = usedEmail;
    }
}
