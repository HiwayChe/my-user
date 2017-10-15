package com.my.user.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.my.user.mapper.UserMapper;

public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
}
