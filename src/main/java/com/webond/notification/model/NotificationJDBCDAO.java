package com.webond.notification.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NotificationJDBCDAO implements NotificationDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/webond_project?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO notification(member_id, title, content, notification_type, is_read, report_id, employee_id, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM notification order by notification_id";
	private static final String GET_ONE_STMT = "SELECT * FROM notification WHERE notification_id = ?";
	private static final String DELETE = "DELETE FROM notification WHERE notification_id = ?";
	private static final String UPDATE = "UPDATE notification set member_id = ?, title = ?, content = ?, notification_type = ?, is_read = ?, report_id = ?, employee_id = ?, created_at = ? WHERE notification_id = ?";

	@Override
	public void insert(NotificationVO notificationVO) {
		// TODO Auto-generated method stub
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		}

		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				PreparedStatement pstmt = con.prepareStatement(INSERT_STMT, PreparedStatement.RETURN_GENERATED_KEYS)) {

			pstmt.setInt(1, notificationVO.getMemberId());
			pstmt.setString(2, notificationVO.getTitle());
			pstmt.setString(3, notificationVO.getContent());
			pstmt.setByte(4, notificationVO.getNotificationType());
			pstmt.setByte(5, notificationVO.getIsRead());
			pstmt.setObject(6, notificationVO.getReportId());
			pstmt.setInt(7, notificationVO.getEmployeeId());
			pstmt.setTimestamp(8, new Timestamp(notificationVO.getCreatedAt().getTime()));

			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				notificationVO.setNotificationId(rs.getInt(1));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}

	}

	@Override
	public void update(NotificationVO notificationVO) {
		// TODO Auto-generated method stub
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		}

		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				PreparedStatement pstmt = con.prepareStatement(UPDATE, PreparedStatement.RETURN_GENERATED_KEYS)) {

			pstmt.setInt(1, notificationVO.getMemberId());
			pstmt.setString(2, notificationVO.getTitle());
			pstmt.setString(3, notificationVO.getContent());
			pstmt.setByte(4, notificationVO.getNotificationType());
			pstmt.setByte(5, notificationVO.getIsRead());
			pstmt.setObject(6, notificationVO.getReportId());
			pstmt.setInt(7, notificationVO.getEmployeeId());
			pstmt.setTimestamp(8, new Timestamp(notificationVO.getCreatedAt().getTime()));
			pstmt.setInt(9, notificationVO.getNotificationId());

			// 另外 update 的 prepareStatement 拿掉 RETURN_GENERATED_KEYS
			con.prepareStatement(UPDATE);  // 不需要 RETURN_GENERATED_KEYS

			pstmt.executeUpdate();

			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}

	}

	@Override
	public void delete(Integer notificationId) {
		// TODO Auto-generated method stub
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		}

		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				PreparedStatement pstmt = con.prepareStatement(DELETE)) {

			pstmt.setInt(1, notificationId);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}

	}

	@Override
	public NotificationVO findByPrimaryKey(Integer notificationId) {
		// TODO Auto-generated method stub
		NotificationVO notificationVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, notificationId);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				notificationVO = new NotificationVO();
				notificationVO.setMemberId(rs.getInt("member_id"));
				notificationVO.setTitle(rs.getString("title"));
				notificationVO.setContent(rs.getString("content"));
				notificationVO.setNotificationType(rs.getByte("notification_type"));
				notificationVO.setIsRead(rs.getByte("is_read"));
				notificationVO.setReportId(rs.getInt("report_id"));
				notificationVO.setEmployeeId(rs.getInt("employee_id"));
				notificationVO.setCreatedAt(rs.getTimestamp("created_at"));

			}

		} catch (SQLException se) {
			se.printStackTrace();
			// Clean up JDBC resources
		} finally {
			closeResources(con, pstmt, rs);
		}

		return notificationVO;
	}

	@Override
	public List<NotificationVO> getAll() {
		// TODO Auto-generated method stub
		List<NotificationVO> notificationList = new ArrayList<>();
		NotificationVO notificationVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				notificationVO = new NotificationVO();
				notificationVO.setMemberId(rs.getInt("member_id"));
				notificationVO.setTitle(rs.getString("title"));
				notificationVO.setContent(rs.getString("content"));
				notificationVO.setNotificationType(rs.getByte("notification_type"));
				notificationVO.setIsRead(rs.getByte("is_read"));
				notificationVO.setReportId(rs.getInt("report_id"));
				notificationVO.setEmployeeId(rs.getInt("employee_id"));
				notificationVO.setCreatedAt(rs.getTimestamp("created_at"));
				notificationList.add(notificationVO);

			}

		} catch (SQLException se) {
			se.printStackTrace();
			// Clean up JDBC resources
		} finally {
			closeResources(con, pstmt, rs);
		}

		return notificationList;
	}

	private void closeResources(Connection con, PreparedStatement pstmt, ResultSet rs) {
		// TODO Auto-generated method stub
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}

	}
}
