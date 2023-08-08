<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("UTF-8"); String cp = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> :: 게시판 :: </title>

<link rel="stylesheet" type="text/css" href="<%=cp %>/board/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=cp %>/board/css/created.css"/>

<script type="text/javascript" src="<%=cp%>/board/js/util.js"></script>
<script type="text/javascript">

	function sendIt() {
		path = document.myForm;
		
		
		str = path.subject.value;
		str = str.trim(); // util.js의 function. 양쪽 공백을 없애는 펑션
		if(!str) {
			alert("\n제목을 입력하세요.");
			path.subject.focus();
			return;
		}
		
		path.subject.value = str;
		
		
		
		str = path.name.value;
		str = str.trim();
		if(!str) {
			alert("\n이름을 입력하세요.");
			path.name.focus();
			return;
		}
		
		if(!isValidKorean(str)) {
			alert("\n이름을 한글로 입력해주세요.");
			path.name.focus();
			return;
		}
		
		path.name.value = str;
		
		if(path.email.value) {
			if(!isValidEmail(path.email.value)) {
				alert("\n이메일을 올바르게 입력해주세요. ");
				path.email.focus();
				return;
			}
		}
		
		
		
		
		str = path.content.value;
		str = str.trim(); // util.js의 function. 양쪽 공백을 없애는 펑션
		if(!str) {
			alert("\n내용을 입력하세요.");
			path.content.focus();
			return;
		}
		
		path.content.value = str;
	 	
		str = path.pwd.value;
		str = str.trim(); // util.js의 function. 양쪽 공백을 없애는 펑션
		if(!str) {
			alert("\n패스워드를 입력하세요.");
			path.pwd.focus();
			return;
		}
		
		path.pwd.value = str;
		
		if(path.mode.value=="insert") {
			path.action = "<%=cp%>/bbs/created.action";
		} else if (path.mode.value=="update") {
			path.action = "<%=cp%>/bbs/updated.action";
		}
		
		path.submit();
		
		
	}

</script>

</head>
<body>

<div id="bbs">
	<div id="bbs_title">
		<p align="center">⋆｡♡ﾟ☁︎ ⋆｡ ﾟ☁︎ ﾟ｡♡⋆｡ 게시판 ⋆｡♡ﾟ☁︎ ⋆｡ ﾟ☁︎ ﾟ｡♡⋆｡</p>
	</div>
	
	<form action="" method="post" name="myForm">
	<div id="bbsCreated">
		<div class="bbsCreated_bottomLine">
			<dl>
				<dt> 제&nbsp;&nbsp;&nbsp;&nbsp;목 </dt>
				<dd>
				<input type="text" name="subject" size="60" maxlength="100" class="boxTF"
					value="${dto.subject }"/>
				</dd>
			</dl>
		</div>
	
	
		<div class="bbsCreated_bottomLine">
			<dl>
				<dt> 작성자 </dt>
				<dd>
				<input type="text" name="name" size="35" maxlength="20" class="boxTF" value="${dto.name}"/>
				</dd>
			</dl>
		</div>

		<div class="bbsCreated_bottomLine">
			<dl>
				<dt> 이메일 </dt>
				<dd>
				<input type="text" name="email" size="35" maxlength="50" class="boxTF" value="${dto.email}"/>
				</dd>
			</dl>
		</div>
		
		<div id="bbsCreated_content">
			<dl>
				<dt> 내&nbsp;&nbsp;&nbsp;&nbsp;용 </dt>
				<dd>
				<textarea rows="12" cols="63" name="content" class="boxTA" style="resize: none; background-color: #ffffff">${dto.content}</textarea>
				</dd>
			</dl>
		</div>

		<div class="bbsCreated_noLine">
			<dl>
				<dt> 패스워드 </dt>
				<dd>
				<input type="password" name="pwd" size="35" maxlength="7" class="boxTF"
					 value="${dto.pwd}"/>&nbsp;(게시물 수정 및 삭제 시 필요)
				</dd>
			</dl>
		</div>		
	</div>
	
	<div id="bbsCreated_footer">
	
	<!-- 수정을 위해 num,pageNum을 넘겨준다. -->
	<c:if test="${mode=='update' }">
		<input type="hidden" name="num" value="${dto.num}">
	</c:if>
	<input type="hidden" name="pageNum" value="${dto.pageNum}">
	
	<!-- 해당 페이지에 insert하기 위해 들어온건지, update하기 위해 들어온건지 구분해 코드를 실행하기 위해 mode를 넘겨준다. -->
	<input type="hidden" name="mode" value="${mode}">
	
	<input type="button" value=" 등록하기 " class="btn2" onclick="sendIt()">
	<input type="reset" value=" 다시입력 " class="btn2" onclick="document.myForm.subject.focus();">
	<input type="button" value=" 작성취소" class="btn2" onclick="javascript: location.href='<%=cp%>/bbs/list.action'">
	</div>
	
	</form>
</div>











</body>
</html>