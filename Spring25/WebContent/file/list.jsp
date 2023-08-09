<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("UTF-8"); String cp = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>파일리뷰 리스트</title>
</head>
<body>

<br>

<h2 align="center">파일게시판</h2>

<table style="margin: auto;">
<tr><td align="right">

<input type="button" value="파일업로드" onclick="location.href='<%=cp%>/fileTest/upload.action';">
<br><br>
<table cellpadding="5" cellspacing="0" style="margin: auto; border: 1px solid #000000; border-radius: 3px;">

<tr align="center">
	<td style="border-bottom: 1px solid #000000;">번호</td>
	<td style="border-bottom: 1px solid #000000; width: 150px;">제목</td>
	<td style="border-bottom: 1px solid #000000; width: 200px;">파일명</td>
	<td style="border-bottom: 1px solid #000000;">삭제</td>
</tr>
<c:forEach var="dto" items="${lists }">
<tr>
	<td align="center">${dto.listNum }</td>
	<td width="150">${dto.subject }</td>
	<td width="200"><a href="">${dto.originalFileName }</a></td>
	<td align="center"><a href="">삭제하기</a></td>
</tr>
</c:forEach> 

</table>

<table>
	<c:if test="${dataCount!=0 }">
	<tr><td> ${pageIndexList } </td></tr>
	</c:if>
	<c:if test="${dataCount==0 }">
	<tr><td> 등록된 데이터가 없습니다. </td></tr>
	</c:if>
</table>

</td></tr>
</table>


</body>
</html>