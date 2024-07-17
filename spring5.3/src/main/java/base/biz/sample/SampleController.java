package base.biz.sample;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import base.comm.util.ExcelUtil;
import base.comm.util.FileUtil;

/**
 * 기본 테스트용 클래스
 * @author USER
 *
 */
@Controller
public class SampleController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

	@Autowired
	private SampleServiceImpl sampleService;

	@Value("#{errorCode['success']}")
	private String successCode ;
	
	@Value("#{errorCode['biz.nomalError']}")
	private String bizNomalError;	
	
	/**
	 * SockJs 방식의 브라우저용 websocket 테스트 샘플
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sockJsSample.do")
	public String sockJsSample(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("####################### sockJsSample");
		return "websocket/sockJsSample";
	}

	/**
	 * websocket 일반적인 방식(javax.websocket)의 websocket 테스트 샘플
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/javaxWebsocketSample.do")
	public String javaxWebsocketSample(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("####################### javaxWebsocketSample");
		return "websocket/javaxWebsocketSample";
	}
	
	/**
	 * 샘플용 리스트 페이지 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/baseList.do")
	public ModelAndView baseList(@RequestParam HashMap<String,Object> map) throws Exception {
		LOGGER.debug("########## baseList  #############"+map);
		List<HashMap<String,String>> list = sampleService.selectList(map);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list );
		mv.addObject("pageTitle", "게시판 목록" );
		
		mv.setViewName("sample/baseList");
		return mv;
	}
	
	/**
	 * url에 들어온 url 그대로 view로 매핑. 파라미터들 그대로 전달
	 * @param map
	 * @param subPath
	 * @param viewName
	 * @return
	 */
	@RequestMapping("/urlToView/{subPath}/{viewName}")
	public ModelAndView subPathUrlToVies( @RequestParam HashMap<String,Object> map,@PathVariable String subPath,@PathVariable String viewName) {
		LOGGER.debug("########## subPathUrlToVies  #############"+map);
		ModelAndView mv = new ModelAndView();
		String viewFileName = viewName.substring(0, viewName.indexOf("."));
		mv.setViewName(subPath+"/"+viewFileName);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "/excelUploadSample.do")
	public Map<String, Object> excelUploadSample(@RequestPart(required = false)  MultipartFile multiFiles, HttpServletRequest req) throws Exception {
		LOGGER.debug("@@@@@@@@@@@ excelUploadSample 시작 @@@@@@@@@@@");
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		boolean checkState = FileUtil.checkUploadFileExtension(multiFiles);
		if(!checkState) {
			retMap.put("RESCODE", bizNomalError);
			retMap.put("RESMSG", "잘못된 파일을 업로드 하였습니다."+multiFiles.getOriginalFilename());
			LOGGER.debug("@@@@@@@@@@@ excelUploadSample 에러발생=" + retMap);
			return retMap;
		}

		/**
		 * Excel File Parsing은 두가지 방식이 있음. 
		 * 1. multiFiles.getInputStream()
		 * 2. File
		 * 1번은 업로드 파일 그대로 parsing. 2번은 File을 Disk에 생성해서 진행. 어떤게 메모리 최적화를 해서
		 * 서버 다운을 막을지는 모르겠음
		 */
		
		LOGGER.debug("@@@@@@@@@@@ excelUploadSample file명=" + multiFiles.getOriginalFilename());
		//1번 방식
		File file = new File(multiFiles.getOriginalFilename());
		multiFiles.transferTo(file);
		ExcelUtil.readExcel(multiFiles, "sheet1");

		//2번 방식
//		File file = new File("C:/Users/PMG/Desktop/down/"+multiFiles.getOriginalFilename());
//		ExcelUtil.readExcel(file, "sheet1");

		file.delete();
		retMap.put("RESCODE", successCode);
		retMap.put("RESMSG", "업로드 완료.");

		LOGGER.debug("@@@@@@@@@@@ excelUploadSample 종료" + retMap);
		return retMap;
	}
	
	
}
