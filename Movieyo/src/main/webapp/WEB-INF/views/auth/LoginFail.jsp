<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>로그인</title>

<script type="text/javascript">
function findPw() {
	location.href = '../UserFindpw.do';
}


			



</script>

<style type="text/css">
a{
	color: red;
}

</style>
</head>

<body>
	<h2>로그인</h2>
	
	<form action="./loginCtr.do" method="post">
		이메일<br>
		<input type="text" name="email" id="emailInp"><br>
		
		암호<br>
		<input type="password" name="password"><br>
				<input type="submit" value="로그인">
		<p>입력하신 내용을 다시 확인해주세요.</p>>
	</form>
		<a href="../UserFindpw.do">비밀번호를 잊으셨나요?</a>
		<br>
		<button type="button" onclick="location.href ='../user/add.do'">새 계정 만들기</button>
		
</body>

</html>