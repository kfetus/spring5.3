package base.biz.pms;

import java.util.HashMap;
import java.util.List;

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

}
