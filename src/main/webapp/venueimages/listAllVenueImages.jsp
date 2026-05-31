<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.webond.venueimages.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
VenueImagesService vis = new VenueImagesService();
List<VenueImagesVO> list = vis.getAll();
pageContext.setAttribute("list", list);
%>


<html>
<head>
<title>所有圖片資料 - listAllVenueimages.jsp</title>

<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
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

<style>
table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}
</style>

</head>
<body bgcolor='white'>

<!-- 	<h4>此頁練習採用 EL 的寫法取值:</h4> -->
	<table id="table-1">
		<tr>
			<td>
				<h3>所有場地資料 - listAllVenue.jsp</h3> <a href="select_page.jsp">回首頁</a>
			</td>
		</tr>
	</table>

	<table>
		<tr>
			<th>圖片編號</th>
			<th>場地編號</th>
			<th>圖片</th>
			<th>修改</th>
			<th>刪除</th>

		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="viVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">

			<tr>
				<td>${viVO.imagesId}</td>
				<td>${viVO.venueId}</td>
				<td>
					<%
					VenueImagesVO viVO = (VenueImagesVO) pageContext.getAttribute("viVO");
					String base64 = Base64.getEncoder().encodeToString(viVO.getImages());
					%> 
					<img src="data:image/jpeg;base64,<%=base64%>" height="100" />
				</td>


				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/venueimages/venueimages.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="修改"> <input type="hidden"
							name="imagesId" value="${viVO.imagesId}"> <input
							type="hidden" name="action" value="getOne_For_Update">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/venueimages/venueimages.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="刪除"> <input type="hidden"
							name="imagesId" value="${viVO.imagesId}"> <input
							type="hidden" name="action" value="delete">
					</FORM>
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="page2.file"%>

</body>
</html>