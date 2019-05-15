package com.sophos.poc.wsrestswitchmock.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {
	public void run() {
		try {
			int serverPort = 9881;
			InetAddress host = InetAddress.getByName("192.168.99.100");
			System.out.println("Connecting to server on port " + serverPort);

			Socket socket = new Socket(host, serverPort);
			System.out.println("Just connected to " + socket.getRemoteSocketAddress());
	
			PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
			toServer.println("0200B2200000001000000000000000800000201234000000010000011072218012345606A5DFGR031VETEALDEMONIOISO8583 1234567890");
			toServer.flush();
			
			
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = fromServer.readLine();
			System.out.println("Client received: " + line + " from Server");
						
			toServer.close();
			fromServer.close();
			socket.close();
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String callSwitch() {
		try {
			int serverPort = 9881;
			InetAddress host = InetAddress.getByName("192.168.99.100");
			System.out.println("Connecting to server on port " + serverPort);

			Socket socket = new Socket(host, serverPort);
			System.out.println("Just connected to " + socket.getRemoteSocketAddress());
	
			PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
			toServer.println("0200B2200000001000000000000000800000201234000000010000011072218012345606A5DFGR031VETEALDEMONIOISO8583 1234567890");
			toServer.flush();
			
			
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = fromServer.readLine();
			System.out.println("Client received: " + line + " from Server");
						
			toServer.close();
			fromServer.close();
			socket.close();
			
			return line;
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		ClientSocket client = new ClientSocket();
		client.run();
	}
}
