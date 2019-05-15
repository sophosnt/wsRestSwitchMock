package com.sophos.poc.wsrestswitchmock.utils;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.stereotype.Component;

@Component
public class ParseISOMessage {

	private static final Logger logger = LogManager.getLogger(ParseISOMessage.class);

	public void main(String[] args) throws IOException, ISOException {
		// Print Input Data
		String data = "0200B2200000001000000000000000800000201234000000010000011072218012345606A5DFGR031VETEALDEMONIOISO8583 1234567890";
		getJSON(data);
	}

	public String getJSON(String data) throws ISOException {
		GenericPackager packager;
		try {
			packager = new GenericPackager(ParseISOMessage.class.getResourceAsStream("/Iso8583.xml"));
			StringBuffer buffer = new StringBuffer();
			ISOMsg isoMsg = new ISOMsg();
			isoMsg.setPackager(packager);
			isoMsg.unpack(data.getBytes());

			buffer.append("{ MTI = " + isoMsg.getMTI() + ",");
			for (int i = 1; i <= isoMsg.getMaxField(); i++) {
				if (isoMsg.hasField(i)) {
					if (i == isoMsg.getMaxField()) {
						buffer.append(" Field-" + i + "=" + isoMsg.getString(i) + " }");
					} else {
						buffer.append(" Field-" + i + "=" + isoMsg.getString(i) + ",");
					}
				}
			}
			logger.info(buffer.toString());
			return buffer.toString();

		} catch (ISOException e) {
			logger.error("Error Construyendo Trama",e);
			throw e;
		}
	}

}
