package com.epdc.study.classloader;

import java.lang.reflect.InvocationTargetException;

public class TestClassloader {

    public static void main( String[] args ) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        ClassLoader appClassloader = ClassLoader.getSystemClassLoader();
//        System.out.println(appClassloader);
//        System.out.println(appClassloader.getParent());
//        System.out.println(appClassloader.getParent().getParent());

        CustomClassloader classloader = new CustomClassloader();
        Class clz = classloader.findClass("com.devin.Entity");
        Object obj = clz.getDeclaredConstructor().newInstance();
        System.out.println(obj);
        System.out.println(obj.getClass().getClassLoader());
    }

}
