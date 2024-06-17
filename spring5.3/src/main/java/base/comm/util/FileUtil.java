package base.comm.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

	/*
	 * spring 은 static 에 @value를 지원하지 않는다. static은 jvm의 class area에 저장 되는데 spring 이 기본 로딩 되는 ApplicationContext가 
	 * 로딩되기 전이므로 해당 값을 셋팅할 수 없다. 굳이 억지로 넣을려면 @Component ,@RestController 같은 Annotation을 넣어서 Spring이 Scan 하게 하고
	 * setter 함수를 추가하고 해당 값에 @value를 넣어서 억지로 셋팅되게 해야 한다.
	*/
	private static List<String> fielExtensionList;
	
	@Value("#{controlsite['file.fileExtension']}")
	private void setMsghi(String fileExtensionArray) {
		
		LOGGER.debug("fileExtensionArray");
		FileUtil.fielExtensionList = new ArrayList<>(Arrays.asList(fileExtensionArray.split(",")));
	}
	
	/* https://www.baeldung.com/spring-inject-static-field 참고. static 에 propery 파일 셋팅하기
	    @Value("#{msg['msg.hi']}")
	    private String name;
	
	    private static String NAME_STATIC;
	
	    @Value("#{msg['msg.hi']}")
	    public void setNameStatic(String name){
	    	FileUtil.NAME_STATIC = name;
	    }
	*/
	
	public static boolean checkUploadFileExtension(MultipartFile multiFile) throws Exception{
		
		LOGGER.debug("checkUploadFileExtension="+multiFile);

		if(multiFile == null) {
			return true;
		}
		
		String originalFilename = multiFile.getOriginalFilename();
		int pointIndex = originalFilename.lastIndexOf(".");
		LOGGER.debug("pointIndex="+pointIndex);
		//확장자가 없을 경우
		if(pointIndex < 0) {
			return false;
		}
		//확장자가 허용되지 않을 경우
		String fileExtension = originalFilename.substring(pointIndex + 1).toLowerCase();
		if( !fielExtensionList.contains(fileExtension) ){
			return false;	
		}
	    return true;
	}
	
}
