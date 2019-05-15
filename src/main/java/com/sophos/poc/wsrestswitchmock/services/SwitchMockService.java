package com.sophos.poc.wsrestswitchmock.services;

import org.jpos.iso.ISOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.sophos.poc.wsrestswitchmock.models.SwitchResponse;
import com.sophos.poc.wsrestswitchmock.utils.BuildISOMessage;
import com.sophos.poc.wsrestswitchmock.utils.ParseISOMessage;

@Service
public class SwitchMockService {
		
	@Autowired
	private ParseISOMessage parseISO;

	public SwitchResponse getFieldsData(String data) {
		SwitchResponse response = new SwitchResponse();
		try {
			String dataRs= parseISO.getJSON(data);
			response.setStatusCode(HttpStatus.OK.value());
			response.setData(dataRs);			
		}catch(ISOException e) {
			response.setStatusCode(HttpStatus.BAD_REQUEST.value());
			response.setData(HttpStatus.BAD_REQUEST.name() + e.getMessage());
		}		
		return response;
	}
	
	public SwitchResponse getRandomResponse(String data) {
		SwitchResponse response = new SwitchResponse();
		try {
			BuildISOMessage buildISO = new BuildISOMessage();
			String dataRs= buildISO.getRandom0210Response();
			response.setStatusCode(HttpStatus.OK.value());
			response.setData(dataRs);			
		}catch(Exception e) {
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setData(HttpStatus.INTERNAL_SERVER_ERROR.name() + e.getMessage());
		}		
		return response;
	}

	
}
