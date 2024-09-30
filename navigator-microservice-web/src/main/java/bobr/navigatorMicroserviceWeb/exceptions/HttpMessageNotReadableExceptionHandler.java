package bobr.navigatorMicroserviceWeb.exceptions;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class HttpMessageNotReadableExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Violation> handleExceptions(HttpMessageNotReadableException e) {
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_XML)
                .body(generateViolationFromException(e));
    }

    private Violation generateViolationFromException(HttpMessageNotReadableException e) {
        return new Violation(extractFieldNameFromException(e), e.getMessage());
    }

    private static String extractFieldNameFromException(HttpMessageNotReadableException e) {
        String regex = "bobr\\.routeMicroservice\\.[^\"]+\\[\"([^\"]+)\"\\]";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(e.getMostSpecificCause().toString());

        String lastMatch = null;

        while (matcher.find())
            lastMatch = matcher.group(1);

        return lastMatch;
    }

}
