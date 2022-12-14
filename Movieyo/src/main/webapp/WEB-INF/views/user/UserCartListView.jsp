<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>		

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Moviyo</title>

<style type="text/css">
table, tr, td, th{
	border:1px solid black; 
}
td{
	height: 50px;
}
table {
	border-collapse: collapse;
}
.curPageDiv{
	margin: 0px 0px 0px 200px;
	text-align: center;
	min-width: 600px;
}
.titleContainer{
	border-bottom: 2px solid #252525;
	margin: 3px 3px 3px 0px;
	padding-right: 600px;
	min-width: 200px;
}
.title_con_title{
	margin-left: 20px;
}
.contContainer{
	width: 800px;
    padding: 10px 0 0 30px;
    margin: auto;
    font-size: 20px;
}
.contContainer table{
	width: 800px;
	text-align: center;
}
  
#ul li {
  list-style-type: none;
  float: left;
  margin-left: 10px;
  }
 
th {
	background-color: gray;
}
.contContainer input[type="checkbox"]{
	width: 20px;
	height: 20px;
}
.cartSelectInfo, .csiCkBox, .csiCkBoxView{
	display: flex;
}
.cartSelectInfo{
	flex-direction: column;
	align-items: center;
	margin-top: 20px;
}
.csiCkBox{
	align-items: center;
	width: 300px;
	justify-content: space-between;
	font-size: 16px;
}
.csiCkBoxView{
	flex-direction: column;
	text-align: left;
}
.btnArea{
	width: 300px;
	display: flex;
	flex-direction: row-reverse;
	justify-content: space-between;
	margin-top: 10px;
}
#buyCartSelBtn{
    background-color: #02ace0;
}
#delCartSelBtn{
    background-color: #fd7d40;
}
#buyCartSelBtn, #delCartSelBtn{
	width: 130px;
    border: 2px solid black;
    color: #fff;
    margin: 0px;
}
#buyCartSelBtn:hover, #delCartSelBtn:hover{
	background-color: rgba(135, 206, 235, 0.3);
	color: #ff81ab;
}
</style>

</head>

<body>
	<header>
	<jsp:include page="/WEB-INF/views/Header.jsp"/>
	</header>
	<jsp:include page="/WEB-INF/views/UserMyPageSideMenu.jsp"></jsp:include>
	<div class="curPageDiv">
	
	<div class="titleContainer">
		<h1>장바구니</h1>
	</div>
		
	<div class="contContainer">
	<form id="cartSelectForm">
	<table>
		<tr>
			<th>영화제목</th><th>가격(원)</th><th>담은날짜</th><th>선택</th>
		</tr>
		<c:if test="${not empty cartList}">
		<c:forEach var="cart" items="${cartList}">
		
		<tr>
			<td id="tdMtitle${cart.cartNo}">${cart.movieTitle}</td>
			<td id="cartPsel${cart.cartNo}">${cart.moviePrice}</td>
			<td><fmt:formatDate pattern="YYYY-MM-dd" value="${cart.inCartDate}"/></td>
			<td>
			<input type="hidden" id="cartSelMN${cart.cartNo}" value="${cart.movieNo}">
			<input type="checkbox" id="cartSelCN${cart.cartNo}" value="${cart.cartNo}">
			</td>
		</tr>
		</c:forEach>
	
		</c:if>
		<c:if test="${empty cartList}">
			<tr>
				<td colspan="4" id="tdId">장바구니가 비었습니다</td>
			</tr>		
		</c:if>
	</table>
	<div style="display: flex; flex-direction: row-reverse;">
	  <div class="cartSelectInfo">
		<div class="csiCkBox">
			<div class="csiCkBoxView">
			<input type="hidden" id="cartSelCount" value="0">
			<input type="hidden" id="cartSelPrice" name="sumPrice" value="0">
			<span>선택한항목:　<span id="cartSelCountView"></span><span style="float: right;">　개</span></span>
			<span>가격합계:　<span id="cartSelPriceView"></span><span style="float: right;">　원</span></span></div>
			<div style="display: flex; align-items: center;">전체선택<input type="checkbox" id="allck"></div>
		</div>
		<div class="btnArea">
			<input type="button" value="구매하기" id="buyCartSelBtn" class="body_btn_css">
			<input type="button" value="제외하기" id="delCartSelBtn"  class="body_btn_css">
		</div>
	</div></div>
	</form>

	<jsp:include page="/WEB-INF/views/common/CartPaging.jsp"/>
	
	<form action="./list.do" id="pagingForm" method="post">
		<input type="hidden" id="curPage" name="curPage"
			value="${pagingMap.cartPaging.curPage}">
		<input type="hidden"  name="keyword" value="${searchMap.keyword}">
		<input type="hidden"  name="searchOption" value="${searchMap.searchOption}">
	</form>
	
	<form action="./list.do" method="post">
		<select name="searchOption">
			<c:choose>
				<c:when test="${searchMap.searchOption == 'all'}">
					<option value="all"<c:if test="${searchMap.searchOption eq 'all'}">selected</c:if>>제목 + 담은날짜(월/일)</option>
					<option value="MOVIE_TITLE">제목</option>					
					<option value="CART_INCARTDATE">담은날짜(월/일)</option>
				</c:when>
				<c:when test="${searchMap.searchOption == 'MOVIE_TITLE'}">
					<option value="all">제목 + 담은날짜(월/일)</option>
					<option value="MOVIE_TITLE"<c:if test="${searchMap.searchOption eq 'MOVIE_TITLE'}">selected</c:if>>제목</option>
					<option value="CART_INCARTDATE">담은날짜(월/일)</option>
				</c:when>				
				<c:when test="${searchMap.searchOption == 'CART_INCARTDATE'}">
					<option value="all">제목 + 담은날짜(월/일)</option>
					<option value="MOVIE_TITLE">제목</option>					
					<option value="CART_INCARTDATE"<c:if test="${searchMap.searchOption eq 'CART_INCARTDATE'}">selected</c:if>>담은날짜(월/일)</option>
				</c:when>				
			</c:choose>
		</select>
		
		<input type="text" name="keyword" value="${searchMap.keyword}" placeholder="검색">
		<input type="submit" value="검색">
	</form>
		
	</div>
	</div>
	<jsp:include page="/WEB-INF/views/PopUp/CartBuyPop.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/PopUp/CartDelPop.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/Tail.jsp"/>
	
</body>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.1.js"></script>
<script type="text/javascript">
//선택항목,합계 보여주기
var htmlStr = $('#cartSelCount').val();
$('#cartSelCountView').html(htmlStr);
	htmlStr = $('#cartSelPrice').val();
	htmlStr = comma(htmlStr);
$('#cartSelPriceView').html(htmlStr);
	//다른작업시 넣을 Fnc
	function selViewRefresh() {
		var htmlStr = $('#cartSelCount').val();
		$('#cartSelCountView').html(htmlStr);
			htmlStr = $('#cartSelPrice').val();
			htmlStr = comma(htmlStr);
		$('#cartSelPriceView').html(htmlStr);
	};

//선택항목구매하기 모달창띄우기
var buyCartSelBtn = document.getElementById("buyCartSelBtn");
var popup_layer_cartbuy = document.getElementById("popup_layer_cartbuy");

buyCartSelBtn.addEventListener("click", function(e) {
	var cartSelCount = document.getElementById("cartSelCount");
	if (cartSelCount.value == 0) {
		e.preventDefault();
		alert("선택하신항목이 없습니다.")
	}else{
		var htmlStr = "";
		htmlStr = $('#cartSelCount').val() -1;
		$('#selCountMinOne').html(htmlStr);
		
		htmlStr = $('#cartSelPrice').val();
		htmlStr = comma(htmlStr);
		$('#selPrice').html(htmlStr);
		
		htmlStr = $('#popViewUserCash').text();
		htmlStr = comma(htmlStr);
		$('#popViewUserCash').html(htmlStr);
		
		var checkedFir = $('input[name=cartNo]').first();
		var findMtitle = "#tdMtitle" + checkedFir.val();
		htmlStr = checkedFir.parent().siblings(findMtitle).text();
		$('#selMovieTitleFir').html(htmlStr);
		
		htmlTag.classList.toggle('popup_focus');
		popup_layer_cartbuy.style.visibility = "visible";
	}
});
	//선택항목 구매 submit
	var buyBtn = document.getElementById("buyBtn");
	buyBtn.addEventListener("click", function(e) {
		$("#cartSelectForm").attr("action", "/Movieyo/cart/buyCart.do");
		$("#cartSelectForm").submit();
	});
//선택항목 제외하기 모달창띄우기
var delCartSelBtn = document.getElementById("delCartSelBtn");
var popup_layer_cartdel = document.getElementById("popup_layer_cartdel");

delCartSelBtn.addEventListener("click", function(e) {
	var cartSelCount = document.getElementById("cartSelCount");
	if (cartSelCount.value == 0) {
		e.preventDefault();
		alert("선택하신항목이 없습니다.")
	}else{
		var htmlStr = "";
		
		$("input[name=cartNo]").each(function(i, element) {
			var selMTID = '#tdMtitle' + $(this).val();
			var delListMtitle = $(selMTID).text();
			htmlStr += '<li>'+delListMtitle+'</li>'
		});
		
		$('#cartdel_cont_ul').html(htmlStr);
		
		htmlTag.classList.toggle('popup_focus');
		popup_layer_cartdel.style.visibility = "visible";
	}
});
	//선택항목 제외 submit
	var delBtn = document.getElementById("delBtn");
	delBtn.addEventListener("click", function(e) {
		$('#cartSelectForm').attr("action", "/Movieyo/cart/deleteCart.do");
		$('#cartSelectForm').submit();
	});
	
//체크박스 선택
	var count = 0;
	var sumPrice = 0;
	

	$("input[id^='cartSelCN']").bind('change', function(){
		
		var findMN ='#cartSelMN' + $(this).val();
		
		if ($(this).is(':checked')) {
			$(this).attr("name", "cartNo");
			$(this).siblings(findMN).attr("name", "movieNo");
			sumPrice += parseInt($(this).parent().siblings("td[id^='cartPsel']").text());
			count++;
		}else{
			$(this).removeAttr("name");
			$(this).siblings(findMN).removeAttr("name");
			sumPrice -= parseInt($(this).parent().siblings("td[id^='cartPsel']").text());
			count--;
		}
		$('#cartSelCount').val(count);
		$('#cartSelPrice').val(sumPrice);
		selViewRefresh();
	});
//체크박스 전체선택
	var countAll = $("input[id^='cartSelCN']").length;
	var sumPriceAll =0;
	$("td[id^='cartPsel']").each(function() {
		sumPriceAll += parseInt($(this).text());
	});
	
$('#allck').change(function() {
	if ($(this).is(':checked')) {
		$("input[id^='cartSelCN']").prop('checked',true);
		$("input[id^='cartSelCN']").attr("name", "cartNo");
		$("input[id^='cartSelMN']").attr("name", "movieNo");
		count = countAll;
		sumPrice = sumPriceAll;
	}else {
		$("input[id^='cartSelCN']").prop('checked',false);
		$("input[id^='cartSelCN']").removeAttr("name");
		$("input[id^='cartSelMN']").removeAttr("name");
		count = 0;
		sumPrice = 0;
	}
	$('#cartSelCount').val(count);
	$('#cartSelPrice').val(sumPrice);
	selViewRefresh();
});



// 숫자 콤마 포멧터
	function comma(str) {
        str = String(str);
        return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
    }

    function uncomma(str) {
        str = String(str);
        return str.replace(/[^\d]+/g, '');
    } 
    
    function inputNumberFormat(obj) {
        obj.value = comma(uncomma(obj.value));
    }
    
    function inputOnlyNumberFormat(obj) {
        obj.value = onlynumber(uncomma(obj.value));
    }
    
    function onlynumber(str) {
	    str = String(str);
	    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g,'$1');
	}

</script>
</html>