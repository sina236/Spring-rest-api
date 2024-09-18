package springreact.practicerestapi.services;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springreact.practicerestapi.domain.UserAccount;
import springreact.practicerestapi.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepo userRepo;

    public CustomUserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username ) throws UsernameNotFoundException {
        UserAccount userAccount = userRepo.findByUsername(username);
        if(username==null) throw new UsernameNotFoundException("User not found ");
        return userAccount;
    }

    @Transactional
    public UserAccount loadUserById(Long id){
        UserAccount userAccount =userRepo.getById(id);
        if(userAccount==null) throw new UsernameNotFoundException("User not found ");
        return userAccount;
    }
}
