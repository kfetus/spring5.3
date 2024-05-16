package base.biz.menu;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper {

	
	public int selectMenuListCnt(HashMap<String,Object> map);

	public List<HashMap<String,String>> selectMenuList(HashMap<String,Object> map);

}
