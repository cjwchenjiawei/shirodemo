package com.cjw.common.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVO implements Serializable {
    private Integer code;
    private Integer status;
    private String message;
    private Object data;
    public ResultVO(ResultCode resultCode, Object data){
        this.code = resultCode.status();
        this.status = resultCode.status();
        this.message = resultCode.message();
        this.data = data;
    }
    public ResultVO(ResultCode resultCode){
        this.code = resultCode.status();
        this.status = resultCode.status();
        this.message = resultCode.message();
    }
    public ResultVO(Integer status, String message){
        this.code = status;
        this.status = status;
        this.message = message;
    }

    // 返回成功
    public static ResultVO success(){
        ResultVO resultVO = new ResultVO(ResultCode.SUCCESS);
        return resultVO;
    }
    // 返回成功
    public static ResultVO success(Object data){
        ResultVO resultVO = new ResultVO(ResultCode.SUCCESS,data);
        return resultVO;
    }
    // 返回失败
    public static ResultVO fail(ResultCode resultCode){
        ResultVO resultVO = new ResultVO(resultCode);
        return resultVO;
    }
    // 返回失败
    public static ResultVO fail(ResultCode resultCode, Object data){
        ResultVO resultVO = new ResultVO(resultCode,data);
        return resultVO;
    }
    // 返回失败
    public static ResultVO fail(Integer status, String message){
        ResultVO resultVO = new ResultVO(status,message);
        return resultVO;
    }
}
