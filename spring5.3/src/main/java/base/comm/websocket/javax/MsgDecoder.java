package base.comm.websocket.javax;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MsgDecoder implements Decoder.Text<String> {

	@Override
	public void init(EndpointConfig ec) {

	}

	@Override
	public void destroy() {

	}

	@Override
	public String decode(String msg) throws DecodeException {
		return msg;
	}

	@Override
	public boolean willDecode(String s) {
		return s != null;
	}

}
