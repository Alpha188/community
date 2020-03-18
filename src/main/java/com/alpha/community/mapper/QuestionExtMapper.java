package com.alpha.community.mapper;

import java.util.List;

import com.alpha.community.dto.QuestionDTO;

public interface QuestionExtMapper {

	List<QuestionDTO> listWithUser();

	List<QuestionDTO> listWithUserByCreator(Integer creator);

}
