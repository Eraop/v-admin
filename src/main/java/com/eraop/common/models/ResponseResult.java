package com.eraop.common.models;

import com.eraop.common.utils.LangUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;


/**
 * @author weiyi
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> {
    @JsonIgnore
    private HttpStatus status = HttpStatus.OK;

    private Integer code;

    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date date;

    private T data;

    public ResponseResult() {
        this.date = new Date();
    }

    public ResponseResult(String message) {
        this();
        this.message = message;
    }

    public ResponseResult(T data) {
        this();
        this.setData(data);
    }

    public Integer getCode() {
        return this.status.value();
    }

    public String getMessage() {
        String message = this.message;
        if (message != null && !message.isEmpty()) {
            String result = LangUtils.language(message);
            if (StringUtils.isNotEmpty(result)) {
                return result;
            }
        }
        return message;
    }


    public ResponseResult(HttpStatus status, String message) {
        this();
        this.message = message;
        this.status = status;
    }

    public ResponseResult(HttpStatus status, String message, T data) {
        this();
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public ResponseEntity<ResponseResult<?>> toResponse() {
        return new ResponseEntity<>(this, this.getStatus());
    }

    public static <T> ResponseEntity success(T d) {
        ResponseResult responseResult = new ResponseResult(HttpStatus.OK, "success", d);
        return new ResponseEntity(responseResult, responseResult.getStatus());
    }

    public static <T> ResponseEntity failed() {
        ResponseResult responseResult = new ResponseResult(HttpStatus.BAD_REQUEST, "error.badRequest");
        return new ResponseEntity(responseResult, responseResult.getStatus());
    }

    public static <T> ResponseEntity notFound() {
        ResponseResult responseResult = new ResponseResult(HttpStatus.NOT_FOUND, "error.noResourceFound");
        return new ResponseEntity(responseResult, responseResult.getStatus());
    }

    public static <T> ResponseEntity unauthorized() {
        ResponseResult responseResult = new ResponseResult(HttpStatus.UNAUTHORIZED, "error.unauthorized");
        return new ResponseEntity(responseResult, responseResult.getStatus());
    }

    public static <T> ResponseEntity forbidden() {
        ResponseResult responseResult = new ResponseResult(HttpStatus.FORBIDDEN, "error.permissionDenied");
        return new ResponseEntity(responseResult, responseResult.getStatus());
    }

    public static <T> ResponseEntity serverUnavailable() {
        ResponseResult responseResult = new ResponseResult(HttpStatus.SERVICE_UNAVAILABLE, "error.serverUnavailable");
        return new ResponseEntity(responseResult, responseResult.getStatus());
    }

}
