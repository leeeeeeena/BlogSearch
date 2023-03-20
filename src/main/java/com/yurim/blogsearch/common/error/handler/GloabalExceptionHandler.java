package com.yurim.blogsearch.common.error.handler;


import com.yurim.blogsearch.common.CommonResponse;
import com.yurim.blogsearch.common.ResponseCode;
import com.yurim.blogsearch.common.error.exception.BaseException;
import com.yurim.blogsearch.common.error.exception.BadRequestException;
import com.yurim.blogsearch.common.error.exception.ClientRequestException;
import com.yurim.blogsearch.common.error.exception.InternalException;
import io.lettuce.core.RedisBusyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.*;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisPipelineException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;

@Slf4j
@RestControllerAdvice
public class GloabalExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Spring MVC 관련 Exception 공용 처리 (Parent의 실제 MVC Exception 처리 Method override)
     *
     * @see ResponseEntityExceptionHandler#handleException
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        log.error("Spring MVC Exception Occurred", ex);

        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ResponseCode responseCode = ResponseCode.REQUEST_UNDEFINED_ERROR;
        return new ResponseEntity<>(errorResponse(responseCode, status.getReasonPhrase()), headers, status);
    }


    /**
     * 비즈니스 로직 구현에서 발생할 수 있는 runtime exception
     */
    @ExceptionHandler(BaseException.class)
    @Nullable
    public final ResponseEntity<Object> handleBizException(BaseException ex, WebRequest request) throws Exception {
        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ResponseCode responseCode = ex.getCode();
        log.warn("Exception Occurred", ex);

        return new ResponseEntity<>(errorResponse(responseCode, ex.getMessage()), responseCode.getHttpStatus());
    }


    @ExceptionHandler(BadRequestException.class)
    @Nullable
    public final ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) throws Exception {
        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ResponseCode responseCode = ex.getCode();
        log.warn("Exception Occurred", ex); //사용자 잘못요청으로 인한건 warn level

        return new ResponseEntity<>(errorResponse(responseCode, ex.getMessage()), responseCode.getHttpStatus());
    }


    @ExceptionHandler(ClientRequestException.class)
    @Nullable
    public final ResponseEntity<Object> handleClientException(ClientRequestException ex, WebRequest request) throws Exception {
        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ResponseCode responseCode = ex.getCode();
        log.error("Exception Occurred", ex);

        return new ResponseEntity<>(errorResponse(responseCode, ex.getMessage()), responseCode.getHttpStatus());
    }


    @ExceptionHandler(InternalException.class)
    @Nullable
    public final ResponseEntity<Object> handleInternalException(InternalException ex, WebRequest request) throws Exception {
        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ResponseCode responseCode = ex.getCode();
        log.error("Exception Occurred", ex);

        return new ResponseEntity<>(errorResponse(responseCode, ex.getMessage()), responseCode.getHttpStatus());
    }


    /**
     * 외부 연동 API 호출 시 발생할 수 있는 exception
     */

//    @ExceptionHandler(FeignException.FeignClientException.Unauthorized.class)
//    @Nullable
//    public final ResponseEntity<Object> handleFeignClientUnauthorizedException(FeignException.FeignClientException.Unauthorized ex, WebRequest request) throws Exception {
//        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
//        ResponseCode responseCode = ResponseCode.CLIENT_AUTHENTICATION_ERROR;
//        log.error("Exception Occurred", ex);
//
//        return new ResponseEntity<>(errorResponse(responseCode, ex.getMessage()), responseCode.getHttpStatus());
//    }
//
//
//    @ExceptionHandler(FeignException.NotFound.class)
//    @Nullable
//    public final ResponseEntity<Object> handleFeignNotFoundException(FeignException.NotFound ex, WebRequest request) throws Exception {
//        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
//        ResponseCode responseCode = ResponseCode.CLIENT_TARGET_NOT_FOUND;
//        log.error("Exception Occurred", ex);
//
//        return new ResponseEntity<>(errorResponse(responseCode, ex.getMessage()), responseCode.getHttpStatus());
//    }
//
//
//    @ExceptionHandler(FeignException.FeignClientException.class)
//    @Nullable
//    public final ResponseEntity<Object> handleFeignClientException(FeignException.FeignClientException ex, WebRequest request) throws Exception {
//        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
//        ResponseCode responseCode = ResponseCode.CLIENT_UNDEFINED_ERROR;
//        log.error("Exception Occurred", ex);
//
//        return new ResponseEntity<>(errorResponse(responseCode, ex.getMessage()), responseCode.getHttpStatus());
//    }


    @ExceptionHandler(HttpClientErrorException.class)
    public final ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex, WebRequest request) throws Exception {
        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ResponseCode responseCode = ResponseCode.CLIENT_BAD_REQUEST;
        log.error("Exception Occurred", ex);

        return new ResponseEntity<>(errorResponse(responseCode, ex.getMessage()), responseCode.getHttpStatus());
    }



    @ExceptionHandler(ResourceAccessException.class)
    public final ResponseEntity<Object> handleResourceAccessException(ResourceAccessException ex, WebRequest request) throws Exception {
        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ResponseCode responseCode = ResponseCode.CLIENT_CONNECTION_FAIL;
        log.error("Exception Occurred", ex);

        return new ResponseEntity<>(errorResponse(responseCode, ex.getMessage()),responseCode.getHttpStatus());
    }

    /**
     * requestParam exception
     **/
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ResponseCode responseCode = ResponseCode.REQUEST_PARAMETER_MISSING;
        log.warn("Exception Occurred", ex);

        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(ex.getFieldError().getField())
                .append(" ")
                .append(ex.getFieldError().getDefaultMessage());

        return new ResponseEntity<>(errorResponse(responseCode, errorMessage.toString()), headers, responseCode.getHttpStatus());
    }


    /**
     * redis exception
     **/

    @ExceptionHandler(RedisConnectionFailureException.class)
    public final ResponseEntity<Object> handleRedisConnectionException(RedisConnectionFailureException ex, WebRequest request) throws Exception {
        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ResponseCode responseCode = ResponseCode.INTERNAL_MW_CONNECTION_FAIL;
        log.error("Exception Occurred", ex);

        return new ResponseEntity<>(errorResponse(responseCode, ex.getMessage()),responseCode.getHttpStatus());
    }


    @ExceptionHandler(RedisPipelineException.class)
    public final ResponseEntity<Object> handleRedisPipelineException(RedisPipelineException ex, WebRequest request) throws Exception {
        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ResponseCode responseCode = ResponseCode.INTERNAL_MW_EXECUTE_ERROR;
        log.error("Exception Occurred", ex);

        return new ResponseEntity<>(errorResponse(responseCode, ex.getMessage()), responseCode.getHttpStatus());
    }


    @ExceptionHandler(RedisBusyException.class)
    public final ResponseEntity<Object> handleRedisBusyException(RedisBusyException ex, WebRequest request) throws Exception {
        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ResponseCode responseCode = ResponseCode.INTERNAL_MW_EXECUTE_ERROR;

        return new ResponseEntity<>(errorResponse(responseCode, ex.getMessage()), responseCode.getHttpStatus());
    }



    /**
     * DAO SQL Exception Error 처리
     */
    @ExceptionHandler(DataAccessException.class)
    @Nullable
    public final ResponseEntity<Object> handleSQLException(Exception ex, WebRequest request) throws Exception {
        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ResponseCode responseCode = ResponseCode.INTERNAL_UNDEFINED_ERROR; //Default Response Code

        if (ex instanceof DuplicateKeyException) {
            responseCode = ResponseCode.REQUEST_RESOURCE_CONFLICT;
        } else if (ex instanceof TypeMismatchDataAccessException) { //Type 안 맞음
            responseCode = ResponseCode.REQUEST_PARAMETER_INVALID;
        } else if (ex instanceof DataIntegrityViolationException) { //FK 이슈 , Size 이슈
            responseCode = ResponseCode.REQUEST_PARAMETER_INVALID;
        } else if (ex instanceof QueryTimeoutException) { // Query Timeout
            responseCode = ResponseCode.INTERNAL_DB_CONNECTION_TIMEOUT;
        } else if (ex instanceof DataAccessResourceFailureException) { //DB 접근 불가
            responseCode = ResponseCode.INTERNAL_DB_CONNECTION_FAIL;
        }

        log.error("DAO SQLException Occurred", ex);
        return new ResponseEntity<>(errorResponse(responseCode), responseCode.getHttpStatus());
    }

    /**
     * Servlet 관련 Exception 처리
     */
    @ExceptionHandler(ServletException.class)
    protected ResponseEntity<Object> handleNestedServletException(Exception ex, WebRequest request) {
        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ResponseCode responseCode = ResponseCode.REQUEST_UNDEFINED_ERROR; //Default Response Code

        if (ex instanceof ServletRequestBindingException) {
            responseCode = ResponseCode.REQUEST_BINDING_ERROR;
        } else if (ex instanceof HttpMediaTypeException) {
            responseCode = ResponseCode.REQUEST_CONTENT_TYPE_ERROR;
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            responseCode = ResponseCode.REQUEST_BINDING_ERROR;
        } else if (ex instanceof MissingServletRequestPartException) {
            responseCode = ResponseCode.REQUEST_PAYLOAD_NOT_READABLE;
        } else if (ex instanceof NoHandlerFoundException) {
            responseCode = ResponseCode.REQUEST_BINDING_ERROR;
        }

        log.error("Servlet Exception", ex);
        return new ResponseEntity<>(errorResponse(responseCode), responseCode.getHttpStatus());
    }

    /**
     * 그 외 예외 발생 공통 처리
     */
    @ExceptionHandler(Exception.class)
    @Nullable
    public final ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request) throws Exception {
        request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        ResponseCode responseCode = ResponseCode.INTERNAL_UNDEFINED_ERROR;
        HttpHeaders headers = new HttpHeaders();

        log.error("Unknown Internal Exception Occurred", ex);
        return new ResponseEntity<>(errorResponse(responseCode), headers, responseCode.getHttpStatus());
    }


    private CommonResponse errorResponse(ResponseCode responseCode) {
        return errorResponse(responseCode, null);
    }

    private CommonResponse errorResponse(ResponseCode responseCode, String description) {
        CommonResponse errorResponse = CommonResponse.of(description, responseCode);
        return errorResponse;
    }
}
