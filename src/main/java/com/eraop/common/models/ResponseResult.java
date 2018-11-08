package com.eraop.common.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

/**
 * @author weiyi
 */
@Data
public class ResponseResult<T> {
    private HttpStatus status = HttpStatus.OK;
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

    public ResponseEntity<ResponseResult<?>> toResponse() {
        ResponseEntity<ResponseResult<?>> responseEntity = new ResponseEntity<ResponseResult<?>>(this,
                this.getStatus());
        return responseEntity;
    }
}
