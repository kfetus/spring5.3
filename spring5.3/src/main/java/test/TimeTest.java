package test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class TimeTest {

	public static void main(String[] args) {
		LocalDateTime nowld = LocalDateTime.now();
		System.out.println(nowld);
		int year = nowld.getYear();
		String month = nowld.getMonth().toString();
		int monthValue = nowld.getMonthValue();
		int dayOfMonth = nowld.getDayOfMonth();
		int dayOfYear = nowld.getDayOfYear();
		String dayOfWeek = nowld.getDayOfWeek().toString();
		int dayOfWeekValue = nowld.getDayOfWeek().getValue();
		int hour = nowld.getHour();
		int minute = nowld.getMinute();
		int second = nowld.getSecond();
		System.out.println("년 : " + year+"월 : " + month + "(" + monthValue + ")"+"일(월기준) : " + dayOfMonth+"일(년기준) : " + dayOfYear+"요일 : " + dayOfWeek + "(" + dayOfWeekValue + ")"
		+"시간 : " + hour+"분 : " + minute+"초 : " + second);

		
		String formatedNowFull = nowld.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));
		System.out.println(formatedNowFull);
		System.out.println(nowld.format(DateTimeFormatter.ofPattern("yyyy년MM월dd일HH시mm분ss초")));
		System.out.println(nowld.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				
		
		LocalTime now = LocalTime.now();
		System.out.println(now);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
		String formatedNow = now.format(formatter);
		System.out.println(formatedNow);

		// jdk1.8 이전
		Date now18 = new Date();
		System.out.println(now18);
		SimpleDateFormat formatter18 = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
		String formatedNow18 = formatter18.format(now18);
		System.out.println(formatedNow18);

		Date nowCal = Calendar.getInstance().getTime();
		System.out.println(nowCal);
		SimpleDateFormat formatterCal = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
		String formatedNowCal = formatterCal.format(nowCal);
		System.out.println(formatedNowCal);
		
		long cTime = System.currentTimeMillis();
		System.out.println("System.currentTimeMillis()"+cTime);
		System.out.println(new Date(cTime));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(new Date(cTime)));
		
	}

}
