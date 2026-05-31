package com.webond.venueimages.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import com.webond.venueimages.model.VenueImagesService;
import com.webond.venueimages.model.VenueImagesVO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/venueimages/venueimages.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 10 * 1024 * 1024, maxRequestSize = 10 * 5 * 1024 * 1024)
public class VenueImagesServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("imagesId");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入照片編號");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/venueimages/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			Integer imagesId = null;
			try {
				imagesId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("照片編號格式不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/venueimages/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}
			/*************************** 2.開始查詢資料 *****************************************/
			VenueImagesService vis = new VenueImagesService();
			VenueImagesVO viVO = vis.getOneImages(imagesId);
			if (viVO == null) {
				errorMsgs.add("查無資料");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/venueimages/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}
			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("viVO", viVO); // 資料庫取出的VO物件,存入req
			String url = "/venueimages/listOneVenueImages.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneVenueImages.jsp
			successView.forward(req, res);
		}

		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer imagesId = Integer.valueOf(req.getParameter("imagesId"));

			/*************************** 2.開始查詢資料 *****************************************/
			VenueImagesService vis = new VenueImagesService();
			VenueImagesVO viVO = vis.getOneImages(imagesId);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("viVO", viVO); // 資料庫取出的VO物件,存入req
			String url = "/venueimages/update_venueimages_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 update_venueimages_input.jsp
			successView.forward(req, res);
		}

		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			Integer imagesId = Integer.valueOf(req.getParameter("imagesId"));
			Integer venueId = Integer.valueOf(req.getParameter("venueId"));

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
//			Integer venueId = null;
//			try {
//				venueId = Integer.valueOf(req.getParameter("venueId").trim());
//			} catch (NumberFormatException e) {
//				venueId = 0;
//				errorMsgs.add("場地編號請填數字.");
//			}

			Part part = req.getPart("upfile1");

			if (part == null || part.getSize() == 0) {
				errorMsgs.add("請選擇要上傳的照片。");
			}
			InputStream in = part.getInputStream();
			byte[] imgBytes = in.readAllBytes();

			in.close();

			VenueImagesVO viVO = new VenueImagesVO();
			viVO.setImagesId(imagesId);
			viVO.setVenueId(venueId);
			viVO.setImages(imgBytes);

			if (!errorMsgs.isEmpty()) {
				req.setAttribute("viVO", viVO); // 含有輸入格式錯誤的VO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/venueimages/update_venueimages_input.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/
			VenueImagesService vis = new VenueImagesService();
			viVO = vis.updateImages(imagesId, venueId, imgBytes);
			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("viVO", viVO); // 資料庫update成功後,正確的的emp物件,存入req
			String url = "/venueimages/listOneVenueImages.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneVenueImages.jsp
			successView.forward(req, res);

		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer venueId = null;

			try {
				venueId = Integer.valueOf(req.getParameter("venueId").trim());
			} catch (NumberFormatException e) {
				venueId = 2001;
				errorMsgs.add("請選擇場地編號。");
			}

			Part part = req.getPart("upfile1");

			if (part == null || part.getSize() == 0) {
				errorMsgs.add("請選擇要上傳的照片。");
			}
			InputStream in = part.getInputStream();
			byte[] imgBytes = in.readAllBytes();

			in.close();

			VenueImagesVO viVO = new VenueImagesVO();
			viVO.setVenueId(venueId);
			viVO.setImages(imgBytes);

			if (!errorMsgs.isEmpty()) {
				req.setAttribute("viVO", viVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/venueimages/addVenueImages.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/
			VenueImagesService vis = new VenueImagesService();
			viVO = vis.addImages(venueId, imgBytes);
			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("viVO", viVO);
			String url = "/venueimages/listOneVenueImages.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
			successView.forward(req, res);
		}
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
				/***************************1.接收請求參數***************************************/
				Integer imagesId = Integer.valueOf(req.getParameter("imagesId"));
				
				/***************************2.開始刪除資料***************************************/
				VenueImagesService vis = new VenueImagesService();
				vis.deleteImages(imagesId);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/venueimages/listAllVenueImages.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
		}
		
		if ("getVenueId_For_Display".equals(action)) { // 取得場地編號裡的所有圖片

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("venueId");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入場地編號");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/venueimages/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}
			
			Integer venueId = null;
			try {
				venueId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("場地編號格式不正確");
			}
			
			VenueImagesService vis = new VenueImagesService();
			 List<Integer> venId = vis.getAllVenueIds();
			    if (!venId.contains(venueId)) {
			        errorMsgs.add("請輸入目前有的場地編號：" 
			            + venId.get(0) + "~" 
			            + venId.get(venId.size()-1));
			        RequestDispatcher failureView = req.getRequestDispatcher("/venueimages/select_page.jsp");
			        failureView.forward(req, res);
			        return;
			    }
			/*************************** 2.開始查詢資料 *****************************************/
			List<VenueImagesVO> list = vis.getAllVenueImages(venueId);
			
			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("list", list);
			String url = "/venueimages/listVenueIdImages.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listVenueIdImages.jsp
			successView.forward(req, res);
		}
		
		if ("delete1".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
				/***************************1.接收請求參數***************************************/
				Integer imagesId = Integer.valueOf(req.getParameter("imagesId"));
				Integer venueId = Integer.valueOf(req.getParameter("venueId"));
				/***************************2.開始刪除資料***************************************/
				VenueImagesService vis = new VenueImagesService();
				vis.deleteImages(imagesId);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				List<VenueImagesVO> list = vis.getAllVenueImages(venueId); // 重新查詢
			    req.setAttribute("list", list); // 存入 req
				String url = "/venueimages/listVenueIdImages.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
		}

	}
}
