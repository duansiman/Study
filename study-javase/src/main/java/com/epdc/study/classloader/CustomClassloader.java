package com.epdc.study.classloader;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CustomClassloader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            FileChannel channel = new FileInputStream("G:\\Code\\study-java\\target\\classes\\com\\devin\\Entity.class").getChannel();
            long fileSize = channel.size();
            ByteBuffer byteBuffer = ByteBuffer.allocate(Math.toIntExact(fileSize));
            int readSize = channel.read(byteBuffer);
            System.out.println(String.format("fileSize:%s, readSize:%s", fileSize, readSize));
            byte[] fileByteData = byteBuffer.array();
            channel.close();
            return this.defineClass(name, fileByteData, 0, fileByteData.length);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }
}
