package base.comm.websocket.javax;

/**
 * 경매용 메세지
 * @author USER
 *
 */
public class BidMessage {

	// 사용자 id
	public String bidId;
	// 사용자 이름
	public String userName;
	//출품번호
	public String entryNumber;
	//경매상태(준비:R,시작:S,낙찰:O,유찰:F,경매전체종료:E)
	public String auctionState;
	// 경합 인원 수(신호등)
	public int competePeopleNum;
	// 낙찰 권리 여부. 낙찰된건 아님. 현재 진행중에 낙찰 권리(최고금액)을 넣은 상태
	public boolean hasRight;
	// 최저 판매 금액(입찰 시작 가격)
	public int minAuctionMoney;
	// 현재 진행 호가 금액 
	public int nowAuctionMoney;
	// 희망 낙찰 금액
	public int hopeAuctionMoney;
	// 차량 간단 정보(차량번호,년식,기어,연료,주행거리,자가또는법인) 분리해야 할듯
	public String auctionCarInfo;
	//차량이미지 
	public String imgSrc;
	//차량수리내역(성능점검) 이미지
	public String performanceCheckList;
	//차량 평가 급수
	public String evaluationGrade;
	//응찰가격이 희망가격을 넘어섯다. 낙찰이 되는 물건이 되었다
	public String successfulBidYN;
	//전송 TEXT 메세지(지금은 테스트 메세지이나 주로 위 상태와 함께 낙찰되었습니다, 유찰되었습니다 일듯)
	public String message;
	//websocket 상태? 딱히 단어가.. 일단 지금은 로그인 안한 사람 팅기는 용도. 정상:0000 로그인 하지 않음 : 9999
	public String conState;
	
	public BidMessage() {
		
	};
	
	public BidMessage(String entryNumber, String auctionState, int minAuctionMoney, int hopeAuctionMoney, String auctionCarInfo, String imgSrc,
			String performanceCheckList, String evaluationGrade, String message) {
		super();
		this.entryNumber = entryNumber;
		this.auctionState = auctionState;
		this.competePeopleNum = 0;
		this.hasRight = false;
		this.minAuctionMoney = minAuctionMoney;
		this.nowAuctionMoney = 0;
		this.hopeAuctionMoney = hopeAuctionMoney;
		this.auctionCarInfo = auctionCarInfo;
		this.imgSrc = imgSrc;
		this.performanceCheckList = performanceCheckList;
		this.evaluationGrade = evaluationGrade;
		this.successfulBidYN = "N";
		this.message = message;
		this.conState = "0000";
	}
	public String getBidId() {
		return bidId;
	}
	public void setBidId(String bidId) {
		this.bidId = bidId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEntryNumber() {
		return entryNumber;
	}
	public void setEntryNumber(String entryNumber) {
		this.entryNumber = entryNumber;
	}
	public String getAuctionState() {
		return auctionState;
	}
	public void setAuctionState(String auctionState) {
		this.auctionState = auctionState;
	}
	public int getCompetePeopleNum() {
		return competePeopleNum;
	}
	public void setCompetePeopleNum(int competePeopleNum) {
		this.competePeopleNum = competePeopleNum;
	}
	public boolean isHasRight() {
		return hasRight;
	}
	public void setHasRight(boolean hasRight) {
		this.hasRight = hasRight;
	}
	public int getMinAuctionMoney() {
		return minAuctionMoney;
	}
	public void setMinAuctionMoney(int minAuctionMoney) {
		this.minAuctionMoney = minAuctionMoney;
	}
	public int getNowAuctionMoney() {
		return nowAuctionMoney;
	}
	public void setNowAuctionMoney(int nowAuctionMoney) {
		this.nowAuctionMoney = nowAuctionMoney;
	}
	public int getHopeAuctionMoney() {
		return hopeAuctionMoney;
	}
	public void setHopeAuctionMoney(int hopeAuctionMoney) {
		this.hopeAuctionMoney = hopeAuctionMoney;
	}
	public String getAuctionCarInfo() {
		return auctionCarInfo;
	}
	public void setAuctionCarInfo(String auctionCarInfo) {
		this.auctionCarInfo = auctionCarInfo;
	}
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	public String getPerformanceCheckList() {
		return performanceCheckList;
	}
	public void setPerformanceCheckList(String performanceCheckList) {
		this.performanceCheckList = performanceCheckList;
	}
	public String getEvaluationGrade() {
		return evaluationGrade;
	}
	public void setEvaluationGrade(String evaluationGrade) {
		this.evaluationGrade = evaluationGrade;
	}
	public String getSuccessfulBidYN() {
		return successfulBidYN;
	}
	public void setSuccessfulBidYN(String successfulBidYN) {
		this.successfulBidYN = successfulBidYN;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getConState() {
		return conState;
	}
	public void setConState(String conState) {
		this.conState = conState;
	}

	@Override
	public String toString() {
		return "BidMessage [bidId=" + bidId + ", userName=" + userName + ", entryNumber=" + entryNumber
				+ ", auctionState=" + auctionState + ", competePeopleNum=" + competePeopleNum + ", hasRight=" + hasRight
				+ ", minAuctionMoney=" + minAuctionMoney + ", nowAuctionMoney=" + nowAuctionMoney
				+ ", hopeAuctionMoney=" + hopeAuctionMoney + ", auctionCarInfo=" + auctionCarInfo + ", imgSrc=" + imgSrc
				+ ", performanceCheckList=" + performanceCheckList + ", evaluationGrade=" + evaluationGrade
				+ ", successfulBidYN=" + successfulBidYN + ", message=" + message + ", conState=" + conState + "]";
	}


}
