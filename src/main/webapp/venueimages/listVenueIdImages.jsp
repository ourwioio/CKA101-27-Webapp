<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.venueimages.model.*"%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>場地編號圖片清單 - listVenueIdImages.jsp</title>

<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
	width: 800px;
}

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

	<table id="table-1">
		<tr>
			<td>
				<h3>場地編號圖片清單 - listVenueIdImages.jsp</h3> 
				<a href="select_page.jsp">回首頁</a>
			</td>
		</tr>
	</table>

	<table>
		<tr>
			<th>圖片編號</th>
			<th>場地編號</th>
			<th>圖片</th>
		</tr>

		<c:forEach var="viVO" items="${list}">
			<tr>
				<td>${viVO.imagesId}</td>
				<td>${viVO.venueId}</td>
				<td>
					<%
					VenueImagesVO viVO = (VenueImagesVO) pageContext.getAttribute("viVO");
					String base64 = Base64.getEncoder().encodeToString(viVO.getImages());
					%> 
					<img src="data:image/jpeg;base64,<%=base64%>" height="100"/>
				</td>
			</tr>
		</c:forEach>

	</table>

</body>
</html>