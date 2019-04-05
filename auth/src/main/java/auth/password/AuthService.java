package auth.password;

import auth.persistence.AccountEntity;
import auth.persistence.AccountEntityRepository;
import common.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AccountEntityRepository accountEntityRepository;

    private final PasswordEncoder passwordEncoder = new PasswordEncoder();

    @Transactional(readOnly = true)
    public Optional<UserProfile> authenticate(String login, String password) {
        Optional<AccountEntity> accEntityOptional = accountEntityRepository.findByLogin(login);

        return accEntityOptional
                .filter(
                        accEntity -> passwordEncoder.calculatePassword(accEntity.id, password).equals(accEntity.password)
                )
                .map(accEntity -> new UserProfile(accEntity.id, accEntity.login, accEntity.roles));
    }
}
