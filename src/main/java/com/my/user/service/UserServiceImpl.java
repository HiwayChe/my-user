package com.my.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.my.user.mapper.UserMapper;
import com.my.user.pojo.User;
import com.my.user.pojo.UserExample;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User getById(Integer id) {
		return this.userMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public User getByName(String name) {
		UserExample ex = new UserExample();
		UserExample.Criteria cri = ex.createCriteria();
		cri.andNameEqualTo(name);
		List<User> userList = this.userMapper.selectByExample(ex);
		return CollectionUtils.isEmpty(userList) ? null : userList.get(0);
	}
	
}
