package base.biz.pms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("pmsService")
public class PmsServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(PmsServiceImpl.class);

	@Autowired
	private PmsMapper pmsMapper;
	
	public int selectPmsListCnt(HashMap<String,Object> map) throws Exception {
		LOGGER.debug("@@@@@@@@@@@@@ selectPmsListCnt param data=" + map);
		int result = pmsMapper.selectPmsListCnt(map);
		return result;
	}
	
	public List<HashMap<String,String>> selectPmsList(HashMap<String,Object> map) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ selectPmsList Service 시작=" + map);
		List<HashMap<String,String>> result = pmsMapper.selectPmsList(map);
		return result;
	}
	
	public int changePmsList(List<Map<String, String>> list) throws Exception {
		//의미 없는 카운트. multi update를 해도 건수는 1건으로 잡힘. insert는 제대로 잡힘
		int count = 0;
		
		List<Map<String, String>> updateList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> insertList = new ArrayList<Map<String, String>>();
		
		for(int i = 0 ; i < list.size();i++) {
			if( "I".equals(list.get(i).get("MODE")) ) {
				insertList.add(list.get(i));
			} else {
				updateList.add(list.get(i));
			}
		}
		if(updateList.size()>0) {
			count = pmsMapper.updatePmsList(updateList);
		}
		if(insertList.size() > 0) {
			count += pmsMapper.insertPmsList(insertList);
		}
		
		return count;
	}

}
