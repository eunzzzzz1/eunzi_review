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

<h4>회원정보 확인</h4>

<form action="<%=cp %>/review/join.action" method="post">

아이디: <br>
비밀번호: <br>
가입유형: <br>
<br>
이름: <br>
주민등록번호: <br>
<br>
휴대폰번호: <br>
주소: <br>
동의여부: <br>
<br>
<br>
입력된 정보를 확인하신 후, '가입하기' 버튼을 눌러주세요.<br><br>


<input type="button" value="이전으로" name="_target2">
<input type="button" value="가입하기" name="_finish">
<input type="button" value="가입취소" name="_cancel">

</form>

</body>
</html>