<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("UTF-8"); String cp = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>파일리뷰 업로드 작성창</title>
</head>
<body>

<h2 align="center">파일 업로드하기</h2>

<br>

<form action="<%=cp %>/fileTest/upload.action" method="post" enctype="multipart/form-data">

	<table cellpadding="10" cellspacing="0" style="margin: auto; border: 1px solid #000000; border-radius: 3px;">
	<tr>
		<td>제목</td>
		<td><input type="text" name="subject"></td>
	</tr>
	<tr>
		<td>파일첨부</td>
		<td><input type="file" name="fileUpload"></td>
	</tr>
	</table>
	<br><br>
	<table style="margin: auto;">
	<tr>
		<td colspan="2" align="center"><input type="submit" value="파일올리기">
		<input type="button" value="리스트" onclick="location.href='<%=cp %>/fileTest/list.action';"></td>
	</tr>
	</table>

<input type="hidden" name="mode" value="upload">
</form>


</body>
</html>