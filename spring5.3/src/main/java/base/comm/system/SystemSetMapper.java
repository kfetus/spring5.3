package base.comm.system;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemSetMapper {

	public List<HashMap<String,String>> selectMenuList(HashMap<String,String> map);
}
