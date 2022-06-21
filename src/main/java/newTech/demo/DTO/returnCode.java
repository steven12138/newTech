package newTech.demo.DTO;

public enum returnCode {
    NoPrivileges(10001, "request forbidden"),
    UnknownError(10002, "unknown error"),
    WrongUsernameOrPassword(10003, "username or password error"),
    NotMatchedPassword(10004, "password incorrect"),

    TechPassed(10005, "Tech exam already passed"),
    PhyPassed(10006, "Phy exam already passed"),
    BothPassed(10007, "No need to sign up"),

    InvalidArgument(10008, "Argument Invalid"),
    InvalidToken(10009, "Token Invalid"),
    InvalidMethod(10010, "Invalid Method"),

    PasswordNotMatch(10011, "Password Not Match"),

    InvalidFile(10012, "File Error!"),
    InvalidFileExtension(10013, "File Extension Not Support!"),
    UnknownRecord(10014, "Record Does Not Exist"),
    InvalidData(10015, "Data Invalid, Check for duplicate sid"),

    SystemOff(10016, "System Is No Open"),

    success(10000, "success"),

    testAPI(90000, "接口测试");

    private final int code;
    private final String message;

    returnCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
