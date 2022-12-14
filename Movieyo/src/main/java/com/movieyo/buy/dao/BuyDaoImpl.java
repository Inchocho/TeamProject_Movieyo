package com.movieyo.buy.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movieyo.buy.dto.BuyDto;

@Repository
public class BuyDaoImpl implements BuyDao{
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	//mapper의 namespace를 변수로 생성해서 사용하자
	String namespace = "com.movieyo.buy.";

	@Override
	public int buySelectTotalCount(String searchOption, String keyword, int userNo, int userAdmin) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		map.put("userNo", userNo);
		map.put("userAdmin", userAdmin);
		
		return sqlSession.selectOne(namespace + "buySelectTotalCount", map);
	}

	@Override
	public List<Map<String, Object>> buySelectList(String searchOption, String keyword, int start, int end,
			int userNo, int userAdmin) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);		
		map.put("userNo", userNo);
		map.put("userAdmin", userAdmin);	// --> 세션에서 유저어드민을 가져와서 관리자 확인
		map.put("start", start);
		map.put("end", end);
		
		System.out.println(userAdmin + "다오임플 유저어드민 권한");
		
		return sqlSession.selectList(namespace + "buySelectList", map);
	}

	@Override
	public int buyInsertOne(BuyDto buyDto) {
		// TODO Auto-generated method stub
		return sqlSession.insert(namespace + "buyInsertOne", buyDto);
	}

	@Override
	public int buyExistOne(int userNo, int movieNo) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		map.put("movieNo", movieNo);
		
		return sqlSession.selectOne(namespace + "buyExistOne", map);
	}
	
	
	@Override
	public int buyStatusCheck(int userNo, int movieNo) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		map.put("movieNo", movieNo);
		
		return sqlSession.selectOne(namespace + "buyStatusCheck", map);
	}
	
	@Override
	public void buyStatusUpdate(int userNo, int movieNo) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		map.put("movieNo", movieNo);
		
		sqlSession.selectOne(namespace + "buyStatusUpdate", map);
	}

	@Override
	public int selectRefundNo(int userNo, int movieNo) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		map.put("movieNo", movieNo);
		
		return sqlSession.selectOne(namespace + "selectRefundNo", map);
	}

	@Override
	public void refundRequestUpdate(int buyNo) {
		// TODO Auto-generated method stub
		sqlSession.update(namespace + "refundRequestUpdate", buyNo);
	}

	@Override
	public void refundRequestDeny(int buyNo) {
		// TODO Auto-generated method stub
		sqlSession.update(namespace + "refundRequestDeny", buyNo);
	}

	@Override
	public int totalMoney() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "totalMoney");
	}

	@Override
	public int refundMoney() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "refundMoney");
	}		
	
	@Override
	public int refundCount() {
		
		return sqlSession.selectOne(namespace + "refundCount");
	}

}
