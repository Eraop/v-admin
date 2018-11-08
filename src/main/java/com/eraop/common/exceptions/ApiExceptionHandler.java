package com.eraop.common.exceptions;

import com.eraop.common.models.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author weiyi
 */
@ControllerAdvice(annotations = {RestController.class})
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleUnexpectedException(HttpServletRequest req, Exception e) {
        ResponseResult rr = new ResponseResult();
        if (e != null) {
            ResponseStatus rs = e.getClass().getAnnotation(ResponseStatus.class);
            if (rs != null) {
                rr.setStatus(rs.value());
            }
            String msg = e.getMessage();
            if (!StringUtils.isEmptyOrWhitespace(msg)) {
                rr.setMessage(msg);
            }
        }
        return rr.toResponse();
    }
}
