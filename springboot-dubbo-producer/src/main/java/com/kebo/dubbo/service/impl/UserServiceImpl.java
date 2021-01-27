package com.kebo.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kebo.service.IUserService;
import org.springframework.stereotype.Component;


/**
 * @description:
 * @author: kb
 * @create: 2021-01-27 20:21
 **/
@Component
@Service(interfaceClass = IUserService.class)
public class UserServiceImpl implements IUserService {
    @Override
    public String sayHello(String name) {
        return "hello, " + name;
    }
}

