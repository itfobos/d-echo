package auth.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountEntityRepository extends CrudRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByLogin(String login);
}
