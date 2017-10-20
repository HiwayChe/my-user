package com.my.user.service;

import com.my.user.pojo.User;

public interface UserService {

	User getById(Integer id);
	
	User getByName(String name);
}
