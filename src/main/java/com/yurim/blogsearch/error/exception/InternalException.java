package com.yurim.blogsearch.error.exception;

import com.yurim.blogsearch.common.ResponseCode;

public class InternalException extends BaseException {

    private static final long serialVersionUID = 81963875134266622L;

    public InternalException(String message) {
        super(message);
    }

    public InternalException(ResponseCode code) {
        super(code);
    }

    public InternalException(ResponseCode code, String message) {
        super(code, message);
    }

    public InternalException(ResponseCode code, Throwable cause) {
        super(code, cause);
    }

    public InternalException(ResponseCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}

