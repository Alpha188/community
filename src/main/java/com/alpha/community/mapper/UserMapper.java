package com.alpha.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.alpha.community.model.User;

@Mapper
public interface UserMapper {
	@Insert("insert into user(account_id,name,bio,avatar_url,token,gmt_create,gmt_modified) value(#{accountId},#{name},#{bio},#{avatarUrl},#{token},now(),now())")
	void insert(User user);
	@Select("select * from user where token = #{token}")
	User getUserByToken(String token);
	@Select("select * from user where id=#{id}")
	User getUserById(Integer id);
}
