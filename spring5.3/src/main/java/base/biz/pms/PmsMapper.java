package base.biz.pms;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PmsMapper {

	public int selectPmsListCnt(HashMap<String,Object> map);
	
	public List<HashMap<String,String>> selectPmsList(HashMap<String,Object> map);
}
