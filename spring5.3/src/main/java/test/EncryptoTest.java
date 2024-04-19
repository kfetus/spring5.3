package test;

import base.comm.util.crypto.Sha256Crypto;

public class EncryptoTest {

	public static void main(String[] args) {
		System.out.println("######## START @@@@@@@@@");
		try {
			String sha256pass = Sha256Crypto.encSah256("qqqq", "8B7ek1MbLCqtXJ1Ivzruug==");
			System.out.println("######## result="+sha256pass);
			
			System.out.println(Sha256Crypto.encSah256("tttt", "oeiXs+JyfakOpePsSAE2Mw=="));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
