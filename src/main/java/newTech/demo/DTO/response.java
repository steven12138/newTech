package newTech.demo.DTO;

import lombok.Data;

@Data
public class response<T> {
    private int code;
    private String message;
    private T data;
    private long time_stamp;

    public response(returnCode c, T data) {
        time_stamp = System.currentTimeMillis();
        this.code = c.getCode();
        this.message = c.getMessage();
        this.data = data;
    }
}
