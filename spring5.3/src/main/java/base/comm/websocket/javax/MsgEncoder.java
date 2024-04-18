package base.comm.websocket.javax;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MsgEncoder implements Encoder.Text<String> {
	   @Override
	   public void init(EndpointConfig ec) { 
		   
	   }
	   
	   @Override
	   public void destroy() { 
		   
	   }
	   
	   @Override
	   public String encode(String msg) throws EncodeException {
	      return msg;
	   }
	}