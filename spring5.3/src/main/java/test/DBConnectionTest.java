package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnectionTest {

	public static void connectionTest() {
		
		String sql = "SELECT /* BoardMapper.selectBoardList 게시판 목록 조회 */ " + 
				"				A.SEQ " + 
				"				, A.CATEGORY " + 
				"				, B.CODE_NAME " + 
				"				, A.TITLE " + 
				"				, A.BODY_TEXT " + 
				"				, A.ORIGIN_BOARD_SEQ " + 
				"				, A.ORDER_NUM " + 
				"				, A.USER_NO " + 
				"				, A.REG_DT " + 
				"				, DATE_FORMAT(A.CNG_DT, '%Y-%m-%d %H:%i:%s') CNG_DT " + 
				"		  FROM MAIN_BOARD A , CODE B " + 
				"		  WHERE A.CATEGORY = B.CODE " + 
				"		   AND B.CODE_TYPE = 'BOARD'" +
				"          AND A.SEQ = ?";
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:log4jdbc:mariadb://127.0.0.1:3306/mydb", "root", "pmgk!234");
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, 58);
			rs = pstm.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString("TITLE"));
			}
			conn.close();
			rs.close();
			pstm.close();
		} catch (Exception e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstm != null) pstm.close();
				if (conn != null && !conn.isClosed()) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) {
		connectionTest();

	}

}
