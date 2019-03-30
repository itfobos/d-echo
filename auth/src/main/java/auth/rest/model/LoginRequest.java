package auth.rest.model;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    public final String login;

    @NotBlank
    public final String password;

    public LoginRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @SuppressWarnings("unused") // for Jackson
    private LoginRequest() {
        this(null, null);
    }
}
