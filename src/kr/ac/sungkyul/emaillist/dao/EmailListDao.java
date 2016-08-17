package kr.ac.sungkyul.emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.ac.sungkyul.emaillist.vo.EmailListVo;

public class EmailListDao {

	// connection
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "webdb";
			String password = "webdb";

			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * showlistJSP
	 */
	public List<EmailListVo> getList() {
		List<EmailListVo> list = new ArrayList<EmailListVo>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			stmt = conn.createStatement();

			String sql = "select no," + "first_name, " + "last_name, " + "email, "
					+ "to_char(reg_date, 'yyyy-mm-dd') as \"생성일\" " + "from EMAILLIST";

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Long no = rs.getLong(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String email = rs.getString(4);
				String regDate = rs.getString(5);

				EmailListVo vo = new EmailListVo();

				vo.setNo(no);
				vo.setFirstName(firstName);
				vo.setLastName(lastName);
				vo.setEmail(email);
				vo.setRegDate(regDate);

				list.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	/**
	 * insertEmail
	 */
	public boolean insert(EmailListVo vo) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {

			conn = getConnection();
			String sql = "insert into emaillist values(seq_emaillist.nextval,?,?,?,sysdate)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getFirstName());
			pstmt.setString(2, vo.getLastName());
			pstmt.setString(3, vo.getEmail());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}

		return (count == 1);
	}
}
