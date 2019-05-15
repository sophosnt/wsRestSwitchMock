package com.sophos.poc.wsrestswitchmock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sophos.poc.wsrestswitchmock.controllers.SwitchMockTcpServer;

@SpringBootApplication
public class WsRestSwitchMockApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsRestSwitchMockApplication.class, args);
		SwitchMockTcpServer server = new SwitchMockTcpServer();
		server.setPort(9881);
		server.start();
	}
}
