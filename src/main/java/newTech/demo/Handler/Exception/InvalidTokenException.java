package newTech.demo.Handler.Exception;

import newTech.demo.DTO.returnCode;

public class InvalidTokenException extends RuntimeException {
    String message;
    returnCode code;

    public InvalidTokenException(String message) {
        this.message = message;
        this.code = returnCode.InvalidArgument;
    }
}
