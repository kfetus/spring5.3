package base.biz.sample;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import base.biz.board.BoardServiceImpl;

@Service("sampleService")
public class SampleServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoardServiceImpl.class);

	@Autowired
	private SampleMapper sampleMapper;
	
	public List<HashMap<String,String>> selectList(HashMap<String,Object> map) throws Exception {
		LOGGER.debug("@@@@@@@@@@@@@ selectList param data=" + map);
		List<HashMap<String,String>> result = sampleMapper.selectList(map);
		return result;
	}

	public int insertUploadTestList(List<HashMap<String, String>> list) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ insertUploadTestList 시작=" +list);
		int count = sampleMapper.insertUploadTestList(list);
		return count;
	}
}
