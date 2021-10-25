package com.epdc.study;

import java.io.FileInputStream;
import java.nio.channels.FileChannel;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            FileChannel channel = new FileInputStream("G:\\Code\\study-java\\src\\main\\java\\com\\devin\\Entity.java").getChannel();
            System.out.println(channel.size());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
