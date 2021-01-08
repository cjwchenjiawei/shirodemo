package com.cjw.system.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjw.system.model.LoginLog;
import com.cjw.system.mapper.LoginLogMapper;
import com.cjw.system.service.LoginLogService;
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService{

}
