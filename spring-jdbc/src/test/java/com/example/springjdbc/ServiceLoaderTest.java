package com.example.springjdbc;

import com.example.springjdbc.service.Hello;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.ServiceLoader;

public class ServiceLoaderTest {

    @Test
    public void test() {
        ServiceLoader<Hello> serviceLoader = ServiceLoader.load(Hello.class);
        Iterator<Hello> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getClass().getName());
        }
    }

    @Test
    public void test2() throws IOException, URISyntaxException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource("META-INF/services/com.example.springjdbc.service.Hello");
        System.out.println(resource);
    }

}
