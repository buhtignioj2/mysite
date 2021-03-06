<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    // pagecontext랑 <c:set>이랑 같음
    pageContext.setAttribute( "newLine", "\n" );
%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${fn:replace(vo.content, newLine, "<br>")}
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<a href="${pageContext.request.contextPath }/board?a=replyform&no=${vo.no }&keyword=${keyword }">답글</a>
					<a href="${pageContext.request.contextPath }/board?p=${page}&keyword=${keyword}">글목록</a>
					<c:choose>
						<c:when test="${not empty authUser && authUser.no == vo.userNo }">
						<a href="${pageContext.request.contextPath }/board?a=modifyform&no=${vo.no }">글수정</a>
						</c:when>
					</c:choose>
				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>