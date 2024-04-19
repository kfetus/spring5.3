package base.comm.websocket.javax;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MsgDecoder implements Decoder.Text<BidMessage> {

	@Override
	public void init(EndpointConfig ec) {

	}

	@Override
	public void destroy() {

	}

	@Override
	public BidMessage decode(String msg) throws DecodeException {
		ObjectMapper mapper = new ObjectMapper();
		BidMessage bidMsg;
		try {
			bidMsg = mapper.readValue(msg, BidMessage.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DecodeException(msg,"json to object decode error",e.getCause());
		}
		return bidMsg;
	}

	@Override
	public boolean willDecode(String s) {
		return s != null;
	}

}
