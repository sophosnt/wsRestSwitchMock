package com.sophos.poc.wsrestswitchmock.utils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TcpConnection implements Connection {
    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket socket;
    private List<Listener> listeners = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(TcpConnection.class);
    
    public TcpConnection(Socket socket) {
        this.socket = socket;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InetAddress getAddress() {
        return socket.getInetAddress();
    }

    @Override
    public void send(Object objectToSend) {
        if (objectToSend instanceof byte[]) {
            byte[] data = (byte[]) objectToSend;
            try {
                outputStream.write(data);
                outputStream.flush();
            } catch (IOException e) {
            	logger.error("Send Error", e);                
            }
        }
    }

    @Override
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void start() {
        new Thread(() -> {
            while (true) {
                byte buf[] = new byte[64 * 1024];
                try {
                    int count = inputStream.read(buf);
                    if (count > 0) {
                        byte[] bytes = Arrays.copyOf(buf, count);
                        for (Listener listener : listeners) {
                            listener.messageReceived(this, bytes);
                        }
                    } else {
                        socket.close();
                        for (Listener listener : listeners) {
                            listener.disconnected(this);
                        }
                        break;
                    }
                }  catch (SocketException e) {
                	logger.info("El Socket se ha cerrado");
                	for (Listener listener : listeners) {
                        listener.disconnected(this);
                    }
                	break;
                } 
                catch (IOException e) {
                	logger.error("Error en la conexion: ", e);                
                    for (Listener listener : listeners) {
                        listener.disconnected(this);
                    }
                    break;
                }
            }
        }).start();
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
        	logger.error("Error al cerrar la conexion", e);            
        }
    }
}