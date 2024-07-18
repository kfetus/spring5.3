package base.comm.vo;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommonFBVO {

	//응답 코드
	@JsonProperty("RESCODE")
	String RESCODE;
	//응답 메세지
	@JsonProperty("RESMSG")
	String RESMSG;
	//조회시 전체 카운트
	@JsonProperty("RESULT_TOTAL_CNT")
	String RESULT_TOTAL_CNT;
	//조회 리스트
	@JsonProperty("RESULT_LIST")
	List<HashMap<String, String>> RESULT_LIST;
	//조회 리스트의 카운트(페이징된 카운트)
	@JsonProperty("RESULT_SIZE")
	String RESULT_SIZE;
	//변경된 카운트
	@JsonProperty("CHANGE_COUNT")
	String CHANGE_COUNT;
	
	public String getRESCODE() {
		return RESCODE;
	}
	public void setRESCODE(String rESCODE) {
		RESCODE = rESCODE;
	}
	public String getRESMSG() {
		return RESMSG;
	}
	public void setRESMSG(String rESMSG) {
		RESMSG = rESMSG;
	}
	public String getRESULT_TOTAL_CNT() {
		return RESULT_TOTAL_CNT;
	}
	public void setRESULT_TOTAL_CNT(String rESULT_TOTAL_CNT) {
		RESULT_TOTAL_CNT = rESULT_TOTAL_CNT;
	}
	public List<HashMap<String, String>> getRESULT_LIST() {
		return RESULT_LIST;
	}
	public void setRESULT_LIST(List<HashMap<String, String>> rESULT_LIST) {
		RESULT_LIST = rESULT_LIST;
	}
	public String getRESULT_SIZE() {
		return RESULT_SIZE;
	}
	public void setRESULT_SIZE(String rESULT_SIZE) {
		RESULT_SIZE = rESULT_SIZE;
	}
	public String getCHANGE_COUNT() {
		return CHANGE_COUNT;
	}
	public void setCHANGE_COUNT(String cHANGE_COUNT) {
		CHANGE_COUNT = cHANGE_COUNT;
	}
	@Override
	public String toString() {
		return "CommonFBVO [RESCODE=" + RESCODE + ", RESMSG=" + RESMSG + ", RESULT_TOTAL_CNT=" + RESULT_TOTAL_CNT
				+ ", RESULT_LIST=" + RESULT_LIST + ", RESULT_SIZE=" + RESULT_SIZE + ", CHANGE_COUNT=" + CHANGE_COUNT
				+ "]";
	}
	
}
