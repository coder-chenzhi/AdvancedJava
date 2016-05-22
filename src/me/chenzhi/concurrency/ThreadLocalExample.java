package me.chenzhi.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLocalExample {


    public static class ThreadLocalRunnable implements Runnable {
    	private static final AtomicInteger nextId = new AtomicInteger(0);
    	
        private static ThreadLocal<Integer> threadId =
               new ThreadLocal<Integer>();

        @Override
        public void run() {
            threadId.set(nextId.getAndIncrement());
    
            try {
                Thread.sleep(2000); // to guarantee all threads has executed the threadlocal.set()
            } catch (InterruptedException e) {
            }
    
            System.out.println("ThreadLocal " + Thread.currentThread().getName()
            		+ " threadId: " + threadId.get());
        }
    }
    
    public static class NotThreadLocalRunnable implements Runnable {
    	private static final AtomicInteger nextId = new AtomicInteger(0);
    	
        private static int threadId;

        @Override
        public void run() {
        	threadId = (nextId.getAndIncrement());
    
            try {
                Thread.sleep(2000); // to guarantee all threads has executed the last assignment.
            } catch (InterruptedException e) {
            }
    
            System.out.println("NonThreadLocal " + Thread.currentThread().getName()
            		+ " threadId: " + threadId);
        }
    }

    
    public static class ThreadLocalNonStaticRunnable implements Runnable {
    	private static final AtomicInteger nextId = new AtomicInteger(0);
    	
        private ThreadLocal<Integer> threadId =
               new ThreadLocal<Integer>();

        @Override
        public void run() {
            threadId.set(nextId.getAndIncrement());
    
            try {
                Thread.sleep(2000); // to guarantee all threads has executed the threadlocal.set()
            } catch (InterruptedException e) {
            }
    
            System.out.println("ThreadLocalNonStatic " + Thread.currentThread().getName()
            		+ " threadId: " + threadId.get());
        }
    }

    public static void main(String[] args) {
        ThreadLocalRunnable threadLocalRunnableInstance = new ThreadLocalRunnable();

        Thread thread1 = new Thread(threadLocalRunnableInstance, "thread1");
        Thread thread2 = new Thread(threadLocalRunnableInstance, "thread2");

        thread1.start();
        thread2.start();
        
        try {
	        thread1.join(); //wait for thread 1 to terminate
	        thread2.join(); //wait for thread 2 to terminate
        } catch (Exception e ) {
        	
        }
        
        NotThreadLocalRunnable notThreadLocalRunnableInstance = new NotThreadLocalRunnable();

        Thread thread3 = new Thread(notThreadLocalRunnableInstance, "thread3");
        Thread thread4 = new Thread(notThreadLocalRunnableInstance, "thread4");

        thread3.start();
        thread4.start();
        
        try {
	        thread3.join(); //wait for thread 5 to terminate
	        thread4.join(); //wait for thread 6 to terminate
        } catch (Exception e ) {
        	
        }
        
        ThreadLocalNonStaticRunnable notThreadLocalNonStaticRunnableInstance = 
        		new ThreadLocalNonStaticRunnable();

        Thread thread5 = new Thread(notThreadLocalNonStaticRunnableInstance, "thread5");
        Thread thread6 = new Thread(notThreadLocalNonStaticRunnableInstance, "thread6");

        thread5.start();
        thread6.start();
        
        try {
	        thread5.join(); //wait for thread 5 to terminate
	        thread6.join(); //wait for thread 6 to terminate
        } catch (Exception e ) {
        	
        }
        
    }

}