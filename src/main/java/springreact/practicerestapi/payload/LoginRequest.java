package springreact.practicerestapi.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "User should not be blank")
    private String username;
    @NotBlank(message = "password should not be blank")
    private  String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
