package com.sptek.webfw.common.exceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sptek.webfw.common.code.ErrorCodeEnum;
import com.sptek.webfw.common.responseDto.ApiErrorResponseDto;
import com.sptek.webfw.common.exception.ServiceException;
import com.sptek.webfw.common.exception.DuplicatedRequestException;
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
    public ResponseEntity<ApiErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException : ", ex);

        final ApiErrorResponseDto apiErrorResponseDto = ApiErrorResponseDto.of(ErrorCodeEnum.NOT_VALID_ERROR, ex.getMessage(), ex.getBindingResult());
        return new ResponseEntity<>(apiErrorResponseDto, ErrorCodeEnum.NOT_VALID_ERROR.getHttpStatusCode());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiErrorResponseDto> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        log.error("MissingRequestHeaderException : ", ex);

        final ApiErrorResponseDto apiErrorResponseDto = ApiErrorResponseDto.of(ErrorCodeEnum.REQUEST_BODY_MISSING_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponseDto, ErrorCodeEnum.REQUEST_BODY_MISSING_ERROR.getHttpStatusCode());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException : ", ex);

        final ApiErrorResponseDto apiErrorResponseDto = ApiErrorResponseDto.of(ErrorCodeEnum.REQUEST_BODY_MISSING_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponseDto, ErrorCodeEnum.REQUEST_BODY_MISSING_ERROR.getHttpStatusCode());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponseDto> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error("MissingServletRequestParameterException : ", ex);

        final ApiErrorResponseDto apiErrorResponseDto = ApiErrorResponseDto.of(ErrorCodeEnum.MISSING_REQUEST_PARAMETER_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponseDto, ErrorCodeEnum.MISSING_REQUEST_PARAMETER_ERROR.getHttpStatusCode());
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<ApiErrorResponseDto> handleHttpClientErrorException(HttpClientErrorException ex) {
        log.error("HttpClientErrorException : ", ex);

        final ApiErrorResponseDto apiErrorResponseDto = ApiErrorResponseDto.of(ErrorCodeEnum.BAD_REQUEST_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponseDto, ErrorCodeEnum.BAD_REQUEST_ERROR.getHttpStatusCode());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiErrorResponseDto> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error("NoHandlerFoundException : ", ex);

        final ApiErrorResponseDto apiErrorResponseDto = ApiErrorResponseDto.of(ErrorCodeEnum.NOT_FOUND_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponseDto, ErrorCodeEnum.NOT_FOUND_ERROR.getHttpStatusCode());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiErrorResponseDto> handleNullPointerException(NullPointerException ex) {
        log.error("NullPointerException : ", ex);

        final ApiErrorResponseDto apiErrorResponseDto = ApiErrorResponseDto.of(ErrorCodeEnum.NULL_POINT_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponseDto, ErrorCodeEnum.NULL_POINT_ERROR.getHttpStatusCode());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiErrorResponseDto> handleIOException(IOException ex) {
        log.error("IOException : ", ex);

        final ApiErrorResponseDto apiErrorResponseDto = ApiErrorResponseDto.of(ErrorCodeEnum.IO_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponseDto, ErrorCodeEnum.IO_ERROR.getHttpStatusCode());
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ApiErrorResponseDto> handleJsonParseException(JsonParseException ex) {
        log.error("JsonParseException : ", ex);

        final ApiErrorResponseDto apiErrorResponseDto = ApiErrorResponseDto.of(ErrorCodeEnum.JSON_PARSE_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponseDto, ErrorCodeEnum.JSON_PARSE_ERROR.getHttpStatusCode());
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ApiErrorResponseDto> handleJsonProcessingException(JsonProcessingException ex) {
        log.error("JsonProcessingException : ", ex);

        final ApiErrorResponseDto apiErrorResponseDto = ApiErrorResponseDto.of(ErrorCodeEnum.REQUEST_BODY_MISSING_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponseDto, ErrorCodeEnum.REQUEST_BODY_MISSING_ERROR.getHttpStatusCode());
    }

    //개발자가 의도적으로 생성하는 Exception는 ServiceException로 생성하며 해당 핸들러에서 처리 됨
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiErrorResponseDto> handleServiceException(ServiceException ex) {
        log.error("ServiceException로 : ", ex);

        final ApiErrorResponseDto apiErrorResponseDto = ApiErrorResponseDto.of(ex.getErrorCodeEnum(), ex.getMessage());
        return new ResponseEntity<>(apiErrorResponseDto, ex.getErrorCodeEnum().getHttpStatusCode());
    }

    //어전 request 응답하기 전 동일한 request 중복 요청했을때 DuplicateRequestPreventAspect 에서 발생시킴
    @ExceptionHandler(DuplicatedRequestException.class)
    public ResponseEntity<ApiErrorResponseDto> handleDuplicatedRequestException(DuplicatedRequestException ex) {
        log.error("DuplicatedRequestException : ", ex);

        final ApiErrorResponseDto apiErrorResponseDto = ApiErrorResponseDto.of(ex.getErrorCodeEnum(), ex.getMessage());
        return new ResponseEntity<>(apiErrorResponseDto, ex.getErrorCodeEnum().getHttpStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponseDto> handleUnExpectedException(Exception ex) {
        log.error("UnExpectedException : ", ex);

        final ApiErrorResponseDto apiErrorResponseDto = ApiErrorResponseDto.of(ErrorCodeEnum.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiErrorResponseDto, ErrorCodeEnum.INTERNAL_SERVER_ERROR.getHttpStatusCode());
    }
}
