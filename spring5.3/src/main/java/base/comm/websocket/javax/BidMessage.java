package base.comm.websocket.javax;

public class BidMessage {

	// 사용자 id
	public String bidId;
	//출품번호
	public String entryNumber;
	//경매상태(준비,시작,경쟁,낙찰)
	public String auctionState;
	// 경합 인원 수(신호등)
	public int competePeopleNum;
	// 낙찰 권리 여부
	public boolean hasRight;
	// 최저 판매 금액(입찰 시작 가격)
	public int minAuctionMoney;
	// 현재 진행 호가 금액 
	public int nowAuctionMoney;
	// 희망 낙찰 금액
	public int maxAuctionMoney;
	// 차량 간단 정보(차량번호,년식,기어,연료,주행거리,자가또는법인)
	public String auctionCarInfo;
	//차량이미지 => 응찰가격이 희망가격에 도달하게 되면 이미지 주위가 번쩍임. 낙찰, 유찰 결과 보여주기
	public String imgSrc;
	//차량수리내역(성능점검) 이미지
	public String performanceCheckList;
	//차량 평가 급수
	public String evaluationGrade;
	//낙찰여부 메세지
	public String successfulBidYN;
	//전송메세지
	public String message;
	
	public String getBidId() {
		return bidId;
	}
	public void setBidId(String bidId) {
		this.bidId = bidId;
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
	public int getMaxAuctionMoney() {
		return maxAuctionMoney;
	}
	public void setMaxAuctionMoney(int maxAuctionMoney) {
		this.maxAuctionMoney = maxAuctionMoney;
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
	@Override
	public String toString() {
		return "BidMessage [bidId=" + bidId + ", entryNumber=" + entryNumber + ", auctionState=" + auctionState
				+ ", competePeopleNum=" + competePeopleNum + ", hasRight=" + hasRight + ", minAuctionMoney="
				+ minAuctionMoney + ", nowAuctionMoney=" + nowAuctionMoney + ", maxAuctionMoney=" + maxAuctionMoney
				+ ", auctionCarInfo=" + auctionCarInfo + ", imgSrc=" + imgSrc + ", performanceCheckList="
				+ performanceCheckList + ", evaluationGrade=" + evaluationGrade + ", successfulBidYN=" + successfulBidYN
				+ ", message=" + message + "]";
	}

}
