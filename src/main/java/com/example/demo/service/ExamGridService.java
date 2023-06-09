package com.example.demo.service;

import java.util.List;


import com.example.demo.dto.ExamGridDto;
import com.example.demo.dto.PageDto;
import com.github.pagehelper.Page;

public interface ExamGridService {
    List<ExamGridDto> list(PageDto pageDto);
    ExamGridDto detail(int eId);
    ExamGridDto idCheck(String uId);
    int register(ExamGridDto examGridDto);
    int modify(ExamGridDto examGridDto);
    int remove(int eId);
}
