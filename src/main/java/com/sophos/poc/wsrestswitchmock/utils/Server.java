package com.sophos.poc.wsrestswitchmock.utils;

import java.util.List;

public interface Server {
    int getConnectionsCount();
    void setPort(Integer port);
    void start();
    void stop();
    List<Connection> getConnections();
    void addListener(Connection.Listener listener);
}
