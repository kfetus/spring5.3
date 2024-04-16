package base.biz.schedule;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("scheduleService")
public class ScheduleServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleServiceImpl.class);

	@Autowired
	private ScheduleMapper scheduleMapper;
	
	public List<HashMap<String,String>> selectScheduleList(HashMap<String,String> paramMap) throws Exception {
		LOGGER.debug("@@@@@@@@@@@@@ selectScheduleList paramMap=" + paramMap);
		List<HashMap<String,String>> result = scheduleMapper.selectScheduleList(paramMap);
		return result;
	}

	public List<HashMap<String,String>> selectScheduleOne(HashMap<String,String> paramMap) throws Exception {
		LOGGER.debug("@@@@@@@@@@@@@ selectScheduleOne paramMap=" + paramMap);
		List<HashMap<String,String>> result = scheduleMapper.selectScheduleOne(paramMap);
		return result;
	}

	public int insertSchedule(List<HashMap<String,String>> list, String yyyymmdd) throws Exception {
		LOGGER.debug("@@@@@@@@@@@@@ scheduleUpdate list=" + list);
		
		scheduleMapper.deletescheduleDay(yyyymmdd);
		
		int result = scheduleMapper.insertSchedule(list);
		
		return result;
		
	}

	
	/**
	 * @설명 : 스케쥴 update and insert.
	 *  이 로직에서는 맞지 않는게 pk가 년월일,시,분 으로 설계했기 때문에 화면에서 시,분 만 바꿀경우 삭제 추가가 아니고 추가만 된다. 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int upsertSchedule(List<HashMap<String,String>> list) throws Exception {
		LOGGER.debug("@@@@@@@@@@@@@ scheduleUpdate list=" + list);
		int result = scheduleMapper.upsertSchedule(list);
		return result;
		
	}
}
