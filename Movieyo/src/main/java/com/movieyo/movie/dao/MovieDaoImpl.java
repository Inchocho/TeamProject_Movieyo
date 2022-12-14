package com.movieyo.movie.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.movieyo.movie.dto.MovieDto;

@Repository
public class MovieDaoImpl implements MovieDao{
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	String namespace = "com.movieyo.movie.";

	@Override
	public int movieInsertOne(MovieDto movieDto, MultipartHttpServletRequest mulRequest) {
		// TODO Auto-generated method stub
		return sqlSession.insert(namespace + "movieInsertOne", movieDto);
	}

	@Override
	public void insertFile(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		sqlSession.insert(namespace + "insertFile", map);
	}

	@Override
	public int movieSelectTotalCount(String searchOption, String keyword) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		
		return sqlSession.selectOne(namespace + "movieSelectTotalCount", map);
	}

	@Override
	public List<MovieDto> movieSelectList(String searchOption, String keyword, int start, int end) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("keyword", keyword);
		map.put("start", start);
		map.put("end", end);
		map.put("searchOption", searchOption);
		
		return sqlSession.selectList(namespace + "movieSelectList", map);
	}
	
	@Override
	public List<MovieDto> movieSelectList_title() {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "movieSelectList_title");
	}

	@Override
	public List<Map<String, Object>> fileList_title() {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "fileList_title");
	}

	@Override
	public MovieDto movieSelectOne(int movieNo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "movieSelectOne", movieNo);
	}

	@Override
	public List<Map<String, Object>> fileselectList(int movieNo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "fileselectList", movieNo);
	}

	@Override
	public int movieUpdateOne(MovieDto movieDto) {
		// TODO Auto-generated method stub
		return sqlSession.update(namespace + "movieUpdateOne", movieDto);
	}

	@Override
	public void movieDeleteOne(int movieNo) {
		// TODO Auto-generated method stub
		sqlSession.delete(namespace + "movieDeleteOne", movieNo);
	}

	@Override
	public List<Integer> movieNoSelect(String searchOption, String keyword) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		
		return sqlSession.selectList(namespace + "movieNoSelect", map);
		
	}

	@Override
	public Map<String, Object> fileSelectStoredFileName(int parentSeq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "fileSelectStoredFileName", parentSeq);
	}

	@Override
	public int fileDelete(int idx) {
		// TODO Auto-generated method stub
		return sqlSession.delete(namespace + "fileDelete", idx);
	}

	@Override
	public List<Map<String, Object>> genreSelect(int userNo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "genreSelect", userNo);
	}

	@Override
	public Map<String, Object> fileSelectOne(int fileIdx) {
		// TODO Auto-generated method stub
		
		return sqlSession.selectOne(namespace + "fileSelectOne", fileIdx);
	}

}
