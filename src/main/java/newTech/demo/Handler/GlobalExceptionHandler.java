package newTech.demo.Handler;

import newTech.demo.DTO.response;
import newTech.demo.DTO.returnCode;
import newTech.demo.Handler.Exception.DataNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public response<Object> BaseException(Exception e) {
        return new response<>(returnCode.UnknownError, e.getClass().getName());
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public response<String> InvalidArgument(ArrayIndexOutOfBoundsException e) {
        return new response<>(returnCode.InvalidArgument, e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public response<String> InvalidMethod(HttpRequestMethodNotSupportedException e) {
        return new response<>(returnCode.InvalidMethod, e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public response<Object> AccessDenied(AccessDeniedException e) {
        return new response<>(returnCode.NoPrivileges, null);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public response<Object> DataNotFound(DataNotFoundException e) {
        return new response<>(returnCode.UnknownRecord, e.getMessage());
    }
}
