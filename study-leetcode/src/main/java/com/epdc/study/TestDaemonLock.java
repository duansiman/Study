package com.epdc.study;

public class TestDaemonLock {

    private final static Object lock = new Object();

    public static void main(String[] args) {
//        synchronized (lock) {
//            System.out.println("lock...");
//            try {
//                TimeUnit.DAYS.sleep(1L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        System.out.println(System.getProperties().get("catalina.home"));
    }

}
