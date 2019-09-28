package com.boot.myioc.util;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class PackageUtil {

    /**
     * 获取某包下所有类
     *
     * @param packageName 包名
     */
    public static Set<String> getClassName(String packageName) throws Exception {
        return getClassName(packageName, true);
    }

    /**
     * 获取某包下所有类
     *
     * @param packageName 包名
     * @param isChild     是否遍历子包
     * @return 类的完整名称
     */
    public static Set<String> getClassName(String packageName, boolean isChild) throws Exception {
        Set<String> set = null;
        String packagePath = packageName.replace(".", "/");
        URL url = Thread.currentThread().getContextClassLoader().getResource(packagePath);
        if (url != null) {
            String protocol = url.getProtocol();
            if (protocol.equals("file")) {
                set = getClassNameFromDir(url.getPath(), packageName, isChild);
            }
        }
        return set;
    }

    /**
     * 获取文件路径下所有类
     *
     * @param filePath    文件路径
     * @param packageName 包名
     * @param isChild     是否遍历子包
     * @return 类的完整名称
     */
    public static Set<String> getClassNameFromDir(String filePath, String packageName, boolean isChild) throws Exception {
        Set<String> set = new HashSet<String>();
        File[] files = new File(filePath).listFiles();
        for (File childFile : files) {
            if (childFile.isDirectory() && isChild) {
                set.addAll(
                        getClassNameFromDir(childFile.getPath(), packageName + "." + childFile.getName(), isChild));
            } else {
                String fileName = childFile.getName();
                if (fileName.endsWith(".class") && !fileName.contains("$")) {
                    if(!set.add(packageName + "." + fileName.replace(".class", ""))){
                        throw new Exception("重复的service！！！");
                    }
                }
            }
        }
        return set;
    }

}
