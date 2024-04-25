package base.comm.websocket.javax;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 1. 응찰 클릭시 경매 금액 더해주고, 판매 희망 금액 이상이 되면 낙찰이 되는 물건이 되었다(successfulBidYN=Y). 희망 금액 넘어가면 낙찰. => 완료
 * 2. 낙찰 또는 유찰일 경우 다음 물건 자동 조회 해서 전송 => 완료. 일단 클래스 내에서 해결. 실 적용시 DB로 변경해야 함
 * 3. 관리자가 들어와서 경매 시작 종료.=>완료
 * 4. 신호등(max 3명 정도만 관리. 1명,2명,3명 이상일 경우 상태값 전송) => 완료. 1초당 계산. 
 * 5. 5초간 응찰이 없을경우 처리(낙찰,유찰 메세지) 일반적으로 3초인데 그건 설정에 따르고 낙찰 되는 시간 정해서 낙찰되면 다음 또는 경매 전체 종료 => 완료
 * 6. REDIS 연동해서 멀티서버일 경우 처리
 * 7. 실제 입찰자 정보 세션으로 관리. 낙찰 됐을 경우 해당 낙찰자와 금액, 차량 정보 저장
 * 8. 권리 표시 => 완료
 * 9. 경매가 중간에 스톱이 됐다가 재개 됐을때 스레드 체크가 다시 시작이 되야 하는데? => 완료
 * 10. 관리자 중지 시작시 스레드 체크가 재시작 하지 않는다. => 완료
 * 11. 경매가 완전 종료 후 시작 버튼 누르면 시작 안되게.
 * 12. atomic이 정리 되지 않음. 응찰 금액, 로그인 세션 사용자 등에 대한 유일성 처리 방안 고려 필요
 * @author USER
 * 
 * @ServerEndpoint 이 어노테이션이 붙으면 웹소켓이 연결될때마다 객체가 인스턴스가 생성된다. Singleton으로 처리하기 위해 추가한 부분 : @Service. (@Component도 상관은 없다). ServerConfigurator 에도 추가 내용이 있다.
 */
@Service
@ServerEndpoint(value = "/auctionWebsocket.do", encoders = MsgEncoder.class, decoders = MsgDecoder.class, configurator = ServerConfigurator.class)
public class JavaxWebSocketEndpointServer {
	
	private final Logger LOGGER = LoggerFactory.getLogger(JavaxWebSocketEndpointServer.class);
	
	private List<Session> sessionList = new ArrayList<>();
	
	//현재 응찰 가격
	private int nowBidMoney = 0;
	//응찰시 호가 상승 기준 가격
	private int upMoneyByBid = 0;
	
	//싱글레톤으로 처리가 되지 않으면 매 접속마다 초기화 된다.
	private BidMessage bidMessage = null;
	
	//경매 순번
	private int bidCount = 0;
	//테스트용도. 경매 물건 목록
	private List<BidMessage> bidList = new ArrayList<BidMessage>();
	//경매 진행 상황 체크
	private boolean bidRunningCheck = false;
	//마지막 응찰 시간
	private LocalTime lastBidTime = null;
	//해당 물건 낙찰자 ID
	private String finalSuccessBidderId = null;
	
	
	//경매 물건 정보 초기화. 물건이 바뀔때마다 조회해서 전송해 줘야 함 
	public void initAuction() {
		//DB 조회해서 셋팅
		bidList.add(new BidMessage("2023", "R", 1500, 1550, "차량번호=12가1234,년식=2020,기어=auto,연료=가솔린,주행거리=10000,자가또는법인=자가", "차량 외부 이미지", "성능점검 이미지", "A++",""));
		bidList.add(new BidMessage("2024", "R", 5000, 5100, "차량번호=12가0001,년식=2024,기어=auto,연료=가솔린,주행거리=1000,자가또는법인=자가", "차량 외부 이미지","성능점검 이미지", "A++",""));
		bidList.add(new BidMessage("2025", "R", 500, 550, "차량번호=12가5678,년식=2000,기어=auto,연료=디젤,주행거리=100000,자가또는법인=자가", "차량 외부 이미지","성능점검 이미지", "A++",""));
		bidList.add(new BidMessage("2026", "R", 1000, 1050, "차량번호=12가9012,년식=2010,기어=auto,연료=가솔린,주행거리=10000,자가또는법인=자가", "차량 외부 이미지","성능점검 이미지", "A++",""));

		//테스트용도. 경매 낙찰 또는 유찰 후 자동 넘어가기 위해.
		bidMessage = bidList.get(bidCount);
		bidCount++;

		nowBidMoney = bidMessage.getMinAuctionMoney();
		if ( 1000 < bidMessage.getMinAuctionMoney() ) {
			upMoneyByBid = 5;
		} else {
			upMoneyByBid = 3;
		}

		serveAnyMessage();
	}

	//PathParam은 @ServerEndpoint에서 url값에 {} 같이 변수가 설정되어 있을때 받는 파라미터
	@OnOpen
	public void onOpen( @PathParam("param") String param,Session session, EndpointConfig config) throws Exception {
		LOGGER.debug("@@@@@@@@ onOpen");
		//ServerEndpointConfig에 값을 넣으면 session에 있는 내용이나 config에 있는 내용이나 같은 값. 다만 session은 ID를 설정해 주므로 이것으로 컨트롤
		LOGGER.debug("@@@@@@@@ onOpen session=" + session.getId() + "|" + session.getUserProperties());
//		LOGGER.debug("@@@@@@@@ onOpen config=" + config.getUserProperties());

		BidderVO vo = (BidderVO)session.getUserProperties().get("WEBSOCKET_USER_INFO");
		LOGGER.debug("@@@@@@@@ onOpen session vo=" + vo);
		
		if(  !"".equals(vo.getUserId()) ) {
			sessionList.add(session);
			if(bidMessage == null) {
				initAuction();
			} else {
				serveOneMessage(session);
			}
		} else {
			try {
				BidMessage localBidMessage = new BidMessage();
				localBidMessage.setConState("9999");
				localBidMessage.setMessage("로그인 정보가 없습니다.");
				session.getBasicRemote().sendObject(localBidMessage);
				throw new Exception("login info is null");
			} catch (Exception e) {
				e.printStackTrace();
				//종료가 되면 안되므로 Exception을 던짐. 그 후 onError, onClose가 차례대로 호출됨
				throw e;
			}
		}
	}
	
	@OnMessage
	public void onMessage(Session session, BidMessage onMessage) {
		LOGGER.debug("@@@@@@@@ onMessage session=" + session.getId() + "|" + session.getUserProperties());
		LOGGER.debug("@@@@@@@@ onMessage message=" + onMessage);

		BidderVO vo = (BidderVO)session.getUserProperties().get("WEBSOCKET_USER_INFO");
		if( "START_MASTER".equals(onMessage.getMessage())) {
			//Master만 시작이나 종료를 할 수 있다.
			if( "M".equals( vo.getGrade()) ) {
				bidMessage.setAuctionState("S");
				bidMessage.setMessage("경매를 시작합니다.");
				lastBidTime = LocalTime.now();
			}
		} else if( "STOP_MASTER".equals(onMessage.getMessage())) {
			//Master만 시작이나 종료를 할 수 있다.
			if( "M".equals( vo.getGrade()) ) {
				bidMessage.setAuctionState("E");
				bidMessage.setMessage("경매를 중단합니다.");
			}
		} else {
			//엔트리 넘버 같은거일 경우 추가함
			if( "S".equals(bidMessage.getAuctionState()) && bidMessage.getEntryNumber().equals(onMessage.getEntryNumber()) ) {
				nowBidMoney += upMoneyByBid;
				bidMessage.setNowAuctionMoney(nowBidMoney);
				vo.setNowBidMoney(nowBidMoney);
				vo.setLastBidTime(LocalTime.now());
				
				lastBidTime = LocalTime.now();
			}
			//낙찰이 되는 물건이 되었다
        	if( bidMessage.getNowAuctionMoney() >= bidMessage.getHopeAuctionMoney() ) {
        		bidMessage.setSuccessfulBidYN("Y");
        	}

		}

		//낙찰 및 유찰 다음 경매 처리. 3초 이상 최대 4초 미만 접속이 없을 경우 해당 물건 종료 및 다음 경매물 이동. 없으면 경매 전체 종료
		//경매 체크 스레드 시작 조건 : 체크 스레드가 돌지 않고 있고, 경매가 시작 됐을때
		if(!bidRunningCheck && "S".equals(bidMessage.getAuctionState()) && lastBidTime != null) {
			checkAuctionThread();
		}
		
		serveAnyMessage();
	}

	@OnClose
	public void onClose(Session session) {
		LOGGER.debug("@@@@@@@@ onClose=" + session.getId() + "|" + session.getUserProperties());
		sessionList.remove(session);
	}

	@OnError
	public void onError(Session session, Throwable t) {
		LOGGER.debug("@@@@@@@@ onError=" + session.getId() + "|" + session.getUserProperties());
		t.printStackTrace();
		LOGGER.error(t.getMessage());
	}

	//경쟁 상태 체크 함수. 4초이내 콜인 경우 카운트
	//현재 들어와 있는 키값이 아이디인 시간 어레이를 체크해서 제거한 후 남은 갯수로 경쟁 상태여부를 셋팅
	public int getcompetePeopleNum() {
		LOGGER.debug("@@@@@@@@ getcompetePeopleNum sessionList.size =" + sessionList.size());

		int retCount = 0;
		LocalTime nowTime = LocalTime.now();
		for (Session session : sessionList) {
   			BidderVO vo = (BidderVO)session.getUserProperties().get("WEBSOCKET_USER_INFO");
   			if(vo.getLastBidTime() == null) continue;//아직 경매 호가를 안한 입찰자는 정보가 없음
   			long minusTime = Duration.between( vo.getLastBidTime(), nowTime ).getSeconds();
   			if( minusTime < 4 ) {
   				retCount++;
   			}
		}
		return retCount;
	}
	
	/**
	 * 전체 인원 발송. synchronized 에 대해 생각해 볼 필요가 있다. 많은 사람들이 동시에 접속하면서 경매가 진행중일 경우 자원 경합이나 로스가 일어날 수 있다. 일단 샘플이니 넘어간다. 
	 */
	private synchronized void serveAnyMessage() {
		bidMessage.setCompetePeopleNum(getcompetePeopleNum());
		LOGGER.debug("@@@@@@@@ serveAnyMessage BidMessage =" + bidMessage);
		for (Session session : sessionList) {
            try {
    			LOGGER.debug("@@@@@@@@ serveAnyMessage session.getId() =" + session.getId() +"|WEBSOCKET_USER_INFO =" + session.getUserProperties().get("WEBSOCKET_USER_INFO"));
    			
    			BidderVO vo = (BidderVO)session.getUserProperties().get("WEBSOCKET_USER_INFO");
    			bidMessage.setBidId(vo.getUserId());
    			bidMessage.setUserName(vo.getUserName());

    			//낙찰된건 아님. 현재 진행중에 낙찰 권리(최고금액)을 넣은 상태. 
				if ( bidMessage.getNowAuctionMoney() >= bidMessage.getHopeAuctionMoney() && bidMessage.getNowAuctionMoney() == vo.getNowBidMoney()) {
    				bidMessage.setHasRight(true);
				} else {
					bidMessage.setHasRight(false);
				}

    			//유찰
    			if ( "F".equals(bidMessage.getAuctionState()) ) {
    				bidMessage.setMessage("물건이 유찰되었습니다.");
    				vo.setNowBidMoney(0);
    			//낙찰
    			} else if ( "O".equals(bidMessage.getAuctionState()) ) {
        			//누구에게 낙찰 되었다는걸 알려주는게 맞는가?
        			bidMessage.setMessage("물건이 " + finalSuccessBidderId + "에게 낙찰되었습니다.");
        			vo.setNowBidMoney(0);
    			//준비,시작,종료
    			} else {
    				
    			}
    			Basic basic = session.getBasicRemote();
                basic.sendObject(bidMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
		bidMessage.setBidId("");//BidMessage는 전역변수이므로 사용 후 해당 값 초기화
		bidMessage.setUserName("");
        bidMessage.setMessage("");
	}	

	/**
	 * 특정 인원에게만 발송
	 * @param reqSession
	 */
	private synchronized void serveOneMessage(Session reqSession) {
		bidMessage.setCompetePeopleNum(getcompetePeopleNum());
		LOGGER.debug("@@@@@@@@ serveOneMessage message =" + bidMessage);
        try {
        	BidderVO vo = (BidderVO)reqSession.getUserProperties().get("WEBSOCKET_USER_INFO");
        	bidMessage.setBidId(vo.getUserId());
        	bidMessage.setUserName(vo.getUserName());
        	reqSession.getBasicRemote().sendObject(bidMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	bidMessage.setBidId("");//BidMessage는 전역변수이므로 사용 후 해당 값 초기화
    	bidMessage.setUserName("");
	}
	
	/**
	 * 1초 간격으로 현재 경매 상태 체크. 낙찰, 유찰, 경매 전체 종료 체크 및 메세지 전송 
	 */
	public void checkAuctionThread() {
		LOGGER.debug("@@@@@@@@ checkAuctionThread=" + bidMessage);
		Timer timer = new Timer();
		TimerTask timerTask  = new TimerTask() {
            @Override
            public void run() {
            	LOGGER.debug("@@@@@@@@ Timer Thread"+bidMessage.getUserName());
            	if( "E".equals(bidMessage.getAuctionState()) ) {
	    			timer.cancel();
	    			bidRunningCheck = false;
            	}
            	
        		LocalTime nowTime = LocalTime.now();
       			long bidPeriodTime = Duration.between( lastBidTime, nowTime ).getSeconds();	            	
            	if(bidPeriodTime > 5) {

            		//경매 물건이 낙찰. 희망금액보다 작으면 유찰. 이상이면 낙찰.
            		if( bidMessage.getNowAuctionMoney() >= bidMessage.getHopeAuctionMoney() ) {
            			//누구에게 낙찰되엇따. 현재 세션목록에서 찾아서 셋팅해주기.
                		for (Session session : sessionList) {
                   			BidderVO vo = (BidderVO)session.getUserProperties().get("WEBSOCKET_USER_INFO");
                   			if( bidMessage.getNowAuctionMoney() == vo.getNowBidMoney() ) {
                   				finalSuccessBidderId = vo.getUserId();
                   				break;
                   			}
                		}
                		bidMessage.setAuctionState("O");
            		} else {
            			bidMessage.setAuctionState("F");
            		}
            		
            		
            		serveAnyMessage();
            		finalSuccessBidderId = null;//낙찰자 초기화
            		
            		//위 메세지를 얼마나 보여주고 넘어갈 것인가
            		try {
						Thread.sleep(1000);
            		} catch (InterruptedException e) {
						e.printStackTrace();
					}

            		//다음 물건이 없다면 종료
            		if(bidList.size() == bidCount) {
            			timer.cancel();
            			bidRunningCheck = false;
            			//경매 종료 추가
            			bidMessage.setAuctionState("E");
        				bidMessage.setMessage("오늘의 경매 목록이 완료되었습니다. 경매를 종료합니다.");
            		} else {
	            		//다음 물건으로 넘어간다.
	            		bidMessage = bidList.get(bidCount);
	            		bidCount++;
	            		nowBidMoney = bidMessage.getMinAuctionMoney();
	            		bidMessage.setAuctionState("S");
	            		bidMessage.setMessage("경매를 시작합니다.");
	            		lastBidTime = LocalTime.now();
            		}
            		bidMessage.setSuccessfulBidYN("N");
            		bidMessage.setHasRight(false);
            		serveAnyMessage();
            	}
            	
            	if(bidPeriodTime > 1 && bidPeriodTime < 5) {
	        		bidMessage.setCompetePeopleNum(getcompetePeopleNum());
            		serveAnyMessage();
            	}
            }
		};
		bidRunningCheck = true;
		timer.scheduleAtFixedRate(timerTask, 0, 1000);
	}
	
}