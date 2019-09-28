package com.boot.myioc.config;


import com.springboot.test.springboot.util.PackageUtil;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ioc容器上下文的实现
 */
public class ApplicationContext {

    private static volatile Map<Class<?>, Object> beanContainer = null;

    private ApplicationContext() {
    }

//    /**
//     * 根据class类型获取bean
//     * @return
//     */
    public static Object getBean(Class<?> classType) {
        return beanContainer.get(classType);
    }

    public static void init(String... packages) {
        try {
            initContext(packages);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("beanContainer=="+beanContainer);
    }

    /**
     * 初始化上下文
     *
     * @return
     */
    private static void initContext(String... packages) throws Exception {
        if (beanContainer != null) {
            return;
        }

        synchronized (ApplicationContext.class) {
            if (beanContainer != null) {
                return;
            }
            beanContainer = new HashMap<>();

            for (String pg : packages) {

                //扫描指定的包,获取所有class类路径
                Set<String> classSet = PackageUtil.getClassName(pg);

                //初始化Component对象
                initComponent(classSet);
            }

            //对bean中的依赖进行注入
            initDI();
        }
    }

    /**
     * 初始化Component对象
     */
    private static void initComponent(Collection<String> classSet) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (classSet == null) {
            return;
        }
        for (String className : classSet) {
            Class cls = Class.forName(className);
            if (cls.isAnnotationPresent(MyComponent.class)) {
                beanContainer.put(cls, cls.newInstance());
            }
        }
    }

    /**
     * 进行依赖注入操作
     */
    private static void initDI() throws IllegalAccessException {
        if (beanContainer == null) {
            return;
        }
        for (Map.Entry<Class<?>, Object> entry : beanContainer.entrySet()) {
            Field[] fields = entry.getKey().getDeclaredFields();
            if (fields == null) {
                continue;
            }
            for (Field field : fields) {
                if (field.isAnnotationPresent(MyInject.class)) {
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    // 进行依赖注入

                    System.out.println("注入类field.getType()=="+beanContainer.get(field.getType()));

                    field.set(entry.getValue(), beanContainer.get(field.getType()));
                    field.setAccessible(flag);
                }
            }
        }
    }

}
