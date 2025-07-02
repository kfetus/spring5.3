package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClientTest {

	public static void main(String[] args) {
		System.out.println("START ============");
		try {
			connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("END ============");
	}

	public static void connect() throws Exception {

		URL url = new URL("https://kr.tradingview.com/scripts/page-2/?script_type=strategies");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");

		BufferedReader br;
		int resultCode = conn.getResponseCode();
		System.out.println(resultCode);

		if (resultCode >= 200 && resultCode < +300) { // 정상인 경우
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else { // 에러
			br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		StringBuilder responseBuilder = new StringBuilder();
		String line;
		int count = 1;
		while ((line = br.readLine()) != null) {
			responseBuilder.append(line);
		}
		
		System.out.println(responseBuilder.toString());
		br.close();
		conn.disconnect();
	}
}
