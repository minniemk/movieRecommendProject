<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description"
	content="영화 보고 갈래는 무인 팀이 제작한 사용자 경험 기반 영화 추천 사이트 입니다.">
<meta name="author" content="Team Muin">

<link href="${pageContext.request.contextPath}/resources/css/cover.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" 
integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" 
crossorigin="anonymous"></script>

<link
	href="https://fonts.googleapis.com/css?family=Black+Han+Sans&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Do+Hyeon&display=swap"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Roboto&display=swap"
	rel="stylesheet">
<!-- Bootstrap core CSS -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<title>회원 목록 페이지</title>

<style>
#caption {
	color: white !important;
	font-family: "Do Hyeon";
	font-size: 40px;
}

#memberTable {
	text-align: center;
	margin: 0px;
	font-size: 20px;
	background-color: transparent;
}
</style>
<script>
	$(document).on('click', '#gomain', function(e) {
		e.preventDefault();
		location.href = "${pageContext.request.contextPath}/";
	});
</script>
</head>
<body>
	<sec:authentication var="mvo" property="principal" />
	<br>
	<br>
	<h1 align="center" id="caption">총 회원수 : ${map.count}명</h1>

	<article>
		<div class="container">

			<div class="table-responsive">

				<table class="table table-borderless table-dark table-hover" id="memberTable">
					<colgroup>

						<col style="width: 5%;" />

						<%-- <col style="width:auto;" /> --%>

						<%-- <col style="width:15%;" /> --%>

						<col style="width: 10%;" />

						<col style="width: 10%;" />

					</colgroup>
					<tr>
						<td>회원이메일</td>
						<td>회원이름</td>
						<td>회원아이디</td>
					</tr>
					<c:forEach var="row" items="${map.list}">
						<tr>
							<td>${row.memberEmail}</td>
							<td>${row.memberName}</td>
							<td>${row.memberId}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<form name="form1" method="post"
				action="${pageContext.request.contextPath}/enterCafe">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}"> <select name="searchOption">
					<option value="all"
						<c:out value="${map.searchOption == 'all'?'selected' : ''}"/>>
						전체</option>
					<option value="member_email"
						<c:out value="${map.searchOption == 'member_email'?'selected' : ''}"/>>
						이메일</option>
					<option value="member_name"
						<c:out value="${map.searchOption == 'member_name'?'selected' : ''}"/>>
						이름</option>
					<option value="member_Id"
						<c:out value="${map.searchOption == 'member_Id'?'selected' : ''}"/>>
						아이디</option>
				</select> <input name="keyword" value="${map.keyword}"> <input
					type="submit" value="검색">
			</form>
			<div>
				<button type="button" class="btn btn-sm btn-secondary" id="gomain">메인으로</button>
			</div>
		</div>

	</article>
</body>
</html>