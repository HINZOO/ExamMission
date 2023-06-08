package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.ExamGridDto;
import com.github.pagehelper.Page;

@Mapper
public interface ExamGridMapper {
	Page<ExamGridDto> findAll(ExamGridDto examGridDto);
	ExamGridDto findByEId(int eId);
	ExamGridDto findByUId(String uId);
	int insertOne(ExamGridDto examGridDto);
	int updateOne(ExamGridDto examGridDto);
	int deleteOne(int eId);
}
