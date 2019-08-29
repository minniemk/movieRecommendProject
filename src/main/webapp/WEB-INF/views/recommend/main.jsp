<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  


<sec:authentication var="mvo" property="principal" /> 
<sec:authorize access="isAuthenticated()">
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

<style>
#caption {
	color: white !important;
	font-family: "Do Hyeon";
	font-size: 40px;
}

.card {
	background-color: transparent !important;
	   border: none;
    margin: auto;
    display: inline-block;
}

#mypick {
	text-align: center;
}

.movietd:hover {
	position: relative;
	margin: 0 auto;
}

.movietd:hover img.movieposter {
	opacity: 0.2;
}

.movietd:hover .movietitle {
	visibility: visible;
}

.movietitle {
	position: absolute;
	vertical-align: middle;
	z-index: 5;
	top: 20%;
	font-size: 25px;
	vertical-align: middle;
	text-align: center;
	color: white;
	visibility: hidden;
	font-family: "Do Hyeon";
	padding: 10px 25px 10px 0px;
	width: 100%;
	margin: 0 auto;
}

.movietitle .btn {
    margin: 5px;
    font-size: 20px!important;
    }
    
.recommendGrade{
    margin: 5px;
    height: 36px;
    font-size: 20px;
    background-color: grey;
    color: white;
    border-color: grey;
    
    }
    
</style>

<title>영화 평가 페이지</title>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.3.1.min.js"></script>
<script>
$(function(){	
	function selectRecommendView(){		
		$.ajax({			
			type:"POST",
			url:"${pageContext.request.contextPath}/recommend/select",
			data:"${_csrf.parameterName}=${_csrf.token}&memberId="+"${mvo.memberId}",
			dataType:"json",
			success:function(result){							
				 var data = "";
				 var data="<form method='post'>";
				 $.each(result, function(index, item){					
					//data += "<input type='hidden' id='movieNo' value="+item.movieNo+" name='movieNo'/>";					
						data+="<input type='hidden' value='"+item.movieNo+"'>";
						data += "<input type='hidden' id='memberId' value='${mvo.memberId}' name='memberId'/>";
						var posterlist = item.moviePoster;
						var movieposter = posterlist
								.split("|", 1);
						data += "<td class='movietd'><input type='hidden' value='"+item.movieNo+"'>";
						data += "<img src='"+movieposter+"'class='movieposter' style='width:100%'>";
						data += "<div class='movietitle'><p style='margin-bottom:1px;'>"+item.movieName+"</p>"
						data +="<a class='btn btn-light' href='${pageContext.request.contextPath}/api/movieDetail/"+item.movieNo+"'>상세 보기</a>";
						data += "<br><input type='button'class='btn btn-light' value='찜하기' name='wishListBtn'>";
						
						data+="<br><select id='"+index+"' name='recommendGrade' class='recommendGrade'>";
						data+="<option value=''>---별점 선택---</option>";
						data+= "<option value='1'>★☆☆☆☆</option>";
						data+= "<option value='2'>★★☆☆☆</option>";
						data+= "<option value='3'>★★★☆☆</option>";
						data+= "<option value='4'>★★★★☆</option>";
						data+= "<option value='5'>★★★★★</option>";
						data+= "</select></div></td>";
						if (index % 4 == 3) {
							data += "</tr>";
						}				 
						});
				data+="</form>";
				
				$("#movieListView").html(data);	
			}
		});	
	} 
	selectRecommendView();
	
	$(document).on("change","select[name=recommendGrade]" ,function(){ // 별점 select 할때마다 ajax처리로 바로바로 영화 들어가게끔 한다.		
		var params = {
			memberId:$("#memberId").val(),
			movieNo:$(this).parent().parent().children().eq(0).val(), // movieNo를 가져오는 방법을 찾지 못해 DOM을 사용했습니다.
			//UI가 바뀌게 되면 수정해야 하니 꼭 말씀해주세요
			recommendGrade:$(this).val(),
			"${_csrf.parameterName}":"${_csrf.token}"
		};
		//console.log($(this).parent().parent().children().first().text());
		$(this).parent().parent().children().remove(); // 평점 누르면서 동시에 행 삭제, db에선 삭제하지않는다.
		
		$.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/recommend/insert",				
			data:params			
		});//ajax
		//5개 다 선택하면 새로 5개 불러옴
		if($("#movieListView").children().children().length==0){
			selectRecommendView();
		}		
	});	
	//버튼 누르면 새로 5개 불러옴
	$("#reSelectRecommendView").click(function(){
		selectRecommendView();
	})
});
</script>
</head>
<body>
	<div class="container" style="display:flex; width:100%;">
		<div id="mypick" class="align-middle" style="width:100%;">
		<div id='caption'>${mvo.memberName}님의 탁월한 안목을 믿습니다.</div>
		<div class="card mb-3">
			<div class="table-responsive">
				<table class="table table-borderless table-dark table-hover" width="100%" cellspacing="0">
					<thead style="text-align: center; vertical-align: middle;"></thead>
	<input class="btn btn-light" type="button" id="reSelectRecommendView" value="다른 영화 없어?"/>
	<div id="movieListContainer">
		<tbody id='movieListView'>
		
		</tbody>
		</div>
	</table>
	</div>
	</div>
	</div>
	</div>
</body>
</html>
</sec:authorize>