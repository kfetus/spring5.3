package base.biz.schedule;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScheduleMapper {

	public List<HashMap<String,String>> selectScheduleList(HashMap<String,String> paramMap);
	
	public List<HashMap<String,String>> selectScheduleOne(HashMap<String,String> paramMap);
	
	public int deletescheduleOne(String yyyymmdd, String hh, String mm);
	
	public int deletescheduleDay(String yyyymmdd);
	
	public int insertSchedule(List<HashMap<String,String>> list);
	
	public int upsertSchedule(List<HashMap<String,String>> list);
}
