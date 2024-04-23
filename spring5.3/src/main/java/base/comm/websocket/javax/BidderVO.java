package base.comm.websocket.javax;

import java.time.LocalTime;

/**
 * 경매 websocket Session 용 VO
 * @author USER
 *
 */
public class BidderVO {

	private String userId;
	private String userName;
	private String grade;
	private int nowBidMoney;
	private LocalTime lastBidTime;
	
	public BidderVO(String userId, String userName, String grade) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.grade = grade;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
		return "BidderVO [userId=" + userId + ", userName=" + userName + ", grade=" + grade + ", nowBidMoney=" + nowBidMoney
				+ ", lastBidTime=" + lastBidTime + "]";
	}
	
	
}
