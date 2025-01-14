<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description"
	content="영화 보고 갈래는 무인 팀이 제작한 사용자 경험 기반 영화 추천 사이트 입니다.">
<meta name="author" content="Team Muin">
<link rel="icon" href="../../favicon.ico">

<title>영화 보고 갈래 : index page</title>

<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/resources/css/cover.css"
	rel="stylesheet">

<!-- link for main fonts -->
<link
	href="https://fonts.googleapis.com/css?family=Black+Han+Sans&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Do+Hyeon&display=swap"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Roboto&display=swap"
	rel="stylesheet">
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.3.1.min.js"></script>
<script>
	$(document).ready(function() {
		$("#yup").click(function() {
			var loginbtn = "<a href='/controller/loginForm'>로그인!</a>";
			var registerbtn = "<a href='/controller/registerForm'>처음이니?</a>";
			$("a").remove("#yup");
			$("a").remove("#nope");
			$("#yesorno").append(registerbtn + loginbtn);
		});

		$("#nope").click(function() {
			document.getElementById("movietonite").textContent = "알았어 잘가";
			document.getElementById("yup").textContent = "영화 볼거야";
			$("a").remove("#nope");
		});
		
		
		var id="<%=(String)session.getAttribute("id")%>";
		
		if(id!=null){
			$("#ajaxload").load("${pageContext.request.contextPath}/recommend/createCsv");
			$("#recommendmain").click(function() {
				$("#ajaxload").load("${pageContext.request.contextPath}/recommend/main");
			});
			$("#searchIt").click(function() {
				$("#ajaxload").load("${pageContext.request.contextPath}/movie/selectMovieView", $("#searchMovie").serialize());
			});
		}
	});
	
	
	
</script>

<script>
	function logout() {
		document.getElementById("logoutFrm").submit();
	}
</script>
<style>

#navbarsExampleDefault > ul > li:last-child:hover #logoutbtn{
	visibility:visible!important;
}
</style>
</head>
<body>


	<sec:authorize access="isAnonymous()">

		<header style="position:relative;">
		<div class=overlay>

			<div id="opening" style="position: absolute; top: 30%; left: 10%;">
				<div id="movietonite"
					style="height: auto; font-family: 'Black Han Sans', sans-serif; color: white; font-size: 120pt;">영화보고갈래?</div>
				<div id="yesorno" style="">
					<a href="#" id="yup">응</a> <a href="#" id="nope">아니</a>
				</div>
			</div>

		</div>

		<video class="video-background" height="100%" width="100%"
			preload="auto" autoplay="true" loop="loop" muted="muted" volume="0"
			style="z-index:0;"> <source
			src="${pageContext.request.contextPath}/resources/video/44_MTZfQmxva2Jhc3Rlcl9DREVJVk8.mp4"
			type="video/mp4"></video> </header>

	</sec:authorize>

	<sec:authorize access="isAuthenticated()">
		<sec:authentication var="mvo" property="principal" />
		<div class="container" style="position: relative; top: 150px;">

			<div id="ajaxload">
			</div>
		</div>


		<!-- 
		authentication의 getPrincipal().getName() ::
		Principal은 Provider 에서 Authentication에 넣어준 VO(생성자의 첫 매개변수)
	 -->
		<!-- 인증됬으면 -->
		<!-- 관리자인 경우 -->
		<nav class="navbar navbar-expand-md fixed-top"> <a
			class="navbar-brand" href="${pageContext.request.contextPath}"
			style="height: auto; font-family: 'Black Han Sans', sans-serif; color: white; font-size: 40px; padding: 1px 0px 0px 0px">영화보고갈래?</a>
		<div class="collapse navbar-collapse" id="navbarsExampleDefault">
			<ul class="navbar-nav mr-auto">
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li><a class="nav-link"
						href="${pageContext.request.contextPath }/admin/enterCafe">어드민</a></li>
				</sec:authorize>
				<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')">
					<li><a class="nav-link"
						href="${pageContext.request.contextPath}/member/mypage">마이페이지</a></li>
					<li><a class="nav-link" id="recommendmain" href="#">너의평점은</a></li>


					<form class="form-inline my-2 my-lg-0" name='searchMovie' id='searchMovie'
						method='post' onsubmit="return false;"
						action='${pageContext.request.contextPath}/movie/selectMovieView'>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}"> <select name='searchKind'
							style="vertical-align: middle; height: 28px; font-size: 20px; font-family: Do Hyeon; margin-left: 20px;">
							<option value="movieName">영화제목</option>
							<option value="movieGenre">영화장르</option>
							<option value="movieDirector">영화감독명</option>
						</select> <input class="form-control mr-sm-2" type="text"
							placeholder="Search" name='searchByMovieKeyWord'
							aria-label="Search"
							style="height: 28px; font-size: 20px; margin-top: 0px; padding-top: 5px; border-radius: 5px; font-family: Do Hyeon; border-width: 1px; border-style: solid; border-color: rgb(166, 166, 166); border-image: initial; width: 170px;">
						<button id="searchIt" class="btn btn-secondary my-2 my-sm-0"
							type="button" style="font-size: 20px !important; height: 28px; padding: 0px 10px 10px 10px !important;">함	찾아봐</button>

					</form>
				</sec:authorize>

			<li>


			<a class="btn btn-secondary my-2 my-sm-0" href="javascript:logout();"
				role="button" id="logoutbtn"
				style="font-size: 20px !important; height: 28px; padding: 0px 10px 10px 10px !important; background-color: transparent;margin:10px 16px 0px 10px!important; visibility:hidden;">로그아웃</a>
		</li>
		</ul>
		</div>
		</nav>

	</sec:authorize>



	<!--  
1. 로그아웃은 스프링 시큐러티 4부터는 로그아웃시 post 방식으로 이동하며 
  /logout url로 요청한다(따로 정의하지 않으면...)
2. _csrf 를 요청 파라미터로 보내야 한다.
-->
	<form id="logoutFrm"
		action="${pageContext.request.contextPath}/member/logout"
		method="post" style="display: none">
		<input type="hidden" name="${_csrf.parameterName }"
			value="${_csrf.token }">
	</form>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"
		integrity="sha384-xrRywqdh3PHs8keKZN+8zzc5TX0GRTLCcmivcbNJWm2rs5C8PRhcEn3czEjhAO9o"
		crossorigin="anonymous"></script>

</body>
</html>