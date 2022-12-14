<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>영화관리</title>

<style type="text/css">
table, tr, td, th {
	border: 1px solid black;
}

table {
	border-collapse: collapse;
}

#tdId {
	width: 500px;
	height: 500px;
	text-align: center;
	font-weight: bolder;
}

#ul li {
	list-style-type: none;
	float: left;
	margin-left: 10px;
}

th {
	background-color: gray;
}

#alreadyBuy {
	background-color: rgba(135, 206, 235, 0.3);
	cursor: default;
	width: 400px;
}

#backBtn {
	z-index: 10;
	position: absolute;
	width: 60px;
	height: 40px;
	font-size: 30px;
	margin-top: 23px;
}
.select {
	height: 30px;
	margin: 15px 0px;
	border: solid 1px #bfbfbf;
	border-radius: 4px;
	background: #fff;
}

.file {
	display: flex;
	flex-direction: column;
	padding: 10px 10px 0px 10px;
}
.input, .textarea {
	width: 280px;
	height: 30px;
	position: relative;
	border: solid 1px #bfbfbf;
	border-radius: 4px;
	background: #fff;
}

.textarea {
	width: 858px;
	height: 300px;
	margin: 5px;
	padding: 10px;
	font-size: 18px;
	font-weight: bold;
	font-family: sans-serif;
}

.movieInfo {
	display: flex;
	align-items: center;
	height: 45px;
	border-bottom: 1px dashed #d1d1d1;
	margin: 2px 0px;
}
.p {
	width: 100px;
	height: 30px;
	margin: 0px 30px 0px 0px;
	font-size: 20px;
	margin-block: initial;
	height: 30px;
}

.errorM {
	display: none;
	color: red;
	height: 30px;
}

img {
	width: 250px;
	height: 370px;
}

.curPageDiv {
	margin: 0px;
	text-align: center;
	min-width: 892px;
}

.titleContainer{
	border-bottom: 2px solid #252525;
	margin: 3px 3px 3px 0px;
	padding-right: 600px;
}

.contContainer {
	width: 892px;
	padding: 10px 0 0 30px;
	margin: auto;
}

.detailFormDiv {
	border: 1px solid black;
}

.detailFormDiv, .detailForm_top, .detailForm_bottom {
	display: flex;
	text-align: left;
}

.detailFormDiv, .detailForm_bottom {
	flex-direction: column;
}
.detailForm_top{
	border-bottom: 1px dashed #d1d1d1;
}
.movieInfo_area {
	display: flex;
	flex-direction: column;
	align-items: flex-start;
	height: auto;
	border-bottom: 1px dashed #d1d1d1;
	margin-top: 10px;
}

.btn_area {
	display: flex;
	flex-direction: row;
	justify-content: flex-end;
}

</style>

<script type="text/javascript">
	function deleteFnc(movieNo){
		var url = "./deleteCtr.do?movieNo=" + movieNo;
		location.href = url;
	}
	
	function movieList() {
		location.href = './list.do';
	}
		

</script>

</head>
	
<body>
	<jsp:include page="../Header.jsp" />
<input type="button" class="body_btn_css" id="backBtn" value="←" onclick="movieList();">
<div class="curPageDiv">
<div class="titleContainer">
	<h1>(관리자)영화상세</h1>
</div>
		
<div class="contContainer">
	<form id="detailForm" action='./update.do' method='post'>
	<div class="detailFormDiv">
	<div class="detailForm_top">
		<div class="file">
			<c:forEach var="row" items="${fileList}">
				<img alt="image not found" src="<c:url value='/image/${row.STORED_FILE_NAME}'/>">
			</c:forEach>
		</div>
		<div style="width: 620px; margin-top: 13px;">
			<div class="movieInfo">
				<p class="p">영화제목</p>
				<p class="p" style="width: 250px;">${movieDto.movieTitle}</p>
			</div>
			<div class="movieInfo">
				<p class="p">제작연도</p>
				<p class="p"><fmt:formatDate pattern="yyyy" value="${movieDto.prdtYear}"/> 년</p>
			</div>
			<div class="movieInfo">
				<p class="p">국가</p>
				<p class="p">${movieDto.nation}</p>
			</div>
			<div class="movieInfo">
				<p class="p">감독</p>
				<p class="p">${movieDto.director}</p>
			</div>
			<div class="movieInfo">
				<p class="p">장르</p>
				<p class="p">${movieDto.genreName}</p>
			</div>
			<div class="movieInfo">
				<p class="p">상영시간</p>
				<p class="p">${movieDto.runtime} 분</p>
			</div>
			<div class="movieInfo">
				<p class="p">상영등급</p>
				<p class="p" style="width: 210px;">${movieDto.grade}세 미만 관람 불가</p>
			</div>
			<div class="movieInfo" style="border: none;">
				<p class="p">가격</p>
				<p class="p">${movieDto.price} 원</p>
			</div>
		</div>
	</div>
	<div class="detailForm_bottom" style="width: 891px;">
		<div class="movieInfo_area">
			<div class="movieInfo" style="border: none; margin-left: 10px">
				<p class="p">내용</p>
			</div>
				<h3 class="textarea" id="movieStoryArea">${movieDto.movieStory}</h3>
		</div>
		<div class="btn_area">
			<input type="submit" value='수정' class="body_btn_css">
			<input type="button" value="삭제" onclick="deleteFnc(${movieDto.movieNo});" class="body_btn_css">
			<input type="button" value="목록" onclick="movieList();" class="body_btn_css">
		</div>
<input type="hidden" name="userNo" value="${userDto.userNo}">
<input type="hidden" name="userCash" value="${userDto.userCash}">
<input type="hidden" name="movieNo" value="${movieDto.movieNo}">
<input type="hidden" name="genreNo" value="${movieDto.genreNo}">
<input type="hidden" name="genreName" value="${movieDto.genreName}">
<input type="hidden" name='movieTitle' value="${movieDto.movieTitle}">
<input type="hidden" name='prdtYear' value="${movieDto.prdtYear}">
<input type="hidden" name="nation" value="${movieDto.nation}" > 
<input type="hidden" name="director" value="${movieDto.director}"> 
<input type="hidden" name="runtime" value="${movieDto.runtime}"> 
<input type="hidden" name="grade" value="${movieDto.grade}"> 
<input type="hidden" name="price" value="${movieDto.price}"> 
<input type="hidden" name="registrant" value="${movieDto.registrant}"> 
<input type="hidden" name="creDate" value="${movieDto.creDate}"> 
<input type="hidden" name="rate" value="${movieDto.rate}"> 
<input type="hidden" name="movieStory" value="${movieDto.movieStory}"> 

</div>
</div>
</form>
<input type="hidden" name="likeGenre" value="${likeGenre}">

</div></div>
	<jsp:include page="/WEB-INF/views/PopUp/BuyMoviePop.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/PopUp/InCartMoviePop.jsp"></jsp:include>
	<jsp:include page="../Tail.jsp" />
</body>
</html>