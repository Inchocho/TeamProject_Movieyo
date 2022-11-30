<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %>    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix='c' %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>영화추가</title>

<style type="text/css">
/* .file input[type="file"] {  /* 파일 필드 숨기기 */ */
/*   position: absolute; */
/*   width: 1px; */
/*   height: 1px; */
/*   padding: 0; */
/*   margin: -1px; */
/*   overflow: hidden; */
/*   clip:rect(0,0,0,0); */
/*   border: 0; */
/* } */

/* .file label { */
/*   display: inline-block; */
/*   padding: .5em .75em; */
/*   color: #999; */
/*   font-size: inherit; */
/*   line-height: normal; */
/*   vertical-align: middle; */
/*   background-color: black; */
/*   cursor: pointer; */
/*   border: 1px solid #ebebeb; */
/*   border-bottom-color: #e2e2e2; */
/*   border-radius: .25em; */
/* } */

</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script type="text/javascript">
	
	
	

	$(function (){
		$("#genreNo").click(function () {
			var a = $("#genreNo option:checked").text();
			$("#genreName").val(a);
		});
	});
	
// 			$("#moviegenre").val()= $("#genreNo option:checked").text();
// 			$("#moviegenre").val() = $("#genreNo option:checked").text();
		
	
	
</script>
</head>
<body>
	<jsp:include page="../Header.jsp"/>
	<h1>영화등록</h1>	
	<form action="./addCtr.do" method="post" id='submitForm'
		enctype="multipart/form-data">
		<div class="file">
			<label for="file">사진등록</label>
			<input type='file' name='file' id="file">
		</div>
		영화제목:	<input type="text" name='movieTitle' id='movieTitle'><br>
		제작연도: <input type="text" name='prdtYear' id='prdtYear'><br>
		국가: <input type="text" name='nation' id='nation'><br>
		감독: <input type="text" name='director' id='director'><br>
		장르: <select name="genreNo" id="genreNo">
				 <option value="1">애니메이션</option>
			     <option value="2">드라마</option>
			     <option value="3">코미디</option>
			     <option value="4">다큐멘터리</option>
			     <option value="5">범죄</option>
			     <option value="6">성인물(에로)</option>
			     <option value="7">SF</option>
			     <option value="8">멜로/로맨스</option>
			     <option value="9">어드벤처</option>
			     <option value="10">공포(호러)</option>
			     <option value="11">스릴러</option>
			     <option value="12">기타</option>
		     </select> <br>
		<input type="hidden" name="genreName" id="genreName" value="">
		상영시간: <input type='text' name='runtime' id='runtime'><br>
		상영등급: <input type='text' name='grade' id='grade'><br>
		가격: <input type="number" step="1000" name='price' id='price'><br>
		영화내용: <br>
		<textarea name="movieStory" style="width: 400px; height: 400px;"></textarea><br>
		등록자: <input type="text" name="registrant" value="${userDto.userName}" readonly="readonly"><br>
		
		<input type='submit' value='등록' id='submitBtn'>
		<input type="button" value="취소">				
	</form>
</body>
</html>