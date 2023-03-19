package com.yurim.blogsearch.common.error.exception;


import com.yurim.blogsearch.common.ResponseCode;

public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = 7977074515633412631L;

    private ResponseCode code;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(ResponseCode code) {
        super(code.getReason());
        this.code = code;
    }

    public BaseException(ResponseCode code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(ResponseCode code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public BaseException(ResponseCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ResponseCode getCode() {
        return code;
    }
}