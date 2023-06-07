package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.ExamGridDto;

@Controller
@RequestMapping("/testpage")
public class TestPageController {
    @GetMapping("/list.do")
    public String list(){
        return "testpage/list";
    }

}
