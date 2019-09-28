package com.boot.myioc.service;


import com.boot.myioc.config.MyComponent;

/**
 * @author : ljl
 * @date : 2019/9/24
 */
@MyComponent
public class TestService {


    public Object hello(String name){
        return "service"+name;
    }


}
