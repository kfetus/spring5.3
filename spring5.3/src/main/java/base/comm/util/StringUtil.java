package base.comm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);
	
	/**
	 * 문자 타입 오른쪽 공백 padding
	 * 
	 * @param data
	 * @param length
	 * @return
	 */
	public static String rPadSpace(String data, int length) {
		if (data != null && data.length() <= length) {
			data = String.format("%1$-" + length + "s", data);
		} else {
			LOGGER.error("@@@@@@ Input Data is Fault @@@@@@");
			data = String.format("%1$-" + length + "s", "");
		}
		return data;
	}

	/**
	 * 문자 타입 왼쪽 공백 padding
	 * 
	 * @param data
	 * @param length
	 * @return
	 */
	public static String lPadSpace(String data, int length) {
		if (data != null && data.length() <= length) {
			data = String.format("%1$" + length + "s", data);
		} else {
			LOGGER.error("@@@@@@ Input Data is Fault @@@@@@");
			data = String.format("%1$" + length + "s", "");
		}
		return data;
	}

	/**
	 * 숫자 타입 왼쪽 0 padding
	 * 
	 * @param data
	 * @param length
	 * @return
	 */
	public static String lPadZero(String data, int length) {
		if (data != null && data.length() <= length) {
			data = String.format("%0" + length + "d", Long.parseLong(data));
		} else {
			LOGGER.error("@@@@@@ Input Data is Fault @@@@@@");
			data = String.format("%0" + length + "d", 0);
		}
		return data;
	}

	public static void main(String[] args) {
		LOGGER.debug("[" + rPadSpace("gergsdfds", 10) + "]");
		LOGGER.debug("[" + lPadSpace("3ergsdfds", 10) + "]");
		LOGGER.debug("[" + lPadZero("32342423424", 10) + "]");
	}
}
