package base.comm.websocket.javax;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MsgEncoder implements Encoder.Text<BidMessage> {
	
	   @Override
	   public void init(EndpointConfig ec) { 
		   
	   }
	   
	   @Override
	   public void destroy() { 
		   
	   }
	   
	   @Override
	   public String encode(BidMessage msg) throws EncodeException {
		  ObjectMapper mapper = new ObjectMapper();
		  String strMessage;
		try {
			strMessage = mapper.writeValueAsString(msg);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new EncodeException(msg,"object to json encode error",e.getCause());
		}
	      return strMessage;
	   }
	}