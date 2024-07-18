package base.biz.sample;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SampleMapper {

	public List<HashMap<String,String>> selectList(HashMap<String,Object> map);
	
	public int insertUploadTestList(List<HashMap<String, String>> list);
}
