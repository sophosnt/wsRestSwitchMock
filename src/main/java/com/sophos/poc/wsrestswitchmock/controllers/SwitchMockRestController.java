package com.sophos.poc.wsrestswitchmock.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sophos.poc.wsrestswitchmock.models.SwitchRequest;
import com.sophos.poc.wsrestswitchmock.models.SwitchResponse;
import com.sophos.poc.wsrestswitchmock.services.SwitchMockService;
import com.sophos.poc.wsrestswitchmock.utils.ClientSocket;

@RestController 
@RequestMapping("/switch")
public class SwitchMockRestController {
	
	@Autowired
	private SwitchMockService swtichService;
	
	@PostMapping(path="/getFieldsData" , consumes = "application/json", produces = "application/json")
	public ResponseEntity<SwitchResponse> getFieldsData(@Valid @RequestBody SwitchRequest request) {
		if(request.getJwt().equals("AUTH")) {
			SwitchResponse response = swtichService.getFieldsData(request.getData());
			return new ResponseEntity<SwitchResponse>(response, HttpStatus.OK) ;
		}		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping(path="/getRandomResponse" , consumes = "application/json", produces = "application/json")
	public ResponseEntity<SwitchResponse> getRandomResponseData(@Valid @RequestBody SwitchRequest request) {
		if(request.getJwt().equals("AUTH")) {
			SwitchResponse response = swtichService.getRandomResponse(request.getData());
			return new ResponseEntity<SwitchResponse>(response, HttpStatus.OK) ;
		}		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping(path="/getRandomFromSwitch" , consumes = "application/json", produces = "application/json")
	public ResponseEntity<SwitchResponse> getRandomFromSwitch(@Valid @RequestBody SwitchRequest request) {
		if(request.getJwt().equals("AUTH")) {
			ClientSocket client = new ClientSocket();
			String responseData = client.callSwitch();
			if (responseData!= null) {
				SwitchResponse response = new SwitchResponse();
				response.setStatusCode(HttpStatus.OK.value());
				response.setData(responseData);
				return new ResponseEntity<SwitchResponse>(response, HttpStatus.OK) ;
			}
			return new ResponseEntity<SwitchResponse>(HttpStatus.NOT_IMPLEMENTED) ;
		}		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
