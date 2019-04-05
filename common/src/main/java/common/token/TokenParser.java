package common.token;

import common.model.UserProfile;
import common.utils.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

import static common.utils.Strings.requireNotBlank;

public class TokenParser {

    private static final Logger logger = LoggerFactory.getLogger(TokenParser.class);

    private RSAPublicKey publicKey;

    @Value("${jwt.token.expiration.claim.name}")
    private String expirationClaimName;

    @Value("${jwt.token.expiration.period.minutes}")
    private int expirationPeriodMinutes;

    @Value("${jwt.token.expiration.timestampZone}")
    private ZoneId expirationZoneId;

    @Value("${jwt.signature.public.key}")
    private String publicKeyContent;

    TokenParser() {
    }

    @PostConstruct
    private void init() {
        requireNotBlank(expirationClaimName);
        requireNotBlank(publicKeyContent);
        Objects.requireNonNull(expirationZoneId);
        if (expirationPeriodMinutes <= 0) throw new IllegalArgumentException("Expiration period should be positive");

        String publicKeyBody = publicKeyContent.replaceAll("\\n", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "");

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyBody));
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpecX509);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<UserProfile> getProfileIfNotExpired(String token) {
        Optional<Claims> claimsOptional = Optional.ofNullable(token)
                .filter(Strings::notNullOrBlank)
                .map(String::strip)
                .map(tkn -> {
                    try {
                        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
                    } catch (Exception e) {
                        return null;
                    }
                });

        if (claimsOptional.isEmpty()) {
            logger.warn("Passed token is nullable/blank/incorrect of empty: '{}'", token);
            return Optional.empty();
        }

        return claimsOptional
                .filter(claims -> {
                    Long expirationTimestampSec = claims.get(expirationClaimName, Long.class);
                    return expirationTimestampSec != null &&
                            ZonedDateTime.now(expirationZoneId).toEpochSecond() < expirationTimestampSec;
                })
                .map(UserProfileClaimWrapper::fromClaims);
    }

}
