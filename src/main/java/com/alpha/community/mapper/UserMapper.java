package com.alpha.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.alpha.community.model.User;

@Mapper
public interface UserMapper {
	@Insert("insert into user(account_id,name,bio,token,gmt_create,gmt_modified) value(#{accountId},#{name},#{bio},#{token},now(),now())")
	void insert(User user);
	@Select("select * from user where token = #{token}")
	User getUser(String token);
}
