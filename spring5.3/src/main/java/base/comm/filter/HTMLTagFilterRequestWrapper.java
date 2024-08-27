package base.comm.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTMLTagFilterRequestWrapper extends HttpServletRequestWrapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(HTMLTagFilterRequestWrapper.class);

	private byte[] reqStreamData;

	public HTMLTagFilterRequestWrapper(HttpServletRequest request) {
		super(request);

		try {
			String reqAccept = request.getHeader("accept");
			String reqContentType = request.getHeader("content-type");
			LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ HTMLTagFilterRequestWrapper 생성자"+reqAccept);
//			LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ HTMLTagFilterRequestWrapper 생성자"+reqContentType);
			
			if( (reqAccept != null && reqContentType != null) && 
					(reqAccept.indexOf("application/json")>-1 || reqContentType.indexOf("application/json") > -1 || reqContentType.indexOf("multipart/form-data") > -1 ) ) {
//				LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ HTMLTagFilterRequestWrapper InputStream 가로채기");		
				InputStream is = request.getInputStream();
				this.reqStreamData = handleXSS(IOUtils.toByteArray(is));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ HTMLTagFilterRequestWrapper 생성자 종료");
	}

    private byte[] handleXSS(byte[] data) {
        String strData = new String(data);
//        LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ HTMLTagFilterRequestWrapper.handleXSS byte=>"+strData);
        strData = handleXSS(strData);
		return strData.getBytes();
    }

	private String handleXSS(String parameter) {
//		LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ HTMLTagFilterRequestWrapper.handleXSS String =>" + parameter);

		if (parameter == null) {
			return null;
		}
		StringBuffer strBuff = new StringBuffer();
		for (int i = 0; i < parameter.length(); i++) {
			char c = parameter.charAt(i);
			switch (c) {
			case '<':
				strBuff.append("&lt;");
				break;
			case '>':
				strBuff.append("&gt;");
				break;
			case '&':
				strBuff.append("&amp;");
				break;
//			case '\"':
//				strBuff.append("&quot;");
//				break;
			case '\'':
				strBuff.append("&apos;");
				break;
			case '(':
				strBuff.append("&#40;");
				break;
			case ')':
				strBuff.append("&#41;");
				break;
			default:
				strBuff.append(c);
				break;
			}
		}
		parameter = strBuff.toString();
//		LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ HTMLTagFilterRequestWrapper.handleXSS End String =>" + parameter);
		return parameter;

	}

	//request.getInputStream(); 한번 실행되면 해당 InputStream 소실됨. 카피했던 InputStream 리턴
	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (this.reqStreamData == null) {
			return super.getInputStream();
		}

		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.reqStreamData);

		return new ServletInputStream() {
			@Override
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}

			@Override
			public void setReadListener(ReadListener readListener) {
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public boolean isFinished() {
				return false;
			}
		};
	}

	@Override
	public String getQueryString() {
//		LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ HTMLTagFilterRequestWrapper.getQueryString");
		return handleXSS(super.getQueryString());
	}

	@Override
	public String getParameter(String parameter) {
//		LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ HTMLTagFilterRequestWrapper.getParameter"+parameter);
		return handleXSS(super.getParameter(parameter));
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> params = super.getParameterMap();
//		LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ HTMLTagFilterRequestWrapper.getParameterMap"+params);
		if (params != null) {
			params.forEach((key, value) -> {
				for (int i = 0; i < value.length; i++) {
					value[i] = handleXSS(value[i]);
				}
			});
		}
		return params;
	}

	@Override
	public String[] getParameterValues(String parameter) {
//		LOGGER.debug("¿¿¿¿¿¿¿¿¿¿¿¿ HTMLTagFilterRequestWrapper.getParameterValues=>"+parameter);
		String[] params = super.getParameterValues(parameter);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				params[i] = handleXSS(params[i]);
			}
		}
		return params;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.getInputStream(), "UTF_8"));
	}
}
