package base.biz.pms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PmsMapper {

	public int selectPmsListCnt(HashMap<String,Object> map);
	
	public List<HashMap<String,String>> selectPmsList(HashMap<String,Object> map);
	
	public int updatePmsList(List<Map<String, String>> list);
	
	public int insertPmsList(List<Map<String, String>> list);
	
	public int deletePmsOne(ArrayList<String> seqList);
}
