<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
body {
	margin: 0px;
}
.headerDiv{
	background-color: black;
	height: 40px;
}
.ulFlexBox {
	display: flex;
	list-style: none;
	padding: 5px;
	align-items: center;
	min-width: 800px;
	margin: 0px;
}
#adminHeaderUl{
	justify-content: flex-end;
}
#mainLogo{
	color: #F08080;
	font-weight: bold;
	font-style: oblique;
	width: 100px;
	margin: 0px;
	display: block;
    font-size: 1.5em;
	text-decoration: none;
}
.searchBox{
	margin: 0 auto 0 auto;
}
</style>
</head>
<body>
	<div class="headerDiv">
		<ul class="ulFlexBox">
			<li style="display: flex; align-items: center;">
				<a id="mainLogo" href="#" onclick="movePageMainFnc();">무비요</a>
				<input type="button" value="순위">
				<input type="button" value="작품전체">
				<!-- 유저로그인시  [test = "${user.admin ne 1}"(?)]-->
				<c:if test="true">
				<input type="button" value="추천작품">
				</c:if>
			</li>
			<li style="margin-left: 50px; "><input type="button" value="게시판"></li>
			<li class="searchBox">
			<input type="button" value="검색"><input type="search" value="" placeholder="영화를 검색해보세요">
			</li>
			<li style="color: white; padding-right: 20px;">${userDto.nickname} 님　
				<input type="button" value="내정보">
				<input type="button" value="로그아웃">
			</li>
		</ul>
	</div>
	<!-- 관리자로그인시 [test = "${user.admin eq 1}"(?)]-->
	<c:if test="true">
		<div style="background-color: black; height: 15px;">
			<hr	style="display: inline-flex; color: white; align-content: flex-start; width: -webkit-fill-available;">
		</div>
		<div class="headerDiv">
			<ul class="ulFlexBox" id="adminHeaderUl">
				<li>
					<input type="button" value="영화등록">
				</li>
				<li>
					<input type="button" value="영화관리">
				</li>
				<li>
					<input type="button" value="회원관리">
				</li>
				<li>
					<input type="button" value="환불관리">
				</li>
				<li style="padding-right: 20px;">
					<input type="button" value="게시판관리">
				</li>
			</ul>
		</div>
	</c:if>
</body>
</html>