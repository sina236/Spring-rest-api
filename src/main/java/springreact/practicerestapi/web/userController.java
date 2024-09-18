package springreact.practicerestapi.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springreact.practicerestapi.Validator.UserValidator;
import springreact.practicerestapi.domain.UserAccount;
import springreact.practicerestapi.payload.JWTLogin;
import springreact.practicerestapi.payload.LoginRequest;
import springreact.practicerestapi.security.JwtTokenProvider;
import springreact.practicerestapi.services.MapvalidationError;
import springreact.practicerestapi.services.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class userController {

    private final MapvalidationError mapvalidationError;
    private final UserService userService;
    private final UserValidator userValidator;
    private final JwtTokenProvider  jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public userController(MapvalidationError mapvalidationError, UserService userService, UserValidator userValidator, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.mapvalidationError = mapvalidationError;
        this.userService = userService;
        this.userValidator = userValidator;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest , BindingResult result){
        System.out.println(loginRequest);
        System.out.println(47);
        ResponseEntity<?> error=mapvalidationError.MapvalidationService(result);
        if(error!=null) return error;
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt="Bearer "+jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTLogin(true,jwt));
    }
    @PostMapping("/register")
    public ResponseEntity<?> addingUser(@Valid @RequestBody UserAccount user, BindingResult result){
        userValidator.validate(user,result);
        System.out.println(user.getUsername());
        ResponseEntity<?> error=mapvalidationError.MapvalidationService(result);
        if(error!=null){
            return error;
        }
        UserAccount newUser=userService.saveUser(user);
        return  new ResponseEntity<>(newUser, HttpStatus.CREATED);

    }

}
