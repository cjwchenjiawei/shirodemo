package com.cjw.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class BizRuntimeException extends RuntimeException {
    private Integer status;
    private String message;

    public BizRuntimeException() {
    }

    public BizRuntimeException(String message){
        super(message);
        this.message = message;
    }
    public BizRuntimeException(Integer status, String message){
        super(status+"");
        this.status = status;
        this.message = message;

    }

    public BizRuntimeException(BaseInfoInterface baseInfoInterface) {
        super(baseInfoInterface.status() + "");
        this.status = baseInfoInterface.status();
        this.message = baseInfoInterface.message();
    }

    public BizRuntimeException(BaseInfoInterface baseInfoInterface, Throwable cause) {
        super(baseInfoInterface.status() + "", cause);
        this.status = baseInfoInterface.status();
        this.message = baseInfoInterface.message();
    }
}
