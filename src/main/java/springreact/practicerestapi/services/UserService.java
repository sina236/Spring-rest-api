package springreact.practicerestapi.services;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import springreact.practicerestapi.Exceptions.DuplicateEmail;
import springreact.practicerestapi.domain.UserAccount;
import springreact.practicerestapi.repositories.UserRepo;

@Service
public class UserService {
    private UserRepo userRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserAccount saveUser (UserAccount newUser){
        try {
            newUser.setPassword(bCryptPasswordEncoder.encode((newUser.getPassword())));
            newUser.setConfirmPassword(newUser.getConfirmPassword());
            newUser.setUsername(newUser.getUsername());
            return userRepo.save(newUser);
        }catch (Exception e){
            throw new DuplicateEmail("This email already exist in our database");
        }

    }
}
