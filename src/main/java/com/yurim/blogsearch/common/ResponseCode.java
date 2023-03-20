package com.yurim.blogsearch.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    REQUEST_SUCCESS("2000", "요청 성공하였습니다.", HttpStatus.OK),


    /** 사용자 요청 오류 */
    REQUEST_UNDEFINED_ERROR("4000", "잘못 된 요청입니다.", HttpStatus.BAD_REQUEST),
    REQUEST_BINDING_ERROR("4200", "지원하지 않는 요청입니다.", HttpStatus.BAD_REQUEST),
    REQUEST_CONTENT_TYPE_ERROR("4201", "지원하지 않는 요청입니다.", HttpStatus.BAD_REQUEST),
    REQUEST_PAYLOAD_NOT_READABLE("4202", "잘못 된 요청입니다.", HttpStatus.BAD_REQUEST),

    REQUEST_PARAMETER_MISSING("4300", "필수 값이 누락되었습니다.", HttpStatus.BAD_REQUEST),
    REQUEST_PARAMETER_INVALID("4301", "요청 값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    REQUEST_PARAMETER_NOT_ALLOWED("4302", "요청 값이 허용되지 않습니다.", HttpStatus.BAD_REQUEST),
    REQUEST_RESOURCE_NOT_FOUND("4400", "요청 대상을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    REQUEST_RESOURCE_CONFLICT("4401", "요청 대상이 중복 되었습니다.", HttpStatus.CONFLICT),




    /** 서버 내부 오류 */
    INTERNAL_UNDEFINED_ERROR("5000", "알 수 없는 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_AUTHENTICATION_ERROR("5001", "허가 되지 않은 요청입니다.", HttpStatus.BAD_REQUEST),

    INTERNAL_DB_CONNECTION_FAIL("5100", "DB 연결 실패하였습니다.", HttpStatus.SERVICE_UNAVAILABLE),
    INTERNAL_DB_CONNECTION_TIMEOUT("5101", "DB 연결이 오래 걸립니다.", HttpStatus.SERVICE_UNAVAILABLE),

    INTERNAL_MW_CONNECTION_FAIL("5200", "MW 연결 실패하였습니다.", HttpStatus.SERVICE_UNAVAILABLE),
    INTERNAL_MW_EXECUTE_ERROR("5201", "MW의 실행 중 문제가 발생하였습니다.", HttpStatus.SERVICE_UNAVAILABLE),




    /** 외부 API 연동 오류 */
    CLIENT_UNDEFINED_ERROR("6000", "알 수 없는 오류가 발생하였습니다.", HttpStatus.BAD_REQUEST),
    CLIENT_AUTHENTICATION_ERROR("6001", "허가 되지 않은 요청입니다.", HttpStatus.BAD_REQUEST),
    CLIENT_TARGET_NOT_FOUND("6003", "외부 요청 대상이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    CLIENT_NOT_SUPPORTED("6004", "지원이 되지 않는 외부 요청입니다.", HttpStatus.BAD_REQUEST),
    CLIENT_BAD_REQUEST("6005", "요청 값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),

    CLIENT_CONNECTION_FAIL("6100", "연동 API 연결 실패하였습니다.", HttpStatus.SERVICE_UNAVAILABLE),

    ;
    private String code;
    private String reason;
    private HttpStatus httpStatus;

}
