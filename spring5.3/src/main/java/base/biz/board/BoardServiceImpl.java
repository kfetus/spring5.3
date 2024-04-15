package base.biz.board;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("boardService")
public class BoardServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoardServiceImpl.class);

	@Autowired
	private BoardMapper boardMapper;

	public int insertBoardOne(HashMap<String,Object> map) throws Exception {
		LOGGER.debug("@@@@@@@@@@@@@ insertBoardOne data=" + map);
		
		boardMapper.insertBoardOne(map);
		String boardSeq = String.valueOf(map.get("boardSeq"));
		int result = Integer.parseInt(boardSeq);

		MultipartFile multiFiles = (MultipartFile)map.get("multiFiles");
		if(multiFiles != null) {
			HashMap<String, Object> fileMap = new HashMap<String, Object>();

			String originalFilename = multiFiles.getOriginalFilename();
			int pointIndex = originalFilename.lastIndexOf(".");
			String fileExtension = originalFilename.substring(pointIndex + 1);
			String fileName = originalFilename.substring(0, pointIndex);
			long fileSize = multiFiles.getSize(); // 파일 사이즈
			LOGGER.debug("fileName= " + fileName);
			LOGGER.debug("fileExtension= " + fileExtension);
			LOGGER.debug("fileSize= " + fileSize);
			
			fileMap.put("boardSeq", result);
			fileMap.put("fileName", fileName);
			fileMap.put("fileData", multiFiles.getBytes());
			fileMap.put("fileSize", fileSize);
			fileMap.put("fileExtension", fileExtension);
			
			insertBoardFile(fileMap);
		}

		
		return result;
	}
	
	public int insertBoardFile(HashMap<String,Object> map) throws Exception {
		LOGGER.debug("@@@@@@@@@@@@@ insertBoardFile param data=" + map);
		int result = boardMapper.insertBoardFile(map);
		return result;
	}
	
	
	public int selectBoardListCnt(HashMap<String,Object> map) throws Exception {
		LOGGER.debug("@@@@@@@@@@@@@ selectBoardListCnt param data=" + map);
		int result = boardMapper.selectBoardListCnt(map);
		return result;
	}
	
	public List<HashMap<String,String>> selectBoardList(HashMap<String,Object> map) throws Exception {
		List<HashMap<String,String>> result = boardMapper.selectBoardList(map);
		return result;
	}

	public HashMap<String,String> selectBoardOne(String seq) throws Exception {
		HashMap<String,String> result = boardMapper.selectBoardOne(seq);
		return result;
	}

	public HashMap<String,Object> selectBoardFileOne(HashMap<String,String> map) throws Exception {
		HashMap<String,Object> result = boardMapper.selectBoardFileOne(map);
		return result;
	}
	
	
	public int updateBoardOne(HashMap<String,String> map) throws Exception {
		LOGGER.debug("@@@@@@@@@@@@@ updateBoardOne map=" + map);
		int result = boardMapper.updateBoardOne(map);
		return result;
	}
	
	public int deleteBoardOne(String seq) throws Exception {
		LOGGER.debug("@@@@@@@@@@@@@ key=" + seq);
		boardMapper.deleteBoardOne(seq);
		int delRes = boardMapper.deleteBoardFile(seq);
		return delRes;
	}
	

}

