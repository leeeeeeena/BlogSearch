
package com.yurim.blogsearch.common.error.exception;


import com.yurim.blogsearch.common.ResponseCode;

public class BadRequestException extends BaseException {

    private static final long serialVersionUID = 81963875134266622L;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(ResponseCode code) {
        super(code);
    }

    public BadRequestException(ResponseCode code, String message) {
        super(code, message);
    }

    public BadRequestException(ResponseCode code, Throwable cause) {
        super(code, cause);
    }

    public BadRequestException(ResponseCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
