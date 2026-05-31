<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.webond.venueimages.model.*"%>
<%@ page import="java.util.*"%>

<%
VenueImagesVO viVO = (VenueImagesVO) request.getAttribute("viVO");
%>

<html>
<head>
<title>場地資料 - listOneVenueImages.jsp</title>
<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

h4 {
	color: blue;
	display: inline;
}

table {
	width: 600px;
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
				<h3>場地資料 - listOneVenueImages.jsp</h3>
				<h4>
					<a href="select_page.jsp">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
		<tr>
			<th>圖片編號</th>
			<th>場地編號</th>
			<th>圖片</th>
		</tr>
		<tr>
			<td><%=viVO.getImagesId()%></td>
			<td><%=viVO.getVenueId()%></td>
			<td>
				<%
				String base64 = Base64.getEncoder().encodeToString(viVO.getImages());
				%> 
				<img src="data:image/jpeg;base64,<%=base64%>" height="100" />
			</td>
		</tr>
	</table>

</body>
</html>