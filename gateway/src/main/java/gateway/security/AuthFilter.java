package gateway.security;


import common.token.TokenParser;
import common.token.UserAuthentication;
import common.utils.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

class AuthFilter extends GenericFilterBean {
    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    private final TokenParser tokenParser;
    private final String headerName;
    private final String cookieName;

    AuthFilter(TokenParser tokenParser, String headerName, String cookieName) {
        this.tokenParser = tokenParser;
        this.headerName = headerName;
        this.cookieName = cookieName;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        Optional<String> tokenOptional = getAuthTokenFromHeader(httpRequest).or(() -> getAuthTokenFromCookie(httpRequest));

        //noinspection OptionalGetWithoutIsPresent
        tokenOptional.flatMap(tokenParser::getProfileIfNotExpired)
                .map(profile -> new UserAuthentication(profile, tokenOptional.get()))
                .ifPresentOrElse(
                        userAuthentication -> {
                            logger.debug("Authenticated as {} ", userAuthentication.getName());
                            SecurityContextHolder.getContext().setAuthentication(userAuthentication);
                        },
                        () -> logger.debug("No user is authenticated.")
                );

        // do secured processing
        chain.doFilter(request, response);

        //we need the cleanup in case when "event loop" server is used, for example Netty
        //Because, in the server all the requests are processed in a same thread.
        //Zuul 2 is based at Netty server
        SecurityContextHolder.getContext().setAuthentication(null);
        logger.debug("Cleaned up the authentication in the thread");
    }


    private Optional<String> getAuthTokenFromHeader(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(headerName))
                .filter(Strings::notNullOrBlank)
                .map(token -> token.replace("Bearer", ""));
    }

    private Optional<String> getAuthTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) return Optional.empty();

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .map(Cookie::getValue);
    }
}
