package auth.rest.model;


public class LoginResponse {
    public final String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    @SuppressWarnings("unused") // for Jackson
    private LoginResponse() {
        this.token = null;
    }
}
