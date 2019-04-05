package common.token;

import common.model.UserProfile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserAuthentication implements Authentication {

    private final UserProfile userProfile;
    private final Collection<? extends GrantedAuthority> roles;
    private final String token;

    private boolean authenticated = true;

    public UserAuthentication(UserProfile userProfile, String token) {
        this.userProfile = userProfile;
        this.token = token;

        roles = Arrays.stream(userProfile.roles)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public UserProfile getDetails() {
        return userProfile;
    }

    @Override
    public Long getPrincipal() {
        return userProfile.id;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return userProfile.login;
    }
}
