package com.sophos.poc.wsrestswitchmock.utils;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.stereotype.Component;


@Component
public class BuildISOMessage {
	
	private static final Logger logger = LogManager.getLogger(BuildISOMessage.class);
	private GenericPackager packager;
	
	public BuildISOMessage () throws IOException, ISOException{
		
		setPackager(new GenericPackager(BuildISOMessage.class.getResourceAsStream("/Iso8583.xml")));
	}
	
	public GenericPackager getPackager() {
		try {
			if (packager != null) {
				return packager;
			}
			return new GenericPackager(BuildISOMessage.class.getResourceAsStream("/Iso8583.xml"));
		} catch (ISOException e) {

		}
		return null;
	}

	public void setPackager(GenericPackager packager) {
		this.packager = packager;
	}
	
	public String getRandom0210Response() {
		double i = Math.random()*100;
		if (i>0 && i<=25) {
			return get021000Response();
		}else if (i>25 && i<=50) {
			return get021055Response();
		}
		else if (i>50 && i<=75) {
			return get021096Response();
		}
		else{
			return get021061Response();
		}
	}

	private String get021061Response() {
		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager(getPackager());
		String response = null;
		try {
			isoMsg.setMTI("0210");
			isoMsg.set(3, "201234");
			isoMsg.set(4, "10000");
			isoMsg.set(7, "110722180");
			isoMsg.set(11, "123456");
			isoMsg.set(38, "000000");
			isoMsg.set(39, "61");
			isoMsg.set(44, "A5DFGR");
			isoMsg.set(105, "VETEALDEMONIOISO8583 1234567890");	
			byte[] data = isoMsg.pack();
			response= new String(data);
			logger.info("021061->"+response);
		} catch (ISOException e) {
			logger.error("Error creando Respuesta 61:  ",e);
		}		
		return response;
	}

	private String get021096Response() {
		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager(getPackager());
		String response = null;
		try {
			isoMsg.setMTI("0210");
			isoMsg.set(3, "201234");
			isoMsg.set(4, "10000");
			isoMsg.set(7, "110722180");
			isoMsg.set(11, "123456");
			isoMsg.set(38, "000000");
			isoMsg.set(39, "96");
			isoMsg.set(44, "A5DFGR");
			isoMsg.set(105, "VETEALDEMONIOISO8583 1234567890");	
			byte[] data = isoMsg.pack();
			response= new String(data);
			logger.info("021096->"+response);
		} catch (ISOException e) {
			logger.error("Error creando Respuesta 96:  ",e);
		}		
		return response;
	}

	private String get021055Response() {
		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager(getPackager());
		String response = null;
		try {
			isoMsg.setMTI("0210");
			isoMsg.set(3, "201234");
			isoMsg.set(4, "10000");
			isoMsg.set(7, "110722180");
			isoMsg.set(11, "123456");
			isoMsg.set(38, "000000");
			isoMsg.set(39, "55");
			isoMsg.set(44, "A5DFGR");
			isoMsg.set(105, "VETEALDEMONIOISO8583 1234567890");	
			byte[] data = isoMsg.pack();
			response= new String(data);
			logger.info("021055->"+response);
		} catch (ISOException e) {
			logger.error("Error creando Respuesta 55:  ",e);
		}		
		return response;
	}

	private String get021000Response() {
		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager(getPackager());
		String response = null;
		try {
			isoMsg.setMTI("0210");
			isoMsg.set(3, "201234");
			isoMsg.set(4, "10000");
			isoMsg.set(7, "110722180");
			isoMsg.set(11, "123456");
			isoMsg.set(38, "888888");
			isoMsg.set(39, "00");
			isoMsg.set(44, "A5DFGR");
			isoMsg.set(105, "VETEALDEMONIOISO8583 1234567890");	
			byte[] data = isoMsg.pack();
			response= new String(data);
			logger.info("021000->"+response);
		} catch (ISOException e) {
			logger.error("Error creando Respuesta 00:  ",e);
		}		
		return response;
	}
	
	
	public static void main(String[] args) throws IOException, ISOException {
		// Create Packager based on XML that contain DE type
		GenericPackager packager = new GenericPackager(BuildISOMessage.class.getResourceAsStream("/Iso8583.xml"));
		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager(packager);
		isoMsg.setMTI("0210");
		isoMsg.set(3, "201234");
		isoMsg.set(4, "10000");
		isoMsg.set(7, "110722180");
		isoMsg.set(11, "123456");
		isoMsg.set(38, "888888");
		isoMsg.set(39, "00");
		isoMsg.set(44, "A5DFGR");
		isoMsg.set(105, "VETEALDEMONIOISO8583 1234567890");		
 		// print the DE list
		logISOMsg(isoMsg);
 
		byte[] data = isoMsg.pack();
		logger.info("RESULT-1: " + new String(data));
		
	}
 
	private static void logISOMsg(ISOMsg msg) {	
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("{ MTI = " + msg.getMTI() + ",");
			for (int i = 1; i <= msg.getMaxField(); i++) {
				if (msg.hasField(i)) {
					if (i == msg.getMaxField()) {
						buffer.append(" Field-" + i + "=" + msg.getString(i) + " }");
					} else {
						buffer.append(" Field-" + i + "=" + msg.getString(i) + ",");
					}
				}
			}
			logger.info(buffer.toString());
		} catch (ISOException e1) {
			logger.error("Error leyendo ISO",e1);
		} 
	}
	
}
