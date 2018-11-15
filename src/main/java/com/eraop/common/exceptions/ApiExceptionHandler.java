package com.eraop.common.exceptions;

import com.eraop.common.models.ResponseResult;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author weiyi
 */
@ControllerAdvice(annotations = {RestController.class})
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleUnexpectedException(HttpServletRequest req, Exception ex) {
        ResponseResult rr = new ResponseResult();
        if (ex != null) {
            // ResponseStatus rs = ex.getClass().getAnnotation(ResponseStatus.class);
            if (ex instanceof AuthorizationException) {
                // 权限异常
                return ResponseResult.unauthorized();
            } else if (ex instanceof AuthenticationException) {
                // 登录认证异常
                return ResponseResult.forbidden();
            }
        }
        // 统一返回请求异常
        return ResponseResult.failed();
    }


}
