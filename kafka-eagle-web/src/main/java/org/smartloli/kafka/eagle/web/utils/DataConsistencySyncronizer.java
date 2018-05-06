package org.smartloli.kafka.eagle.web.utils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by dujijun on 2018/5/6.
 * 数据一致同步器: 用来同步数据创建线程和后台清除线程
 * ==============================================
 * 1、数据创建线程是通过前端请求触发, 执行数据创建逻辑.
 * 2、后台清除线程通过对比数据库和Docker中ImageId, 将数据
 * 库中不存在的Image从Docker中清除
 *
 * 在数据创建的过程中, 存在Docker Image已经生成而数据库数据
 * 还未提交的中间状态, 而这个中间状态会被后台清除线程捕捉到, 进
 * 行Image清除, 这样就会导致错误出现。所以使用数据一致同步器进
 * 行数据清除
 *
 * ==============================================
 * 数据创建线程之间可以共享锁, 而后台清除线程需要与数据创建进行互斥
 * 后台清除线程只有一个相当于写者, 数据创建线程相当于读者
 * 故这里使用读写可重入锁来进行数据同步
 */
public class DataConsistencySyncronizer {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    private static final Lock readLock = lock.readLock();

    private static final Lock writeLock = lock.writeLock();

    public static void createMonitorLock(){
        readLock.lock();
    }

    public static void releaseCreateMonitorLock(){
        readLock.unlock();
    }

    public static void deleteMonitorLock(){
        writeLock.lock();
    }

    public static void releaseDeleteMonitorLock(){
        writeLock.unlock();
    }
}
