package base.comm.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

	/**
	 * getNowDateFormat("yyyy-MM-dd HH:mm:ss") => 2024-04-23 15:15:10
	 * @param format
	 * @return
	 */
	public static String getNowDateFormat(String format) {
		LocalDateTime nowld = LocalDateTime.now();
		return nowld.format(DateTimeFormatter.ofPattern(format));
	}
	
	/**
	 * getNowTimeFormat("HH:mm:ss") => 15:18:38
	 * getNowTimeFormat("HHmmss") => 151838
	 * @return
	 */
	public static String getNowTimeFormat(String format) {
		LocalTime now = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return now.format(formatter);
	}
	
	public static void main(String arg[]) {
		System.out.println(getNowDateFormat("yyyy-MM-dd HH:mm:ss"));
		System.out.println(getNowTimeFormat("HH:mm:ss"));
		System.out.println(getNowTimeFormat("HHmmss"));
		
		LocalTime time1 = LocalTime.of(12, 29, 58);
		LocalTime time2 = LocalTime.of(12, 30, 1);
		LocalTime time3 = LocalTime.now();
		LocalTime time4 = LocalTime.of(16, 11, 40);
		System.out.println(time1);
		System.out.println(time2);
		Duration duration = Duration.between( time1, time2 ); 
		System.out.println(duration.getNano());
		System.out.println(duration.getSeconds());
		System.out.println(Duration.between( time4, time3 ).getSeconds());
	}
}
