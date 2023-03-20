package com.yurim.blogsearch.common.error;

import com.yurim.blogsearch.common.ResponseCode;
import com.yurim.blogsearch.common.error.exception.ClientRequestException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    Environment env;

    @Autowired
    public FeignErrorDecoder(Environment env) {
        this.env = env;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return new ClientRequestException(ResponseCode.CLIENT_BAD_REQUEST,response.reason());
            case 401:
                return new ClientRequestException(ResponseCode.CLIENT_AUTHENTICATION_ERROR,response.reason());
            case 404:
                new ClientRequestException(ResponseCode.CLIENT_TARGET_NOT_FOUND,response.reason());
            case 405:
                new ClientRequestException(ResponseCode.CLIENT_NOT_SUPPORTED,response.reason());
            default:
                return new ClientRequestException(ResponseCode.CLIENT_UNDEFINED_ERROR,response.reason());
        }
    }
}
