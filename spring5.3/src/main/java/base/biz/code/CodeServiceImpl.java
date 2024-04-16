package base.biz.code;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("codeService")
public class CodeServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(CodeServiceImpl.class);

	@Autowired
	private CodeMapper codeMapper;

	public List<HashMap<String,String>> selectCodeList(HashMap<String,String> map) throws Exception {
		LOGGER.debug("@@@@@@@@@@@@@ selectCodeList data=" + map);
		List<HashMap<String,String>> result = codeMapper.selectCodeList(map);
		return result;
	}

}
