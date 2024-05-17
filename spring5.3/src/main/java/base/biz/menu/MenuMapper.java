package base.biz.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper {

	
	public int selectMenuListCnt(HashMap<String,Object> map);

	public List<HashMap<String,String>> selectMenuList(HashMap<String,Object> map);
	
	public int updateMenuList(List<Map<String, String>> list);
	
	public int insertMenuList(List<Map<String, String>> list);
	
	public int deleteMenuList(List<Map<String, String>> list);
	
	public int selectMenuOne(Map<String,String> map);
	
	public int insertMenuOne(Map<String, String> map);


}
