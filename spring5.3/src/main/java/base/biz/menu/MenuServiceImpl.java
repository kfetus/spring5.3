package base.biz.menu;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("menuService")
public class MenuServiceImpl {
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);

	@Autowired
	private MenuMapper menuMapper;
	
	public int selectMenuListCnt(HashMap<String,Object> map) throws Exception {
		LOGGER.debug("@@@@@@@@@@@@@ selectMenuListCnt param data=" + map);
		int result = menuMapper.selectMenuListCnt(map);
		return result;
	}
	
	public List<HashMap<String,String>> selectMenuList(HashMap<String,Object> map) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ selectMenuList Service 시작=" + map);
		List<HashMap<String,String>> result = menuMapper.selectMenuList(map);
		return result;
	}

}
