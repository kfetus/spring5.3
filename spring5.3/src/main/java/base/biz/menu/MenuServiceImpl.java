package base.biz.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	//업데이트만. 인서트는 따로 구현. 개개로 insert 중복확인도 필요
	public void updateMenuList(List<Map<String, String>> list) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ updateMenuList 시작=" +list);
		menuMapper.updateMenuList(list);
	}
	
	
	public int saveMenuOne( Map<String, String> map) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ saveMenuList 시작=" +map);
		int resultCount = 0;
		//중복조회
		int result = menuMapper.selectMenuOne(map);
		if(result == 0) {
			//데이터 저장
			resultCount = menuMapper.insertMenuOne(map);
		}
		return resultCount;
	}
	
	public int deleteMenuList(List<Map<String,String>> list) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ selectMenuList Service 시작=" + list);
		int result = menuMapper.deleteMenuList(list);
		return result;
	}

}
