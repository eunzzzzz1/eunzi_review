<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("UTF-8"); String cp = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="<%=cp %>/board/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=cp %>/board/css/article.css"/>

</head>
<body>

<div id="bbs">
	<div id="bbs_title">
		<p align="center">⋆｡♡ﾟ☁︎ ⋆｡ ﾟ☁︎ ﾟ｡♡⋆｡ 게시판 ⋆｡♡ﾟ☁︎ ⋆｡ ﾟ☁︎ ﾟ｡♡⋆｡</p>
	</div>

	<div id="bbsArticle">
		<div id="bbsArticle_header">
			${dto.subject }
		</div>
		<div class="bbsArticle_bottomLine">
			<dl>
				<dt> 작성자 </dt>
					<dd>
					${dto.name }
					<c:if test="${!empty dto.email }">
					&nbsp;(<a href="mailto:${dto.email }">${dto.email }</a>)
					</c:if>
					</dd>
				<dt> 줄 수 </dt><dd> ${lineSu } </dd>
			</dl>
		</div>
		
		<div class="bbsArticle_bottomLine">
			<dl>
				<dt> 등록일 </dt> <dd> ${dto.created } </dd>
				<dt> 조회수 </dt><dd> ${dto.hitCount } </dd>
			</dl>
		</div>
		
		<div id="bbsArticle_content">
			<table width="600" border="0">
				<tr><td style="padding: 20px 80px 20px 62px" valign="top" height="200">${dto.content }</td></tr>
			</table>
		</div>
	</div>
	<div class="bbsArticle_noLine" style="text-align: right">
		From : ${dto.ipAddr }
	</div>
	<div class="bbsArticle_bottomLine">
	이전글&nbsp;&nbsp;&nbsp;
	<c:if test="${empty preSubject }">
		이전 글이 없습니다.
	</c:if>
	<c:if test="${!empty preSubject }">
		<a href="<%=cp %>/bbs/article.action?${params}&num=${preNum }">${preSubject }</a>
	</c:if>
	</div>
	<div class="bbsArticle_noLine">
	다음글&nbsp;&nbsp;&nbsp;
	<c:if test="${empty nextSubject }">
		다음 글이 없습니다.
	</c:if>
	<c:if test="${!empty nextSubject }">
		<a href="<%=cp %>/bbs/article.action?${params}&num=${nextNum }">${nextSubject }</a>
	</c:if>
	</div>
	
	<div id="bbsArticle_footer">
		
		<div id=leftFooter>
			<input type="hidden" name="mode" value="update">
		<input type="button" value=" 수정 " class="btn2" onclick="<%=cp %>/bbs/created.action"/>
		<input type="button" value=" 삭제 " class="btn2" onclick="<%=cp %>/bbs/delete.action?num=${dto.num}"/>
		</div>
		
		<div id="rightFooter">
		<input type="button" value=" 리스트 " class="btn2" onclick="location.href='<%=cp %>/bbs/list.action'"/>
		</div>
		
	</div>
</div>


</body>
</html>