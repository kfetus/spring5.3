package base.biz.code;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CodeMapper {

	public List<HashMap<String,String>> selectCodeList(HashMap<String,String> map);
}
