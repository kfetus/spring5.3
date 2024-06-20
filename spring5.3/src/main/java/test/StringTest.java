package test;

public class StringTest {

	public static void main(String[] arg) {
		testString();
	}
	
	public static void testString() {
		String temp = "&lt;script&gt;alert&#40;\"hi\"&#41;&lt;/script&gt;";
		System.out.println(temp);
		temp = temp.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&#40;", "(").replaceAll("&#41;", ")");
		System.out.println(temp);
	}
}
