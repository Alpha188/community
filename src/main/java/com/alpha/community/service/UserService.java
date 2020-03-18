package com.alpha.community.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.community.mapper.UserMapper;
import com.alpha.community.model.User;
import com.alpha.community.model.UserExample;
import com.alpha.community.service.UserService;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	/**
	 * 认证token是否存在，存在返回用户信息
	 */

	public void insertOrUpdateUser(User user) {
		UserExample userExample = new UserExample();
		userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
		List<User> users = userMapper.selectByExample(userExample);
		if (users.size() == 0) {
			// 插入
			Date date = new Date();
			user.setGmtCreate(date);
			user.setGmtModified(date);
			userMapper.insert(user);
		} else {
			// 更新
			User dbUser = users.get(0);
			User updateUser = new User();
			updateUser.setGmtModified(new Date());
			updateUser.setAvatarUrl(user.getAvatarUrl());
			updateUser.setName(user.getName());
			updateUser.setToken(user.getToken());
			updateUser.setBio(user.getBio());
			UserExample example = new UserExample();
			example.createCriteria().andIdEqualTo(dbUser.getId());
			userMapper.updateByExampleSelective(updateUser, example);
		}
	}

}
