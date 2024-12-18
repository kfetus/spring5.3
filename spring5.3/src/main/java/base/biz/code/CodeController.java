package base.biz.code;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class CodeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CodeController.class);
	
	@Value("#{errorCode['noData']}")
	private String noData ;

	@Value("#{errorCode['success']}")
	private String successCode ;
	
	@Autowired
	private CodeServiceImpl codeService;
	
	@RequestMapping(value = "/common/codeList.do")
	public Map<String,Object> codeList(@RequestBody  HashMap<String,String> map) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ commonCodeList 시작=" + map);
		Map<String , Object> retMap = new HashMap<String,Object>();
		
		List<HashMap<String,String>> resultList = codeService.selectCodeList(map);

		if( resultList.size() == 0) {
			retMap.put("RESCODE",noData);
			retMap.put("RESMSG","데이타 없습니다.");
			retMap.put("RESULT_SIZE","0");
			return retMap;
		} else {
			retMap.put("RESCODE",successCode);
			retMap.put("RESMSG","정상적으로 처리되었습니다.");
			retMap.put("RESULT_SIZE",resultList.size());
			retMap.put("RESULT_LIST",resultList);
		}

		LOGGER.debug("@@@@@@@@@@@ commonCodeList 종료"+ retMap);
		return retMap;
	}
}
