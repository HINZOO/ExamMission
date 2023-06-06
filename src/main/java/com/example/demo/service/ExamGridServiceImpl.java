package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ExamGridDto;
import com.example.demo.mapper.ExamGridMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExamGridServiceImpl implements ExamGridService{

    private ExamGridMapper examGridMapper;

    @Override
    public List<ExamGridDto> list(ExamGridDto examGridDto) {
        List<ExamGridDto>list = examGridMapper.findAll(examGridDto);
        return list;
    }

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


}
