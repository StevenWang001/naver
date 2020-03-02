package com.naver;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CachedData {

    private Object data;
    private volatile boolean cacheValid;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        CachedData cachedData = new CachedData();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                cachedData.invalidCache();
                cachedData.processCachedData();
            }).start();
        }
    }

    public void invalidCache() {
        this.cacheValid = false;
    }

    public void processCachedData() {
        lock.readLock().lock();
        if (!cacheValid) {
            lock.readLock().unlock();
            lock.writeLock().lock();
            if (!cacheValid) {
                data = getData();
                cacheValid = true;
            }
            lock.readLock().lock();
            lock.writeLock().unlock();
        }
        process(data);
        lock.readLock().unlock();
    }

    public Object getData() {
        int ran = new Random().nextInt();
        System.out.println(Thread.currentThread().getName() + "Get data: " + ran);
        return ran;
    }

    public void process(Object data) {
        System.out.println(Thread.currentThread().getName() + "process data: " + data);
    }
}
