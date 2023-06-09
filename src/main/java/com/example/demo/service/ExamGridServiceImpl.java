package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ExamGridDto;
import com.example.demo.dto.PageDto;
import com.example.demo.mapper.ExamGridMapper;
import com.github.pagehelper.PageHelper;


@Service
public class ExamGridServiceImpl implements ExamGridService {
	@Autowired
    private ExamGridMapper examGridMapper;

    @Override
    public ExamGridDto detail(int eId) {
        ExamGridDto detail=examGridMapper.findByEId(eId);
        return detail;
    }

    @Override
    public ExamGridDto idCheck(String uId) {
        ExamGridDto idCheck=examGridMapper.findByUId(uId);
        return idCheck;
    }

    @Override
    public int register(ExamGridDto examGridDto) {
        int register;
        register=examGridMapper.insertOne(examGridDto);
        return register;
    }

    @Override
    public int modify(ExamGridDto examGridDto) {
        int modify;
        modify=examGridMapper.updateOne(examGridDto);
        return modify;
    }

    @Override
    public int remove(int eId) {
        int remove;
        remove=examGridMapper.deleteOne(eId);
        return remove;
    }

	@Override
	public List<ExamGridDto> list (PageDto pageDto) {
		 PageHelper.startPage(pageDto.getPageNum(), pageDto.getPageSize(),pageDto.getOrderBy());
		 List<ExamGridDto> list=examGridMapper.findAll(pageDto);
		 return list;
	}


}
