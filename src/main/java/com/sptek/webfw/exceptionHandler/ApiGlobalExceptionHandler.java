package com.sptek.webfw.exceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sptek.webfw.code.ErrorCode;
import com.sptek.webfw.commonDto.ApiErrorResponse;
import com.sptek.webfw.exceptionHandler.exception.ServiceException;
import com.sptek.webfw.exceptionHandler.exception.DuplicatedRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;
/*
RestController 의 GlobalException (실행중 예상하지 않은 Exception에 대한 처리로 ServiceException과 비교할 수 있음) 처리를 담당함
Exception의 종류에 따라 에러코드와 Exception 메시지가 정해진다. (Exception 메시지는 실제 발생한 Exception의 메시지를 사용한다.)
최종 Response 응답까지 처리해 준다.
 */
@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ApiGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException : ", ex);

        final ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(ErrorCode.NOT_VALID_ERROR, ex.getMessage(), ex.getBindingResult());
        return new ResponseEntity<>(apiErrorResponse, ErrorCode.NOT_VALID_ERROR.getHttpStatusCode());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<ApiErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        log.error("MissingRequestHeaderException : ", ex);

        final ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(ErrorCode.REQUEST_BODY_MISSING_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, ErrorCode.REQUEST_BODY_MISSING_ERROR.getHttpStatusCode());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException : ", ex);

        final ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(ErrorCode.REQUEST_BODY_MISSING_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, ErrorCode.REQUEST_BODY_MISSING_ERROR.getHttpStatusCode());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ApiErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error("MissingServletRequestParameterException : ", ex);

        final ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(ErrorCode.MISSING_REQUEST_PARAMETER_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, ErrorCode.MISSING_REQUEST_PARAMETER_ERROR.getHttpStatusCode());
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    protected ResponseEntity<ApiErrorResponse> handleHttpClientErrorException(HttpClientErrorException ex) {
        log.error("HttpClientErrorException : ", ex);

        final ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(ErrorCode.BAD_REQUEST_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, ErrorCode.BAD_REQUEST_ERROR.getHttpStatusCode());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ApiErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error("NoHandlerFoundException : ", ex);

        final ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(ErrorCode.NOT_FOUND_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, ErrorCode.NOT_FOUND_ERROR.getHttpStatusCode());
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ApiErrorResponse> handleNullPointerException(NullPointerException ex) {
        log.error("NullPointerException : ", ex);

        final ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(ErrorCode.NULL_POINT_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, ErrorCode.NULL_POINT_ERROR.getHttpStatusCode());
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<ApiErrorResponse> handleIOException(IOException ex) {
        log.error("IOException : ", ex);

        final ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(ErrorCode.IO_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, ErrorCode.IO_ERROR.getHttpStatusCode());
    }

    @ExceptionHandler(JsonParseException.class)
    protected ResponseEntity<ApiErrorResponse> handleJsonParseException(JsonParseException ex) {
        log.error("JsonParseException : ", ex);

        final ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(ErrorCode.JSON_PARSE_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, ErrorCode.JSON_PARSE_ERROR.getHttpStatusCode());
    }

    @ExceptionHandler(JsonProcessingException.class)
    protected ResponseEntity<ApiErrorResponse> handleJsonProcessingException(JsonProcessingException ex) {
        log.error("JsonProcessingException : ", ex);

        final ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(ErrorCode.REQUEST_BODY_MISSING_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, ErrorCode.REQUEST_BODY_MISSING_ERROR.getHttpStatusCode());
    }

    //개발자가 의도적으로 생성하는 Exception는 ServiceException로 생성하며 해당 핸들러에서 처리 됨
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiErrorResponse> handleServiceException(ServiceException ex) {
        log.error("ServiceException로 : ", ex);

        final ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, ex.getErrorCode().getHttpStatusCode());
    }

    //어전 request 응답하기 전 동일한 request 중복 요청했을때 DuplicateRequestPreventAspect 에서 발생시킴
    @ExceptionHandler(DuplicatedRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicatedRequestException(DuplicatedRequestException ex) {
        log.error("DuplicatedRequestException : ", ex);

        final ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, ex.getErrorCode().getHttpStatusCode());
    }

    @ExceptionHandler(Exception.class)
    protected final ResponseEntity<ApiErrorResponse> handleUnExpectedException(Exception ex) {
        log.error("UnExpectedException : ", ex);

        final ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponse, ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatusCode());
    }
}
