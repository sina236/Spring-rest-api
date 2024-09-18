package springreact.practicerestapi.Validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import springreact.practicerestapi.domain.UserAccount;

import javax.validation.Valid;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UserAccount.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        UserAccount userAccount=(UserAccount) object;
        if(userAccount.getPassword().length()<6){
            errors.rejectValue("password","Length","password must be at least 6 characters");
        }
        if(!userAccount.getPassword().equals(userAccount.getConfirmPassword())){
            errors.rejectValue("confirmPassword","Match","password must match");

        }
    }
}
