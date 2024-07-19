package base.biz.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
		LOGGER.debug("@@@@@@@@@@@ insertUploadTestList 시작="+list.size());
		int count = 0;
		List<HashMap<String, String>> tempList = new ArrayList<HashMap<String, String>>();
		for ( int index = 1 ; index < list.size()+1; index++) {
			tempList.add(list.get(index-1));
			if(index % 10000 == 0) {
				count += sampleMapper.insertUploadTestList(tempList);
				tempList.clear();
				LOGGER.debug("@@@@@@@@@@@ count="+count);
			}
		}
		if(tempList.size() > 0) {
			count += sampleMapper.insertUploadTestList(tempList);
			tempList.clear();
			LOGGER.debug("@@@@@@@@@@@ count="+count);
		}
		
		return count;
	}

	//비동기 서비스 샘플 ThreadPoolTaskExecutor 설정하고 클래스 만들고... 먼가 더 해야 하는데 안함
	@Async("nameAsync")
	public void asyncTest() throws Exception {
		LOGGER.debug("@@@@@@@@@@@ asyncTest 시작 @@@@@@@@@@@" );

		Thread.sleep(1000);
		LOGGER.debug("@@@@@@@@@@@ asyncTest 완료 @@@@@@@@@@@" );
	}

}
