package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UrlConSessionTest {

	private String firstCon() {

		String cookie = "";
		HttpURLConnection con = null;
		URL url;

		try {
			url = new URL("http://192.168.0.16:8080/restLogin.do");
			con = (HttpURLConnection) url.openConnection();
			
			con.setRequestMethod("POST");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setConnectTimeout(10000);
			con.setInstanceFollowRedirects(false);
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
//			con.connect();
			
			String data = "{\"userId\":\"qqqq\",\"userPass\":\"qqqq\"}";
			con.getOutputStream().write(data.getBytes());

			Map<String, List<String>> headerReturnMap = con.getHeaderFields();
			if (headerReturnMap.containsKey("Set-Cookie")) {
				List<String> cookeiList = (List<String>) headerReturnMap.get("Set-Cookie");
				for (Iterator<String> iter = cookeiList.iterator(); iter.hasNext();) {
					cookie += (String) iter.next() + ", ";
				}
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			StringBuffer sb = new StringBuffer();
			String responseData = "";
			while ((responseData = br.readLine()) != null) {
				sb.append(responseData);
			}
			System.out.println("html=" + sb.toString()+"=end");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.disconnect();
		}

		return cookie;
	}

	private String conAfterGetSession(String cookie) {

		StringBuffer sb = null;
		HttpURLConnection con = null;
		URL url;

		try {
			url = new URL("http://192.168.0.16:8080/auction/auctionBidMain.do");
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setRequestProperty("cookie", cookie);
			con.connect();

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			sb = new StringBuffer();

			String responseData = "";
			while ((responseData = br.readLine()) != null) {
				sb.append(responseData);
			}
			System.out.println("RESPONSE=" + sb.toString()+"=end");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.disconnect();
		}

		return sb.toString();
	}

	public static void main(String[] arg) {
		System.out.println("START Connection ");
		UrlConSessionTest test = new UrlConSessionTest();
		String cookie = test.firstCon();
		
		test.conAfterGetSession(cookie);
		
		System.out.println("END Cookie="+cookie);
	}
}
