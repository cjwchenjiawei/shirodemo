package com.cjw.system.service;

import cn.hutool.core.convert.Convert;
import org.junit.Test;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

import static org.junit.Assert.*;

public class UserServiceTest {

    @Test
    public void selectUserByUsername() {
        Date date = Convert.toDate("2020-11-11");
        System.out.println("date = " + date);

    }
}