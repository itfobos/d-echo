package auth.password;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

class SecretHasher {

    private static final Logger logger = LoggerFactory.getLogger(SecretHasher.class);

    private final int iterations;
    private final int keyLength;
    private final SecretKeyFactory keyFactory;

    SecretHasher(String key, int iterations, int keyLength) {
        this.iterations = iterations;
        this.keyLength = keyLength;
        try {
            this.keyFactory = SecretKeyFactory.getInstance(key);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Algorithm not found", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    String encrypt(String password, String salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(StandardCharsets.UTF_8), iterations, keyLength);
            SecretKey secretKey = keyFactory.generateSecret(spec);
            return toHex(secretKey.getEncoded());
        } catch (InvalidKeySpecException e) {
            logger.error("Encryption error", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    private String toHex(byte[] array) {
        String hex = new BigInteger(1, array).toString(16);

        int paddingLength = (array.length * 2) - hex.length();

        return "0".repeat(Math.max(0, paddingLength)) + hex;
    }

}

