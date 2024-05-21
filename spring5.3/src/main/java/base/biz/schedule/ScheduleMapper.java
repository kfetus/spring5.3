package base.biz.schedule;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScheduleMapper {

	public List<HashMap<String,String>> selectScheduleList(HashMap<String,String> paramMap);
	
	public List<HashMap<String,String>> selectScheduleOneDay(HashMap<String,String> paramMap);
	
	public int deletescheduleOne(HashMap<String,Object> paramMap);
	
	public int deletescheduleDay(HashMap<String,Object> paramMap);
	
	public int insertSchedule(List<HashMap<String,String>> list);
	
	public int upsertSchedule(List<HashMap<String,String>> list);
}
