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
	
	/**
	 * 이름 마스킹
	 * @param name
	 * @return
	 */
	public static String asteriskName(String name) {
		String chgStr = "";
		if(name == null || name.length() < 2) {
			return name;
		} else if (name.length() == 2) {
			chgStr = name.substring(0, 1) + "*";
		} else {
			chgStr = name.substring(0, 1); 
			for ( int i = 0 ; i < name.length() -2; i++) {
				chgStr += "*";
			}
			chgStr += name.substring(name.length()-1);
		}
		
		return chgStr;
	}

	
	public static String asteriskHP(String hpNo) {
		String mask = "";
		
		if( hpNo.length() == 10 || hpNo.length() == 11) {
			if( hpNo.length() == 10 ) {
				mask = hpNo.substring(0,3) + "-" + hpNo.substring(3,4) + "**-" + hpNo.substring(6,8)+"**";
			} else {
				mask = hpNo.substring(0,3) + "-" + hpNo.substring(3,5) + "**-" + hpNo.substring(7,9)+"**";
			}
		} else {
			return hpNo;
		}
		
		return mask;
	}
	
	
	public static void main(String[] args) {
		LOGGER.debug("[" + rPadSpace("gergsdfds", 10) + "]");
		LOGGER.debug("[" + lPadSpace("3ergsdfds", 10) + "]");
		LOGGER.debug("[" + lPadZero("32342423424", 10) + "]");
		LOGGER.debug("[" + asteriskName("123456789") + "]");
		LOGGER.debug("[" + asteriskHP("12345678901") + "]");
	}
}
