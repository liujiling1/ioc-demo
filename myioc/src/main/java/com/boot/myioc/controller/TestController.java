package com.boot.myioc.controller;

import com.boot.myioc.config.ApplicationContext;
import com.boot.myioc.config.MyComponent;
import com.boot.myioc.config.MyInject;
import com.boot.myioc.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : ljl
 * @date : 2019/9/24
 */
@RestController
public class TestController {

//    @MyInject
//    TestDispatch testDispatch;

    @MyInject
    TestService testService ;

    @RequestMapping("/test")
    public Object test(){

        testService = (TestService) ApplicationContext.getBean(TestService.class);

        System.out.println("sss="+testService);
        return testService.hello("张三");
    }

}
