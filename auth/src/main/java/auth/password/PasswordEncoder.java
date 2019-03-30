package auth.password;

import java.util.Objects;

class PasswordEncoder {
    private final SecretHasher secretHasher = new SecretHasher("PBKDF2WithHmacSHA512", 1000, 256);


    String calculatePassword(Long accountId, String passText) {
        Objects.requireNonNull(accountId, "accountId should'n be nullable");

        return secretHasher.encrypt(accountId + passText, accountId.toString());
    }
}
