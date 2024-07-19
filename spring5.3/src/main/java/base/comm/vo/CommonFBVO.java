package base.comm.vo;

import java.util.HashMap;
import java.util.List;

//현재 PropertyNamingStrategy에서는 대문자 snakeCaseStrategy를 지원하지 않는다.
//차선책으로 아래의 JsonProperty를 하면 한개의 엔티티가 두개씩 리턴되므로 오버헤드가 심해서 사용하지 말자. 걍 camel로 가자
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CommonFBVO {

	//응답 코드
//	@JsonProperty("RESCODE")
	String resCode;
	//응답 메세지
//	@JsonProperty("RESMSG")
	String resMsg;
	//조회시 전체 카운트
//	@JsonProperty("RESULT_TOTAL_CNT")
	String resultTotalCnt;
	//조회 리스트
//	@JsonProperty("RESULT_LIST")
	List<HashMap<String, String>> resultList;
	//조회 리스트의 카운트(페이징된 카운트)
//	@JsonProperty("RESULT_SIZE")
	String resultSize;
	//변경된 카운트
//	@JsonProperty("CHANGE_COUNT")
	String changeCount;
	
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	public String getResultTotalCnt() {
		return resultTotalCnt;
	}
	public void setResultTotalCnt(String resultTotalCnt) {
		this.resultTotalCnt = resultTotalCnt;
	}
	public List<HashMap<String, String>> getResultList() {
		return resultList;
	}
	public void setResultList(List<HashMap<String, String>> resultList) {
		this.resultList = resultList;
	}
	public String getResultSize() {
		return resultSize;
	}
	public void setResultSize(String resultSize) {
		this.resultSize = resultSize;
	}
	public String getChangeCount() {
		return changeCount;
	}
	public void setChangeCount(String changeCount) {
		this.changeCount = changeCount;
	}
	@Override
	public String toString() {
		return "CommonFBVO [resCode=" + resCode + ", resMsg=" + resMsg + ", resultTotalCnt=" + resultTotalCnt
				+ ", resultList=" + resultList + ", resultSize=" + resultSize + ", changeCount=" + changeCount + "]";
	}
	

	
}
