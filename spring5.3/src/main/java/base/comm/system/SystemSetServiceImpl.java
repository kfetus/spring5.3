package base.comm.system;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("systemSetService")
public class SystemSetServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemSetServiceImpl.class);

	@Autowired
	private SystemSetMapper systemSetMapper;
	
	public List<HashMap<String,String>> selectMenuList(HashMap<String,String> map) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ selectMenuList Service 시작=" + map);
		List<HashMap<String,String>> result = systemSetMapper.selectMenuList(map);
		return result;
	}
}
