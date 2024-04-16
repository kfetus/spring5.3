package base.comm.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@ResponseBody
public class SystemSetController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemSetController.class);
	
//	@Autowired
//	private SessionManager sessionManager;
	
	@Autowired
	private SystemSetServiceImpl systemSetService;
	
	@RequestMapping(value = "/menuList.do")
	public Map<String,Object> menuList(@RequestBody  HashMap<String,String> map) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ menuList 시작=" + map);
		Map<String , Object> retMap = new HashMap<String,Object>();
		
		List<HashMap<String,String>> resultList = systemSetService.selectMenuList(map);

		retMap.put("RESCODE","0000");
		retMap.put("RESMSG","");
		retMap.put("RESULT_SIZE",resultList.size());
		retMap.put("RESULT_LIST",resultList);

		LOGGER.debug("@@@@@@@@@@@ menuList 종료"+ retMap);
		return retMap;
	}
}
