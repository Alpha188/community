package com.alpha.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.alpha.community.model.Question;

@Mapper
public interface questionMapper {

	@Insert(" insert into question (id, creator, title, comment_count, like_count, view_count, tag, gmt_create, gmt_modified, description) values (#{id,jdbcType=INTEGER}, #{creator,jdbcType=INTEGER}, #{title,jdbcType=VARCHAR}, #{commentCount,jdbcType=INTEGER}, #{likeCount,jdbcType=INTEGER}, #{viewCount,jdbcType=INTEGER}, #{tag,jdbcType=VARCHAR},now(), now(), #{description,jdbcType=LONGVARCHAR})")
	void insert(Question question);
}
