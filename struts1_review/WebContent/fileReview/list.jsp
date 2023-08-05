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

<input type="button" value="파일업로드" onclick="location.href='<%=cp%>/reFile.do?method=upload';">
<br><br>
<table cellpadding="5" cellspacing="0" style="margin: auto; border: 1px solid #000000; border-radius: 3px;">

<tr align="center">
	<td style="border-bottom: 1px solid #000000;">번호</td>
	<td style="border-bottom: 1px solid #000000; width: 150px;">제목</td>
	<td style="border-bottom: 1px solid #000000; width: 200px;">파일명</td>
	<td style="border-bottom: 1px solid #000000;">삭제</td>
</tr>
<tr>
	<td align="center">1</td>
	<td width="150">안녕하세요</td>
	<td width="200"><a href="">파일명.jpg</a></td>
	<td align="center"><a href="">삭제하기</a></td>
</tr>
<tr>
	<td align="center">1</td>
	<td width="150">안녕하세요</td>
	<td width="200"><a href="">파일명.jpg</a></td>
	<td align="center"><a href="">삭제하기</a></td>
</tr>

</table>

</td></tr>
</table>


</body>
</html>