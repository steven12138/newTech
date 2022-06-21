package newTech.demo.Handler.Exception;

import newTech.demo.DTO.returnCode;

public class DataNotFoundException extends RuntimeException {
    String message;
    returnCode code;

    public DataNotFoundException(String message) {
        this.message = message;
        this.code = returnCode.UnknownRecord;
    }
}
