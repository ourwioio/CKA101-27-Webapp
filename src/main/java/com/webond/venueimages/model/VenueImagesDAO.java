package com.webond.venueimages.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VenueImagesDAO implements VenueImagesDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/webond_project?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO venue_images (venue_id, venue_images, VENUE_COVER) VALUES (?, ?, ?)";
	private static final String Get_ALL_STMT = "SELECT * FROM venue_images";
	private static final String GET_ONE_STMT = "SELECT * FROM venue_images WHERE venue_images_id = ?";
	private static final String DELETE = "DELETE FROM venue_images WHERE venue_images_id = ?";
	private static final String UPDATE = "UPDATE venue_images set venue_id =?, venue_images =? WHERE venue_images_id=?";
	private static final String GET_ALL_VENUE_IDS = "SELECT venue_id FROM venue ORDER BY venue_id";
	private static final String GET_VENUE_IMAGES = "SELECT * FROM venue_images WHERE venue_id = ?";

	@Override
	public void insert(VenueImagesVO venueImagesVO) {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}

		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				PreparedStatement pstmt = con.prepareStatement(INSERT_STMT, PreparedStatement.RETURN_GENERATED_KEYS)) {

//			pstmt.setInt(1, venueImagesVO.getImagesId());
			pstmt.setInt(1, venueImagesVO.getVenueId());
			pstmt.setBytes(2, venueImagesVO.getImages());
			pstmt.setByte(3, venueImagesVO.getCover());
			pstmt.executeUpdate();

			// 取得流水號
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				venueImagesVO.setImagesId(rs.getInt(1));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}
	}

	@Override
	public void update(VenueImagesVO venueImagesVO) {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}

		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				PreparedStatement pstmt = con.prepareStatement(UPDATE)) {

			pstmt.setInt(1, venueImagesVO.getVenueId());
			pstmt.setBytes(2, venueImagesVO.getImages());
			pstmt.setInt(3, venueImagesVO.getImagesId());

			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}
	}

	@Override
	public void delete(Integer imagesId) {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}

		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				PreparedStatement pstmt = con.prepareStatement(DELETE)) {
			pstmt.setInt(1, imagesId);

			pstmt.executeUpdate();
		} catch (SQLException se) {
			System.out.println(se);
		}
	}

	@Override
	public VenueImagesVO findByPrimaryKey(Integer imagesId) {

		VenueImagesVO viVO = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}

		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				PreparedStatement pstmt = con.prepareStatement(GET_ONE_STMT)) {

			pstmt.setInt(1, imagesId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				viVO = new VenueImagesVO();
				viVO.setImagesId(rs.getInt("venue_images_id"));
				viVO.setVenueId(rs.getInt("venue_id"));
				viVO.setImages(rs.getBytes("venue_images"));
			}
		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
			System.out.println(se);
		}
		return viVO;
	}

	@Override
	public List<VenueImagesVO> getAll() {
		List<VenueImagesVO> list = new ArrayList<VenueImagesVO>();
		VenueImagesVO viVO = null;

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}

		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				PreparedStatement pstmt = con.prepareStatement(Get_ALL_STMT);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				viVO = new VenueImagesVO();
				viVO.setImagesId(rs.getInt(1));
				viVO.setVenueId(rs.getInt(2));
				viVO.setImages(rs.getBytes(3));
				list.add(viVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}

		return list;
	}

	@Override
	public List<Integer> getAllVenueIds() {
		List<Integer> list = new ArrayList<>();

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage());
		}

		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				PreparedStatement pstmt = con.prepareStatement(GET_ALL_VENUE_IDS);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				list.add(rs.getInt("venue_id"));
			}
		} catch (SQLException se) {
			throw new RuntimeException(se.getMessage());
		}
		return list;
	}

	@Override
	public List<VenueImagesVO> getAllVenueImages(Integer venueId) {
		List<VenueImagesVO> list = new ArrayList<VenueImagesVO>();
		VenueImagesVO viVO = null;
//		ResultSet rs = null;

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}

		try (Connection con = DriverManager.getConnection(url, userid, passwd);
				PreparedStatement pstmt = con.prepareStatement(GET_VENUE_IMAGES)) {

			pstmt.setInt(1, venueId);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					viVO = new VenueImagesVO();
					viVO.setImagesId(rs.getInt(1));
					viVO.setVenueId(rs.getInt(2));
					viVO.setImages(rs.getBytes(3));
					list.add(viVO);
				}
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}

		return list;
	}

}
