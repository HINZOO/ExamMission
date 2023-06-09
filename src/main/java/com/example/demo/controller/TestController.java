package com.example.demo.controller;

import java.util.List;
import java.util.Locale;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/testpage")
public class TestController {

	
	@GetMapping("/list.do")
	public String test(Model model) {
		
		/*
		 * messageSourceAccessor.getMessage("ERROR001", Locale.ENGLISH);
		 * messageSourceAccessor.getMessage("ERROR002", Locale.ENGLISH);
		 * messageSourceAccessor.getMessage("DEFAULT", Locale.ENGLISH);
		 * 
		 * List<String> items = new ArrayList<>();
		 * items.add(messageSourceAccessor.getMessage("ERROR001", Locale.ENGLISH));
		 * items.add(messageSourceAccessor.getMessage("ERROR002", Locale.ENGLISH));
		 * items.add(messageSourceAccessor.getMessage("DEFAULT", Locale.ENGLISH));
		 * 
		 * model.addAttribute("item",items);
		 */
		return "/testpage/list";
	}

}
