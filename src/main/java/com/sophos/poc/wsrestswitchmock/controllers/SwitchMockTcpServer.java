package com.sophos.poc.wsrestswitchmock.controllers;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.sophos.poc.wsrestswitchmock.models.SwitchResponse;
import com.sophos.poc.wsrestswitchmock.services.SwitchMockService;
import com.sophos.poc.wsrestswitchmock.utils.Connection;
import com.sophos.poc.wsrestswitchmock.utils.Server;
import com.sophos.poc.wsrestswitchmock.utils.TcpConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Component
public class SwitchMockTcpServer implements Server, Connection.Listener {
    private static Log logger = LogFactory.getLog(SwitchMockTcpServer.class);
    private ServerSocket serverSocket;
    private volatile boolean isStop;
    private List<Connection> connections = new ArrayList<>();
    private List<Connection.Listener> listeners = new ArrayList<>();
	
    public void setPort(Integer port) {
        try {
            if (port == null) {
                logger.info("Property tcp.server.port not found. Use default port 1234");
                port = 1234;
            }
            serverSocket = new ServerSocket(port);
            logger.info("Server start at port " + port);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("May be port " + port + " busy.");
        }
    }

    @Override
    public int getConnectionsCount() {
        return connections.size();
    }

    @Override
    public void start() {
        new Thread(() -> {
            while (!isStop) {
                try {
                    Socket socket = serverSocket.accept();
                    if (socket.isConnected()) {
                        TcpConnection tcpConnection = new TcpConnection(socket);
                        tcpConnection.start();
                        tcpConnection.addListener(this);
                        connected(tcpConnection);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void stop() {
        isStop = true;
    }

    @Override
    public List<Connection> getConnections() {
        return connections;
    }

    @Override
    public void addListener(Connection.Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void messageReceived(Connection connection, Object message) {
        logger.info("Received new message from " + connection.getAddress().getCanonicalHostName());
        try {
        	byte[] input = (byte[]) message;
			String data = new String(input);
			logger.info("Request: " +data);
			SwitchMockService swtichService = new SwitchMockService();
			SwitchResponse response = swtichService.getRandomResponse(data);
			logger.info("Response: " + response.getData());
			connection.send(response.getData().getBytes());
			connection.close();
		} catch (Exception e) {
			logger.error("Error al Leer mensaje enviado por el  cliente: ", e);
		}
        for (Connection.Listener listener : listeners) {
            listener.messageReceived(connection, message);
        }
    }

    @Override
    public void connected(Connection connection) {
        logger.info("New connection! Ip: " + connection.getAddress().getCanonicalHostName() + ".");
        connections.add(connection);
        logger.info("Current connections count: " + connections.size());
        for (Connection.Listener listener : listeners) {
            listener.connected(connection);
        }
    }

    @Override
    public void disconnected(Connection connection) {
        logger.info("Disconnect! Ip: " + connection.getAddress().getCanonicalHostName() + ".");
        connections.remove(connection);
        logger.info("Current connections count: " + connections.size());
        for (Connection.Listener listener : listeners) {
            listener.disconnected(connection);
        }
    }
}