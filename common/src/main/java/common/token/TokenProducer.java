package common.token;

import common.model.UserProfile;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

import static common.utils.Strings.requireNotBlank;

public class TokenProducer {
    private PrivateKey privateKey;

    @Value("${jwt.token.expiration.claim.name}")
    private String expirationClaimName;

    @Value("${jwt.token.expiration.period.minutes}")
    private int expirationPeriodMinutes;

    @Value("${jwt.token.expiration.timestampZone}")
    private ZoneId expirationZoneId;

    @Value("${jwt.signature.private.key}")
    private String privateKeyContent;


    TokenProducer() {
    }

    @PostConstruct
    private void init() {
        requireNotBlank(expirationClaimName);
        requireNotBlank(privateKeyContent);
        Objects.requireNonNull(expirationZoneId);
        if (expirationPeriodMinutes <= 0) throw new IllegalArgumentException("Expiration period should be positive");

        String privateKeyBody = privateKeyContent.replaceAll("\\n", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "");

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyBody));
            privateKey = keyFactory.generatePrivate(keySpecPKCS8);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public String produce(UserProfile userProfile) {
        Map<String, Object> claims = new UserProfileClaimWrapper(userProfile).getClaims();

        long expirationTimestamp = ZonedDateTime.now(expirationZoneId)
                .plusMinutes(expirationPeriodMinutes)
                .toEpochSecond();

        claims.put(expirationClaimName, expirationTimestamp);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .setClaims(claims)
                .compact();
    }
}
