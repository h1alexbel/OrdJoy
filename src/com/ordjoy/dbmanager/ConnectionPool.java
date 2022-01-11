package com.ordjoy.dbmanager;

import com.ordjoy.exception.DataBaseException;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.ordjoy.util.ExceptionMessageUtils.DATABASE_LAYER_EXCEPTION_MESSAGE;

public class ConnectionPool {

    private static final String DRIVER_KEY = "db.postgres.driver";
    private static final String POOL_SIZE_KEY = "db.pool.size";
    private static ConnectionPool instance = new ConnectionPool();
    private static Lock lock = new ReentrantLock();
    private static AtomicBoolean isInstanced = new AtomicBoolean(false);
    private static BlockingQueue<ProxyConnection> connections;

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        if (!isInstanced.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    isInstanced.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    static {
        loadDriver();
        initConnectionPool();
    }

    private static void initConnectionPool() {
        int poolSize = Integer.parseInt(PropertiesManager.getPropertyByKey(POOL_SIZE_KEY));
        connections = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            connections.offer(ConnectionManager.getProxyConnection());
        }
    }

    private static void loadDriver() {
        try {
            Class.forName(PropertiesManager.getPropertyByKey(DRIVER_KEY));
        } catch (ClassNotFoundException e) {
            throw new DataBaseException(DATABASE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public ProxyConnection getConnection() {
        try {
            return connections.take();
        } catch (InterruptedException e) {
            throw new DataBaseException(e);
        }
    }

    public void releaseConnection(ProxyConnection proxyConnection) {
        try {
            if (!proxyConnection.getAutoCommit()) {
                proxyConnection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connections.offer(proxyConnection);
    }

    public void closeConnectionPool() {
        try {
            while (!connections.isEmpty()) {
                connections.poll().closeConnection();
            }
        } catch (SQLException e) {
            throw new DataBaseException(DATABASE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }
}