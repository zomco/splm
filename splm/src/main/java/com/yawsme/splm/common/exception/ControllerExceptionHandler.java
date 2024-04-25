package com.yawsme.splm.common.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.yawsme.splm.common.enums.ResultCode;
import com.yawsme.splm.common.enums.ResultMessage;
import com.yawsme.splm.common.enums.ResultUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常异常处理
 */
@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

  /**
   * 如果超过长度，则前后段交互体验不佳，使用默认错误消息
   */
  static Integer MAX_LENGTH = 200;

  /**
   * 自定义异常
   */
  @ExceptionHandler(ControllerException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ResultMessage<Object> handleServiceException(HttpServletRequest request, final Exception e, HttpServletResponse response) {


    //如果是自定义异常，则获取异常，返回自定义错误消息
    if (e instanceof ControllerException controllerException) {
      ResultCode resultCode = controllerException.getResultCode();

      Integer code = null;
      String message = null;

      if (resultCode != null) {
        code = resultCode.code();
        message = resultCode.message();
      }
      //如果有扩展消息，则输出异常中，跟随补充异常
      if (!controllerException.getMsg().equals(ControllerException.DEFAULT_MESSAGE)) {
        message += ":" + controllerException.getMsg();
      }

      log.error("全局异常[ServiceException]:{}-{}", controllerException.getResultCode().code(), controllerException.getResultCode().message());
      return ResultUtil.error(code, message);

    } else {

      log.error("全局异常[ServiceException]:", e);
    }

    //默认错误消息
    String errorMsg = "服务器异常，请稍后重试";
    if (e != null && e.getMessage() != null && e.getMessage().length() < MAX_LENGTH) {
      errorMsg = e.getMessage();
    }
    return ResultUtil.error(ResultCode.ERROR.code(), errorMsg);
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ResultMessage<Object> runtimeExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response) {

    log.error("全局异常[RuntimeException]:", e);

    return ResultUtil.error(ResultCode.ERROR);
  }

  /**
   * 请求方法异常
   * Request Method not Allowed.
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ResultMessage<Object> httpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response) {
    HttpRequestMethodNotSupportedException exception = (HttpRequestMethodNotSupportedException) e;
    return ResultUtil.error(ResultCode.METHOD_NOT_ALLOWED.code(), exception.getMessage());
  }

  /**
   * bean校验未通过异常
   * Deserialization fields mismatched.
   *
   * @see org.springframework.validation.Validator
   * @see org.springframework.validation.DataBinder
   */
  @ExceptionHandler(MismatchedInputException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ResultMessage<Object> mismatchedInputExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response) {
    MismatchedInputException exception = (MismatchedInputException) e;
    return ResultUtil.error(ResultCode.PARAMS_ERROR.code(), exception.getMessage());
  }

  /**
   * bean校验未通过异常
   * Required parameters missed.
   *
   * @see org.springframework.validation.Validator
   * @see org.springframework.validation.DataBinder
   */
  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ResultMessage<Object> missingServletRequestParameterExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response) {
    MissingServletRequestParameterException exception = (MissingServletRequestParameterException) e;
    return ResultUtil.error(ResultCode.PARAMS_ERROR.code(), exception.getMessage());
  }

  /**
   * bean校验未通过异常
   * Content-Type: multipart/form-data.
   *
   * @see org.springframework.validation.Validator
   * @see org.springframework.validation.DataBinder
   */
  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ResultMessage<Object> bindExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response) {

    BindException exception = (BindException) e;
    List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
    for (FieldError error : fieldErrors) {
      return ResultUtil.error(ResultCode.PARAMS_ERROR.code(), error.getField() + error.getDefaultMessage());
    }
    return ResultUtil.error(ResultCode.PARAMS_ERROR);
  }

  /**
   * bean校验未通过异常
   * Content-Type: application/json.
   * Content-Type: application/xml.
   *
   * @see org.springframework.validation.Validator
   * @see org.springframework.validation.DataBinder
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ResultMessage<Object> methodArgumentNotValidExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response) {

    MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
    List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
    for (FieldError error : fieldErrors) {
      return ResultUtil.error(ResultCode.PARAMS_ERROR.code(), error.getField() + error.getDefaultMessage());
    }
    return ResultUtil.error(ResultCode.PARAMS_ERROR);
  }

  /**
   * bean校验未通过异常
   * Validate fields failed.
   *
   * @see org.springframework.validation.Validator
   * @see org.springframework.validation.DataBinder
   */
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ResultMessage<Object> constraintViolationExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response) {
    ConstraintViolationException exception = (ConstraintViolationException) e;
    return ResultUtil.error(ResultCode.PARAMS_ERROR.code(), exception.getMessage());
  }
}
