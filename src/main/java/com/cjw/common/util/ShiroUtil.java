package com.cjw.common.util;

import com.cjw.common.exception.BizRuntimeException;
import com.cjw.system.model.User;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {

    /**
     * 获取从登陆信息中获取当前用户，不存在就抛出未登陆异常
     * @return
     */
    public static User getCurrentUser(){
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            throw new BizRuntimeException(ResultCode.USER_NOT_LOGGED_IN);
        }
        return user;
    }
}
