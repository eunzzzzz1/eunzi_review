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

핸드폰번호<br>
<input type="text" name="userName"><br>
주소<br>
<input type="text" name="userIdentifycationNum1"><br><br>

약관에 동의하시겠습니까? <input type="checkbox" value="동의완료"><br><br>


<input type="button" value="이전으로" name="_target1">
<input type="button" value="다음으로" name="_target3">
<input type="button" value="가입취소" name="_cancel">

</form>

</body>
</html>