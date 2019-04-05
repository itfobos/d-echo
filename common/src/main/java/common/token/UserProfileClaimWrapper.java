package common.token;

import common.model.UserProfile;
import io.jsonwebtoken.Claims;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

class UserProfileClaimWrapper extends UserProfile {
    private static final String USER_ID = "userId";
    private static final String LOGIN = "login";
    private static final String ROLES = "roles";

    UserProfileClaimWrapper(UserProfile srcProfile) {
        super(srcProfile.id, srcProfile.login, srcProfile.roles);
    }

    private UserProfileClaimWrapper(Long id, String login, String rolesAsString) {
        super(id, login, rolesAsString);
    }

    Map<String, Object> getClaims() {
        Map<String, Object> result = new HashMap<>(4);
        result.put(USER_ID, id);
        result.put(LOGIN, login);
        result.put(ROLES, this.getRolesAsString());

        return result;
    }

    public static UserProfileClaimWrapper fromClaims(Claims claims) {
        return new UserProfileClaimWrapper(
                requireNonNull(claims.get(USER_ID, Long.class)),
                requireNonNull(claims.get(LOGIN, String.class)),
                claims.get(ROLES, String.class)
        );
    }
}
