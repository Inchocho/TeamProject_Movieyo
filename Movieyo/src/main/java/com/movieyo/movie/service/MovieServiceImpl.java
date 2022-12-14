package com.movieyo.movie.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.movieyo.movie.dao.MovieDao;
import com.movieyo.movie.dto.MovieDto;
import com.movieyo.util.FileUtils;

@Service
public class MovieServiceImpl implements MovieService{
	
	private static final Logger log
		= LoggerFactory.getLogger(MovieServiceImpl.class);

	@Autowired
	public MovieDao movieDao;
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Override
	public void movieInsertOne(MovieDto movieDto, MultipartHttpServletRequest mulRequest) throws Exception {
		// TODO Auto-generated method stub
		movieDao.movieInsertOne(movieDto, mulRequest);
		
		Iterator<String> iterator = mulRequest.getFileNames();
		MultipartFile multipartFile = null;
		
		while(iterator.hasNext()) {
			multipartFile = mulRequest.getFile(iterator.next());
			
			if(multipartFile.isEmpty() == false) {
				log.debug("-------- file start --------");
				
				log.debug("name : {}", multipartFile.getName());
				log.debug("fileName : {}", multipartFile.getOriginalFilename());
				log.debug("size : {}", multipartFile.getSize());
				
				log.debug("-------- file end --------\n");
			}
		}	// while end
		
		//10.18파일 업로드에 들어가는 memberDto.getNo를 처리하기위해 생성후 조회하는 쿼리를  수행
		//int parentSeq = memberDao.getMemberNo();
		
		//mapper에서 generateKey를 통해 getNo를 얻어옴
		int parentSeq = movieDto.getMovieNo();
			
		List<Map<String, Object>> list
		= fileUtils.parseInsertFileInfo(parentSeq
			, mulRequest);
		
		//다수의 동시 업로드를 처리하기 위해 list를 사용함 - 기존 단건 업로드시 Map형식으로 작성하면 끝
		for (int i = 0; i < list.size(); i++) {
			movieDao.insertFile(list.get(i));
		}		
	}

	@Override
	public int movieSelectTotalCount(String searchOption, String keyword) {
		// TODO Auto-generated method stub
		return movieDao.movieSelectTotalCount(searchOption, keyword);
	}

	@Override
	public List<MovieDto> movieSelectList(String searchOption, String keyword, int start, int end) {
		// TODO Auto-generated method stub
		// movieNo를 뽑을 쿼리 
		
		return movieDao.movieSelectList(searchOption, keyword, start, end);
	}
	
	@Override
	public List<MovieDto> movieSelectList_title() {
		// TODO Auto-generated method stub
		return movieDao.movieSelectList_title();
	}

	@Override
	public List<Map<String, Object>> fileList_title() {
		// TODO Auto-generated method stub
		return movieDao.fileList_title();
	}

	@Override
	public Map<String, Object> movieSelectOne(int movieNo) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		MovieDto movieDto = movieDao.movieSelectOne(movieNo);
		
		resultMap.put("movieDto", movieDto);
		
		List<Map<String, Object>> fileList = movieDao.fileselectList(movieNo);
		resultMap.put("fileList", fileList);
		
		return resultMap;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int movieUpdateOne(MovieDto movieDto,
			MultipartHttpServletRequest multipartHttpServletRequest, int fileIdx) throws Exception {
		// TODO Auto-generated method stub
		int resultNum = 0;
		
		System.out.println(resultNum + "############1번과정#############");
		resultNum = movieDao.movieUpdateOne(movieDto);
		
		System.out.println(resultNum + "#############2번과정##############");
		int movieNo = movieDto.getMovieNo();
		
		// 파일 확인 로직 추가
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		MultipartFile multipartFile = null;
		
		while(iterator.hasNext()) {
			
			System.out.println("새로들어온 파일이 있나요?");
			
			multipartFile = multipartHttpServletRequest.getFile(iterator.next());
			
			if(multipartFile.isEmpty() == false) {
				log.debug("----file----");
				
				log.debug("name : {} ", multipartFile.getName());
				log.debug("fileName : {} ", multipartFile.getOriginalFilename());
				log.debug("size : {} ", multipartFile.getSize());
				
				log.debug("----file end----");
				
				
				try {
					int parentSeq = movieDto.getMovieNo();
					
					List<Map<String, Object>> list 
					= fileUtils.parseInsertFileInfo(parentSeq, multipartHttpServletRequest);
					
					//imgMap에 값이 들어온다 -> 해당 영화번호에 사진이 있다는것을 의미
					Map<String, Object> imgMap = movieDao.fileSelectOne(parentSeq);
					
					//fileIdx - 기존 파일인덱스, fileIdx2 새로만든 인덱스
					
					//해당 영화에 사진이 있으면? 
					if(imgMap != null) {
						
							fileUtils.parseUpdateFileInfo(imgMap);
							movieDao.fileDelete(parentSeq);
							
							for(Map<String, Object> map : list) {
								movieDao.insertFile(map);
							}

					}else {
						//해당 영화에 사진이 없으면?
						for(Map<String, Object> map : list) {
							movieDao.insertFile(map);
						}
							
					}
					
				} catch (Exception e) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}				
			}
		}
	
	return resultNum;
	
	}

	@Override
	public void movieDeleteOne(int movieNo) {
		// TODO Auto-generated method stub
		
		movieDao.movieDeleteOne(movieNo);
	}

	@Override
	public List<Integer> movieNoSelect(String searchOption, String keyword) {
		// TODO Auto-generated method stub
		return movieDao.movieNoSelect(searchOption, keyword);
	}

	@Override
	public Map<String, Object> moviefileOne(int movieNo2) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> fileList = movieDao.fileselectList(movieNo2);
		
		
		resultMap.put("fileList", fileList);
		
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> genreSelect(int userNo) {
		// TODO Auto-generated method stub
		return movieDao.genreSelect(userNo);
	}


	
}
