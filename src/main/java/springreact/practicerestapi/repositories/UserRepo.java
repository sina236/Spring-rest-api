package springreact.practicerestapi.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springreact.practicerestapi.domain.UserAccount;

@Repository
public interface UserRepo extends CrudRepository<UserAccount,Long> {
    UserAccount findByUsername(String username);
    UserAccount getById(Long id);
}
