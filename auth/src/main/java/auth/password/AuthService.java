package auth.password;

import auth.common.UserProfile;
import auth.persistence.AccountEntity;
import auth.persistence.AccountEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AccountEntityRepository accountEntityRepository;

    private final PasswordEncoder passwordEncoder = new PasswordEncoder();

    public Optional<UserProfile> authenticate(String login, String password) {
        Optional<AccountEntity> accEntityOptional = accountEntityRepository.findByLogin(login);

        return accEntityOptional
                .filter(
                        accEntity -> passwordEncoder.calculatePassword(accEntity.id, password).equals(accEntity.password)
                )
                .map(accEntity -> new UserProfile(accEntity.id, accEntity.login, accEntity.roles));
    }
}
