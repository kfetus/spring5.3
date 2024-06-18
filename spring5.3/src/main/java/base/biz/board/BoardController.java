package base.biz.board;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import base.comm.SystemConstance;
import base.comm.util.FileUtil;
import base.comm.util.SessionManager;
import base.comm.vo.UserVO;

@Controller
@ResponseBody
public class BoardController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private BoardServiceImpl boardService;

	@Value("#{controlsite['file.sample']}")
	private String filesample;

	@Value("#{msg['msg.hi']}")
	public String msghi;

	@RequestMapping(value = "/boardList.do")
	public Map<String, Object> boardList(@RequestBody HashMap<String, Object> map) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ boardList 시작=" + map);
		Map<String, Object> retMap = new HashMap<String, Object>();

		LOGGER.debug("@@@@@@@@@@@ boardList filesample=" + filesample);
		LOGGER.debug("@@@@@@@@@@@ boardList msghi=" + msghi);
		
		int totalCnt = boardService.selectBoardListCnt(map);
		
		int nowPage = 0;
		int pageListCnt = SystemConstance.DEFAULT_PAGE_LIST_COUNT;
		int startIdx = 0;
		
		if (totalCnt == 0) {
			retMap.put("RESCODE", "0000");
			retMap.put("RESMSG", "데이타 없습니다.");
			retMap.put("RESULT_SIZE", "0");
			return retMap;
		} else {
			
			if( !ObjectUtils.isEmpty(map.get("nowPage")) && StringUtils.hasText(String.valueOf(map.get("nowPage")))) {
				nowPage = Integer.parseInt(String.valueOf(map.get("nowPage")));
				if(nowPage > 0) {
					nowPage = nowPage -1;
				}
			}
			if( !ObjectUtils.isEmpty(map.get("pageListCnt")) && StringUtils.hasText(String.valueOf(map.get("pageListCnt")))) {
				pageListCnt = Integer.parseInt(String.valueOf(map.get("pageListCnt")));
			}
			
			
			startIdx = nowPage * pageListCnt;
			map.put("startIdx", startIdx);
			map.put("pageListCnt", pageListCnt);
			List<HashMap<String, String>> resultList = boardService.selectBoardList(map);

			
			retMap.put("RESCODE", "0000");
			retMap.put("RESMSG", "");
			retMap.put("RESULT_SIZE", resultList.size());
			retMap.put("RESULT_LIST", resultList);
			retMap.put("RESULT_TOTAL_CNT", totalCnt);
		}

		LOGGER.debug("@@@@@@@@@@@ boardList 종료" + retMap);
		return retMap;
	}

	@RequestMapping(value = "/boardOne.do")
	public Map<String, Object> selectBoardOne(@RequestBody HashMap<String, String> map) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ selectBoardOne 시작=" + map);
		Map<String, Object> retMap = new HashMap<String, Object>();

		String seq = map.get("SEQ");

		HashMap<String, String> resultData = boardService.selectBoardOne(seq);

		retMap.put("RESCODE", "0000");
		retMap.put("RESMSG", "");
		retMap.put("RESULT_DATA", resultData);

		LOGGER.debug("@@@@@@@@@@@ selectBoardOne 종료" + retMap);
		return retMap;
	}

/*	
	@RequestMapping(value = "/boardFileOne.do")
	public ResponseEntity<ByteArrayResource> selectBoardFileOne(@RequestParam HashMap<String, String> map) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ selectBoardFileOne 시작=" + map);

		HashMap<String, Object> resultData = boardService.selectBoardFileOne(map);
		LOGGER.debug("@@@@@@@@@@@ selectBoardFileOne DB 조회 결과=" + resultData);
		
	    byte[] fileData = (byte[]) resultData.get("FILE_DATA");
		
	    if (fileData != null) {
	      ByteArrayResource resource = new ByteArrayResource(fileData);
	      return ResponseEntity.ok()
	          .contentType(MediaType.APPLICATION_OCTET_STREAM)
	          .contentLength(fileData.length)
	          .body(resource);
	    } else {
	      return ResponseEntity.notFound().build();
	    }		
	}
*/	
	/**
	 * 
	 * @설명 : 업로드 파일 여러개일경우 샘플
	 * @param title
	 * @param bodyText
	 * @param category
	 * @param multiFiles
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertBoardOne1.do")
	public Map<String, Object> insertBoardOne1(@RequestParam String title,@RequestParam String bodyText,@RequestParam String category
			,@RequestPart MultipartFile[] multiFiles, HttpServletRequest req) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ insertBoardOne 시작title=" + title);
		LOGGER.debug("@@@@@@@@@@@ insertBoardOne 시작bodyText=" + bodyText);
		LOGGER.debug("@@@@@@@@@@@ insertBoardOne 시작category=" + category);
		LOGGER.debug("@@@@@@@@@@@ insertBoardOne 시작=" + multiFiles);
		if(multiFiles != null) {
			for(int i=0; i<multiFiles.length; i++) {
				MultipartFile file = multiFiles[i];
	            
				String originName = file.getOriginalFilename(); // ex) 파일.jpg
				String fileExtension = originName.substring(originName.lastIndexOf(".") + 1); // ex) jpg
				originName = originName.substring(0, originName.lastIndexOf(".")); // ex) 파일
				long fileSize = file.getSize(); // 파일 사이즈
				
				LOGGER.debug("originName= " + originName);
				LOGGER.debug("fileExtension= " + fileExtension);
				LOGGER.debug("fileSize= " + fileSize);
				LOGGER.debug("file.getName()= " + file.getName());
			}		
		}
		
		Map<String, Object> retMap = new HashMap<String, Object>();


		retMap.put("RESCODE", "0000");
		retMap.put("RESMSG", "");

		LOGGER.debug("@@@@@@@@@@@ insertBoardOne 종료" + retMap);
		return retMap;
	}
	
	
	//vue든 react 든 client에서 파일 업로드시 new FormData() 선언해서 append 로 값을 할당해서 보내는데 이렇게 받아야 함. RequestBody로는 받지 못함.
	@RequestMapping(value = "/insertBoardOne.do")
	public Map<String, Object> insertBoardOne(@RequestParam String title,@RequestParam String bodyText,@RequestParam String category
			,@RequestPart(required = false)  MultipartFile multiFiles, HttpServletRequest req) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ insertBoardOne 시작 @@@@@@@@@@@");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		boolean checkState = FileUtil.checkUploadFileExtension(multiFiles);
		if(!checkState) {
			retMap.put("RESCODE", "9998");
			retMap.put("RESMSG", "잘못된 파일을 업로드 하였습니다."+multiFiles.getOriginalFilename());
			LOGGER.debug("@@@@@@@@@@@ insertBoardOne 에러발생=" + retMap);
			return retMap;
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title",title);
		map.put("bodyText",bodyText);
		map.put("category",category);
		LOGGER.debug("@@@@@@@@@@@ insertBoardOne 시작map=" + map);

		UserVO vo = sessionManager.getUserInfo(req);
		if (vo == null) {
			retMap.put("RESCODE", "9998");
			retMap.put("RESMSG", "로그인 정보가 없습니다.");
			LOGGER.debug("@@@@@@@@@@@ insertBoardOne 에러발생=" + retMap);
			return retMap;
		} else {
			map.put("userNo", String.valueOf(vo.getUserNo()));
		}
		map.put("multiFiles", multiFiles);
		
		
		int result = boardService.insertBoardOne(map);
/*
		LOGGER.debug("@@@@@@@@@@@ insertBoardOne multiFiles=" + multiFiles);
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

//			File file2 = new File("D:\\"+originalFilename);
//			multiFiles.transferTo(file2);

			
			fileMap.put("boardSeq", result);
			fileMap.put("fileName", fileName);
			fileMap.put("fileData", multiFiles.getBytes());
			fileMap.put("fileSize", fileSize);
			fileMap.put("fileExtension", fileExtension);
			
			boardService.insertBoardFile(fileMap);
		}
		
		LOGGER.debug("@@@@@@@@@@@ insertBoardOne result" + result);
*/
		retMap.put("RESCODE", "0000");
		retMap.put("RESMSG", "");
		retMap.put("RESULT_CNT", result);

		LOGGER.debug("@@@@@@@@@@@ insertBoardOne 종료" + retMap);
		return retMap;
	}

	@RequestMapping(value = "/updateBoardOne.do")
	public Map<String, Object> updateBoardOne(@RequestBody HashMap<String, String> map, HttpServletRequest req)
			throws Exception {
		LOGGER.debug("@@@@@@@@@@@ updateBoardOne 시작=" + map);
		Map<String, Object> retMap = new HashMap<String, Object>();

		UserVO vo = sessionManager.getUserInfo(req);
		if (vo == null) {
			retMap.put("RESCODE", "9998");
			retMap.put("RESMSG", "로그인 정보가 없습니다.");
			return retMap;
		}

		String seq = map.get("seq");
		HashMap<String, String> resultData = boardService.selectBoardOne(seq);
		String userNo = String.valueOf(resultData.get("USER_NO"));

		int tempUserNo = Integer.parseInt(userNo);

		if (tempUserNo != vo.getUserNo()) {
			retMap.put("RESCODE", "9997");
			retMap.put("RESMSG", "수정 권한이 없습니다.");
			return retMap;
		}

		int result = boardService.updateBoardOne(map);

		retMap.put("RESCODE", "0000");
		retMap.put("RESMSG", "");
		retMap.put("RESULT_CNT", result);

		LOGGER.debug("@@@@@@@@@@@ updateBoardOne 종료" + retMap);
		return retMap;
	}

	@RequestMapping(value = "/oneFileUpload")
	public Map<String, Object> oneFileUpload(@RequestParam("files") MultipartFile[] files,
			@RequestBody HashMap<String, String> map) throws Exception {

		Map<String, Object> retMap = new HashMap<String, Object>();
		LOGGER.debug("[FileUploadController.singleFileUpload] 파일개수 : {}", files.length);

		List<String> fileList = new ArrayList<>();

		if (files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getOriginalFilename();
				String fileExt = fileName.substring(fileName.lastIndexOf("."));

				LOGGER.debug("[FileUploadController.singleFileUpload] 파일 이름 : {}", fileName);
				LOGGER.debug("[FileUploadController.singleFileUpload] 파일 확장자 : {}", fileExt);
				LOGGER.debug("[FileUploadController.singleFileUpload] 파일 크기 : {}", files[i].getSize());

				try (InputStream is = files[i].getInputStream();) {

				} catch (Exception ex) {
					throw new RuntimeException("file Save Error");
				}
				fileList.add(fileName);
			}
		}
		return retMap;
	}

	@RequestMapping(value = "/deleteBoardOne.do")
	public Map<String, Object> deleteBoardOne(@RequestBody HashMap<String, String> map, HttpServletRequest req)
			throws Exception {
		LOGGER.debug("@@@@@@@@@@@ deleteBoardOne 시작=" + map);
		Map<String, Object> retMap = new HashMap<String, Object>();

		String seq = map.get("SEQ");
		String userNo = StringUtils.trimWhitespace(map.get("userNo"));

		UserVO vo = sessionManager.getUserInfo(req);

		if (vo == null) {
			retMap.put("RESCODE", "9998");
			retMap.put("RESMSG", "로그인 정보가 없습니다.");
			LOGGER.debug("@@@@@@@@@@@ insertBoardOne 에러발생=" + retMap);
			return retMap;
		} else {
			int tempUserNo = Integer.parseInt(userNo);
			LOGGER.debug("@@@@@@@@@@@ deleteBoardOne tempUserNo=" + tempUserNo + "|" + userNo + "|");
			LOGGER.debug("@@@@@@@@@@@ deleteBoardOne userNo=" + (tempUserNo != vo.getUserNo()));

			if (tempUserNo != vo.getUserNo()) {
				retMap.put("RESCODE", "9997");
				retMap.put("RESMSG", "삭제 권한이 없습니다.");
				return retMap;
			}
		}

		int result = boardService.deleteBoardOne(seq);

		retMap.put("RESCODE", "0000");
		retMap.put("RESMSG", "");
		retMap.put("RESULT_CNT", result);

		LOGGER.debug("@@@@@@@@@@@ updateBoardOne 종료" + retMap);
		return retMap;
	}
}
