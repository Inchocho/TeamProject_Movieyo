package com.movieyo.buy.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.movieyo.buy.dto.BuyDto;
import com.movieyo.buy.service.BuyService;
import com.movieyo.cart.service.CartService;
import com.movieyo.movie.dto.MovieDto;
import com.movieyo.refund.service.RefundService;
import com.movieyo.user.dto.UserDto;
import com.movieyo.user.service.UserService;
import com.movieyo.util.Paging;

@Controller
public class BuyController {
	
	private static final Logger logger 
	= LoggerFactory.getLogger(BuyController.class);
	
	@Autowired
	private BuyService buyService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RefundService refundService;
	
	@Autowired
	private CartService cartService; 
	
	// 세션을 받아옴 --> userAdmin 정보를 사용해서 관리자 전용 쿼리를 실행
	@RequestMapping(value = "/buy/list.do"
			, method = {RequestMethod.GET, RequestMethod.POST})
	public String buyList(@RequestParam(defaultValue = "1") int curPage, Model model,
			@RequestParam(defaultValue = "all")String searchOption
		  , @RequestParam(defaultValue = "")String keyword
		  , HttpSession session) {
		
		UserDto userDto = (UserDto) session.getAttribute("userDto");
		
		int userNo = userDto.getUserNo();
		
		int userAdmin = userDto.getUserAdmin();	
		
		logger.info("Welcome BuyController buyList! curPage: {}" + ", searchOption: {}"
				, curPage, searchOption);		
		
		logger.info("keyword: {}",keyword);	
		
		int totalCount = buyService.buySelectTotalCount(searchOption, keyword, userNo, userAdmin);
		
		logger.info("totalCount: {}", totalCount);
		
		Paging buyPaging = new Paging(totalCount, curPage);		
		
		int start = buyPaging.getPageBegin();
		int end = buyPaging.getPageEnd();		
				
		List<Map<String, Object>> listMap = buyService.buySelectList(searchOption, keyword, start, end, userNo, userAdmin);
		
		//sql 페이징 쿼리실행결과 + 토탈카운트를 담아서 멤버리스트와 같이 모델에 담아준다
		//map을 활용하면 다양한 데이터를 쉽게 객체를 만들 수 있다
		//Map의 value타입이 Object인 이유 -> 스프링은 객체지향 프로그래밍 
		Map<String, Object> pagingMap = 
				new HashMap<String, Object>();
		
		//Map에다가 totalCount, memberPaging을 key로해서 담고
		pagingMap.put("totalCount", totalCount);
		pagingMap.put("moviePaging", buyPaging);
		
		Map<String, Object> searchMap = 
				new HashMap<String, Object>();
		
		searchMap.put("searchOption", searchOption);
		searchMap.put("keyword", keyword);
		
		logger.info("curPage: {}", curPage);
		logger.info("curBlock: {}", buyPaging.getCurBlock());			
		
		List<Map<String, Object>> buyListMap = new ArrayList<Map<String,Object>>();
		
		for (int i = 0; i < listMap.size(); i++) {
			Map<String, Object> buyMap = new HashMap<String, Object>();
			
			int moviePrice = Integer.parseInt(String.valueOf(listMap.get(i).get("MOVIE_PRICE")));
			int buyNo = Integer.parseInt(String.valueOf(listMap.get(i).get("BUY_NO")));			
			String userNickName = (String)listMap.get(i).get("USER_NICKNAME");
			String movieTitle = (String)listMap.get(i).get("MOVIE_TITLE");
			String buyStatus = (String)listMap.get(i).get("BUY_STATUS");
			Date buyDate = (Date)listMap.get(i).get("BUY_DATE");
			int movieNo = Integer.parseInt(String.valueOf(listMap.get(i).get("MOVIE_NO")));
			int buyUserNo = Integer.parseInt(String.valueOf(listMap.get(i).get("USER_NO")));
			
			buyMap.put("moviePrice", moviePrice);
			buyMap.put("userNickName", userNickName);
			buyMap.put("movieTitle", movieTitle);
			buyMap.put("buyStatus", buyStatus);
			buyMap.put("buyDate", buyDate);		
			buyMap.put("buyNo", buyNo);
			buyMap.put("buyUserNo", buyUserNo);
			buyMap.put("userNo", userNo);
			buyMap.put("movieNo", movieNo);
			
			if(buyStatus.equals("환불처리완료")) {
				int requestDeny = 0;
				buyMap.put("requestDeny", requestDeny);
			}			
			
			buyListMap.add(buyMap);			
			
		}
		
		int totalMoney = 0;
		int refundMoney = 0;
		int refundCount = 0;
		
		try {
			
			
			refundCount = buyService.refundCount();
			if(refundCount != 0) {
				refundMoney = buyService.refundMoney();
				System.out.println(refundMoney + "환불금액");	
			}
			totalMoney = buyService.totalMoney();
			System.out.println(totalMoney + "총액");
		} catch (NullPointerException nullEx) {
			// TODO: handle exception
			System.out.println("환불내역없음");
		}
		model.addAttribute("refundMoney", refundMoney);
		model.addAttribute("totalMoney", totalMoney);
		model.addAttribute("buyListMap", buyListMap);
		model.addAttribute("pagingMap", pagingMap);
		model.addAttribute("searchMap", searchMap);		
		
		return "buy/BuyListView";
	}	
	
	@RequestMapping(value = "/buy/addCtr.do", method = RequestMethod.GET)
	public String buyAddCtr(BuyDto buyDto, Model model, int userNo, int movieNo, int price
			, HttpSession session) {
		logger.trace("Welcome BuyController buyAddCtr 구매내역 추가!!! " 
			+ buyDto);
		
			int isCheck = buyService.buyExistOne(userNo, movieNo);
			
			String viewUrl = "";
		
		try {
			if(isCheck != 0) {	
				System.out.println("이미 존재하는 영화");
				viewUrl =  "redirect:../buy/list.do?userNo=" +  userNo;
			}else {	
				System.out.println("유저 캐쉬 체크");
				int currentCash = userService.userCurrentCash(userNo);
				
				if(currentCash < price) {
					System.out.println("돈이 부족해");
					viewUrl =  "redirect:../user/userMpoint.do";
					
					return viewUrl;
				
				}else{
					//구매성공
					int cartNo = 0;
					try {
					cartNo = cartService.selectCartNo(userNo, movieNo);
						
					cartService.deleteCart(cartNo);
					} catch (NullPointerException nullEx) {
						// TODO: handle exception
						System.out.println("카트에없는 항목구매");
					}
					
					//구매성공(케이스1 환불한 영화 다시 재구매)	
					int buyStatusCheck = 0;
						buyStatusCheck = buyService.buyStatusCheck(userNo, movieNo);
					System.out.println(buyStatusCheck + "구매상태 체크");
						if(buyStatusCheck != 0) {
							//환불한물건 업데이트처리(구매 케이스 1)

							//구매내역에 상태가 있는지 확인
							buyService.buyStatusUpdate(userNo, movieNo);
							userService.userBuyMovie(userNo, price);
							
							//구매시 장바구니에 들어있는지 확인하여
							//장바구니에 들어있는게 확인되면 장바구니 번호를 조회해서 해당 번호를 삭제							

							//환불내역(환불처리완료됨)에서 삭제처리함 
							int refundNo = 0;
							refundNo = buyService.selectRefundNo(userNo, movieNo);
							refundService.refundDelete(refundNo);							
						}else {
						//구매케이스2 아예 처음 구매함
							int buySuccess = 0;					
							buySuccess = buyService.buyInsertOne(buyDto);
							
							if(buySuccess != 0) {
	
								//구매가 성공했으면 유저의 캐쉬를 영화가격만큼 감소
								userService.userBuyMovie(userNo, price);
							}
						}
						Map<String, Object> map =  userService.userSelectOne(userNo);
						UserDto userDto = (UserDto) map.get("userDto");
						
						session.removeAttribute("userDto");
						session.setAttribute("userDto", userDto);
				}
				
				viewUrl =  "redirect:/movie/detail.do?movieNo=" +  movieNo;
			}
			
		} catch (Exception e) {
			System.out.println("오랜만에 예외 처리 한다");
			System.out.println("파일 문제 예외일 가능성 높음");
			e.printStackTrace();
		}
				
		return viewUrl;
	}	

}
