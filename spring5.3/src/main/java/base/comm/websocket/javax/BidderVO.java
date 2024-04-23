package base.comm.websocket.javax;

import java.time.LocalTime;

public class BidderVO {

	private String userId;
	private String hpNo;
	private String grade;
	private int nowBidMoney;
	private LocalTime lastBidTime;
	
	public BidderVO(String userId, String hpNo, String grade) {
		super();
		this.userId = userId;
		this.hpNo = hpNo;
		this.grade = grade;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getHpNo() {
		return hpNo;
	}
	public void setHpNo(String hpNo) {
		this.hpNo = hpNo;
	}
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getNowBidMoney() {
		return nowBidMoney;
	}
	public void setNowBidMoney(int nowBidMoney) {
		this.nowBidMoney = nowBidMoney;
	}
	public LocalTime getLastBidTime() {
		return lastBidTime;
	}
	public void setLastBidTime(LocalTime lastBidTime) {
		this.lastBidTime = lastBidTime;
	}

	@Override
	public String toString() {
		return "BidderVO [userId=" + userId + ", hpNo=" + hpNo + ", grade=" + grade + ", nowBidMoney=" + nowBidMoney
				+ ", lastBidTime=" + lastBidTime + "]";
	}
	
	
}
