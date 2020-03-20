package com.alpha.community.mapper;

import java.util.List;

import com.alpha.community.dto.QuestionDTO;

public interface QuestionExtMapper {

	List<QuestionDTO> listWithUser();

	List<QuestionDTO> listWithUserByCreator(Long creator);

	void incViewCount(Long id);

	void incCommentCount(Long id);

}
