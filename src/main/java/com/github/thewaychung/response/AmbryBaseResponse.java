package com.github.thewaychung.response;

/**
 * @author Zhong.Zewei Create on 2016.06.12.
 */
public class AmbryBaseResponse {

    private int code;
    private String status;
    private String message;

    public AmbryBaseResponse() {
        code = 400;
        status = "";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
