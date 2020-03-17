package com.alpha.community.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.alpha.community.dto.QuestionDTO;
import com.alpha.community.model.Question;

@Mapper
public interface QuestionMapper {

	@Insert(" insert into question (id, creator, title,  tag, gmt_create, gmt_modified, description) values (#{id,jdbcType=INTEGER}, #{creator,jdbcType=INTEGER}, #{title,jdbcType=VARCHAR},  #{tag,jdbcType=VARCHAR},now(), now(), #{description,jdbcType=LONGVARCHAR})")
	void insert(Question question);
	@Select("select * from question")
	List<Question> list();
	@Select("select * from question where creator = #{creator}")
	List<Question> listByCreator(Integer creator);
}
