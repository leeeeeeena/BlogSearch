
package com.yurim.blogsearch.error.exception;

import com.yurim.blogsearch.common.ResponseCode;

public class ClientRequestException extends BaseException {

    private static final long serialVersionUID = 81963875134266622L;

    public ClientRequestException(String message) {
        super(message);
    }

    public ClientRequestException(ResponseCode code) {
        super(code);
    }

    public ClientRequestException(ResponseCode code, String message) {
        super(code, message);
    }

    public ClientRequestException(ResponseCode code, Throwable cause) {
        super(code, cause);
    }

    public ClientRequestException(ResponseCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
