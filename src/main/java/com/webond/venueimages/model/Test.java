package com.webond.venueimages.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Test {
	public static void main(String[] args) throws IOException {
		byte[] pic = getPictureByteArray("src/images/IMG_8118.jpg");
		byte[] pic2 = getPictureByteArray("src/images/IMG_8124.jpg");
		byte[] pic3= getPictureByteArray("src/images/IMG_8076.jpg");
//		VenueImagesVO vi = new VenueImagesVO(2, 2001, pic);

//		VenueImagesDAO dao = new VenueImagesDAO();
//		dao.insert(vi);
//		dao.update(vi);
//		dao.delete(1);

		// 單筆查詢
//		VenueImagesVO vi2 = dao.findByPrimaryKey(1);
//		System.out.println(vi2);

		// 多筆查詢
//		List<VenueImagesVO> list = dao.getAll();
//		for (VenueImagesVO vo : list) {
//			System.out.println(vo);
//		}

		
		
		// 使用Service 新增修改刪除資料
		VenueImagesService vis = new VenueImagesService();
		// 查全部資料
//		    List<VenueImagesVO> list = vis.getAll();
//		    for (VenueImagesVO v : list) {
//		    	System.out.println(v);
//		    }
		
		// 查詢一筆資料
//		VenueImagesVO vo = vis.getOneImages(1);
//		System.out.println(vo);
		
		// 新增一筆資料
		vis.addImages(2001, pic);
		vis.addImages(2002, pic2);
		vis.addImages(2003, pic3);
		
		// 更新應料
//		vis.updateImages(4, 2002, pic2);
		
		// 刪除資料
//		vis.deleteImages(5009);
	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = fis.readAllBytes();

		fis.close();
		return buffer;
	}

}
