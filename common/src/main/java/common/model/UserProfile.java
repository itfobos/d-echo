package common.model;

import java.util.Arrays;

public class UserProfile {
    private static final String ROLES_DELIMITER = ",";

    public final Long id;
    public final String login;
    public final String[] roles;

    public UserProfile(Long id, String login, String rolesAsString) {
        this.id = id;
        this.login = login;
        this.roles = Arrays.stream(rolesAsString.split(ROLES_DELIMITER))
                .map(String::strip)
                .filter(role -> !role.isBlank())
                .toArray(String[]::new);
    }

    protected String getRolesAsString() {
        return String.join(",", this.roles);
    }

    @SuppressWarnings("unused") // for Jackson
    private UserProfile() {
        this.id = null;
        this.login = null;
        this.roles = null;
    }

    protected UserProfile(Long id, String login, String[] roles) {
        this.id = id;
        this.login = login;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", roles=" + Arrays.toString(roles) +
                '}';
    }
}
