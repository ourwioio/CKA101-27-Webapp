<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>VenueImages Home</title>

<style>
body {
    margin: 0;
    padding: 0;
    display: flex;
    flex-direction: column;
    align-items: center;
    
    padding-top: 25px; 
    
    min-height: 100vh;
    background-color: #f9f9f9;
}

.container {
    text-align: left;       
    width: 100%;
    max-width: 600px;   
    padding: 20px;
}
h3#top {
	width: 450px;
	background-color: #84C1FF;
	margin-top: 5px;
	margin-bottom: 10px;
	border: 3px ridge Gray;
	height: 80px;
	text-align: center;
}

table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
	border: 3px ridge Gray;
	height: 80px;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>

</head>
<body bgcolor='white'>
	<h3 id="top">場地圖片查詢 - select_page.jsp</h3>
	<h3>場地圖片查詢:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${requestScope.errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<ul>
		<li>
			<a href='listAllVenueImages.jsp'>List</a> all VenueImages. <br>
			<br>
		</li>


		<li>
			<FORM METHOD="post" ACTION="venueimages.do">
				<b>輸入圖片編號 (如5001):</b> 
				<input type="text" name="imagesId"> 
				<input type="hidden" name="action" value="getOne_For_Display"> 
				<input type="submit" value="送出">
			</FORM>
		</li>

		<jsp:useBean id="vis" scope="page" class="com.webond.venueimages.model.VenueImagesService" />

		<li>
			<FORM METHOD="post" ACTION="venueimages.do">
				<b>選擇圖片編號:</b> 
				<select size="1" name="imagesId">
					<c:forEach var="viVO" items="${vis.all}">
						<option value="${viVO.imagesId}">${viVO.imagesId}
					</c:forEach>
				</select> 
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>
		<br>
		<li>
			<FORM METHOD="post" ACTION="venueimages.do">
				<b>選擇場地編號 (如2001):</b> 
				<input type="text" name="venueId"> 
				<input type="hidden" name="action" value="getVenueId_For_Display"> 
				<input type="submit" value="送出">
			</FORM>
		</li>

		  <li>
		     <FORM METHOD="post" ACTION="venueimages.do" >
		       <b>選擇場地編號:</b>
		       <select size="1" name="venueId">
				<c:forEach var="venueId" items="${vis.allVenueIds}">
					<option value="${venueId}">${venueId}
				</c:forEach> 
		       </select>
		       <input type="hidden" name="action" value="getVenueId_For_Display">
		       <input type="submit" value="送出">
		     </FORM>
		  </li>
	</ul>


	<h3>場地管理</h3>

	<ul>
		<li><a href='addVenueImages.jsp'>Add</a> a new VenueImages.</li>
	</ul>

</body>
</html>