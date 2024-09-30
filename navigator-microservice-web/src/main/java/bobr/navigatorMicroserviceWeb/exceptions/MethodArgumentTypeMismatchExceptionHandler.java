package bobr.navigatorMicroserviceWeb.exceptions;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class MethodArgumentTypeMismatchExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Violation> handleExceptions(MethodArgumentTypeMismatchException e) {
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_XML)
                .body(new Violation(e.getName(), e.getMessage()));
    }

}
