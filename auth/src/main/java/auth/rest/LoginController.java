package auth.rest;

import auth.common.UserProfile;
import auth.password.AuthService;
import auth.rest.model.LoginRequest;
import auth.rest.model.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    //TODO: [#31] move in some common place
    private static final String DEFAULT_AUTH_COOKIE_NAME = "APP_AUTH";

    @Autowired
    private AuthService authService;

    @Value("${auth.usual.cookie.maxAge.sec:1800}")
    private int authCookieMaxAge;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LoginResponse> login(@Validated @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        logger.debug("Trying to log in the user: {}", loginRequest);

        Optional<UserProfile> profileOptional = authService.authenticate(loginRequest.login, loginRequest.password);


        Optional<ResponseEntity<LoginResponse>> responseOptional = profileOptional
                .map(
                        //TODO: [#31] generate token
                        userProfile -> new ResponseEntity<>(new LoginResponse("stub_token"), HttpStatus.CREATED)
                );

        responseOptional.ifPresent(responseEntity -> {
            //noinspection ConstantConditions
            Cookie cookie = new Cookie(DEFAULT_AUTH_COOKIE_NAME, responseEntity.getBody().token);
            cookie.setPath("/");
            cookie.setMaxAge(authCookieMaxAge);

            response.addCookie(cookie);

            logger.debug("User \"{}\" has been authenticated and is granted with access token", loginRequest.login);
        });


        return responseOptional.orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    @DeleteMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie(DEFAULT_AUTH_COOKIE_NAME, null);
        cookie.setPath("/");

        cookie.setMaxAge(0);
        response.addCookie(cookie);
        logger.debug("Cookie {} is removed", DEFAULT_AUTH_COOKIE_NAME);
    }
}
