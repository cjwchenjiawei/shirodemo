package com.cjw.common.exception;

import com.cjw.common.util.ResultCode;
import com.cjw.common.util.ResultVO;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


//@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 自定义异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizRuntimeException.class)
    @ResponseBody
    public ResultVO bizExceptionHandler(HttpServletRequest request, BizRuntimeException e){
        logger.error("发生业务异常，原因是:" + e.getMessage());
        return ResultVO.fail(e.getStatus(),e.getMessage());
    }

    /**
     * 处理空指针的异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ResultVO exceptionHandler(HttpServletRequest request, NullPointerException e){
        logger.error("发生空指针异常！原因是:",e);
        return ResultVO.fail(ResultCode.BODY_NOT_MATCH);
    }

    /**
     * 处理用户名不存在的异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = UnknownAccountException.class)
    @ResponseBody
    public ResultVO unknownAccountExceptionHandler(HttpServletRequest request, NullPointerException e){
        logger.error("发生用户不存在的异常！原因是:",e);
        return ResultVO.fail(ResultCode.USER_NOT_EXIST);
    }

    /**
     * 处理密码不匹配的异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = org.apache.shiro.authc.IncorrectCredentialsException.class)
    @ResponseBody
    public ResultVO incorrectCredentialsExceptionHandler(HttpServletRequest request, IncorrectCredentialsException e){
        logger.error("发生密码不匹配的异常！原因是:",e);
        return ResultVO.fail(ResultCode.USER_PASS_ERROR);
    }

    /**
     * 处理其他异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVO exceptionHandler(HttpServletRequest request, Exception e){
        logger.error("未知异常！原因是:",e);
        System.out.println("e = " + e);
        return ResultVO.fail(ResultCode.INTERNAL_SERVER_ERROR);
    }


}
