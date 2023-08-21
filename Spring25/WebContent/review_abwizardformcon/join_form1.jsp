<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("UTF-8"); String cp = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<h4>회원가입</h4>

<form action="<%=cp %>/review/join.action" method="post">

아이디<br>
<input type="text" name="userId"><br>
비밀번호<br>
<input type="password" name="userPwd"><br>
가입유형<br>
<select name="joinType">
	<c:forEach var="type" items="${types }">
		<option value="${type }">${type }</option>
	</c:forEach>
</select><br>

<font color="red"><b>${join.message }</b></font><br><br>

<input type="submit" value="다시입력" name="_target0">
<input type="submit" value="다음으로" name="_target1">
<input type="submit" value="가입취소" name="_cancel">

</form>


</body>
</html>