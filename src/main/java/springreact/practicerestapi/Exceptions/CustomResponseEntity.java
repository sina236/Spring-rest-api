package springreact.practicerestapi.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntity extends ResponseEntityExceptionHandler {


    @ExceptionHandler
    public final ResponseEntity<Object> handleprojectexception(ProjectIdException ex, WebRequest request){
        ProjectIdResponse projectIdException= new ProjectIdResponse(ex.getMessage());
        return new ResponseEntity<>(projectIdException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException ex, WebRequest request){
        ProjectNotFoundRsponse projectIdException= new ProjectNotFoundRsponse(ex.getMessage());
        return new ResponseEntity<>(projectIdException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleBadEmail(DuplicateEmail ex, WebRequest request){
        BadEmail badEmail=new BadEmail(ex.getMessage());
        return  new ResponseEntity<>(badEmail,HttpStatus.BAD_REQUEST);
    }

}
