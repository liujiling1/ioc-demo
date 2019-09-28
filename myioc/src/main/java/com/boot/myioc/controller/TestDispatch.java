package com.boot.myioc.controller;

import com.boot.myioc.config.MyComponent;
import com.boot.myioc.config.MyInject;
import com.boot.myioc.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : ljl
 * @date : 2019/9/24
 */
@MyComponent
public class TestDispatch {

    @MyInject
    TestService testService;

    public Object apply(){
        System.out.println("sss="+testService);
        return testService.hello("张三");
    }

}
