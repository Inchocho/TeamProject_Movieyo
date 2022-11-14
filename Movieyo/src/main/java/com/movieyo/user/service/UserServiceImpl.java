package com.movieyo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.movieyo.user.dao.UserDao;
import com.movieyo.user.dto.UserDto;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	public UserDao userDao;
	
	@Override
	public UserDto userExist(String email, String password) {
		// TODO Auto-generated method stub
		return userDao.userExist(email, password);
	}

	@Override
	public void userInsertOne(UserDto userDto) throws Exception {
		// TODO Auto-generated method stub
		
	}
}