package com.example.demo.controller;

import com.example.demo.dto.ExamGridDto;
import com.example.demo.dto.PageDto;
import com.example.demo.service.ExamGridService;
import com.github.pagehelper.PageInfo;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/examgrid")
public class ExamController {

    private final ExamGridService examGridService;

    public ExamController(ExamGridService examGridService) {
        this.examGridService = examGridService;
    }

    @GetMapping("/list.do")
    public String list(Model model,
                       @ModelAttribute PageDto pageDto) {
        List<ExamGridDto> examGridDtos;
        examGridDtos=examGridService.list(pageDto);
        PageInfo<ExamGridDto> pageGrids=new PageInfo<>(examGridDtos);
        model.addAttribute("exam",examGridDtos);
        model.addAttribute("page",pageGrids);
        return "examgrid/list";
    }
    @GetMapping("/list_en.do")
    public String enList(Model model,
                       @ModelAttribute PageDto pageDto) {
        List<ExamGridDto> examGridDtos;
        examGridDtos=examGridService.list(pageDto);
        PageInfo<ExamGridDto> pageGrids=new PageInfo<>(examGridDtos);
        model.addAttribute("exam",examGridDtos);
        model.addAttribute("page",pageGrids);
        return "examgrid/enlist";
    }
    


    @PostMapping("/list.do")
    public String listPost(Model model,
                       @ModelAttribute PageDto pageDto){
    	 List<ExamGridDto> examGridDtos;
         examGridDtos=examGridService.list(pageDto);
         PageInfo<ExamGridDto> pageGrids=new PageInfo<>(examGridDtos);
         model.addAttribute("exam",examGridDtos);
         model.addAttribute("page",pageGrids);
        return "examgrid/list";
    }


    @GetMapping("/{gender}/listBoard.do")
    public String listLoadGender(Model model,
                            @PathVariable String gender,
                            @ModelAttribute PageDto pageDto){
        List<ExamGridDto> examGridDtos;
        examGridDtos=examGridService.list(pageDto);
        pageDto.setGender(gender);
        model.addAttribute("exam",examGridDtos);
        return "examgrid/listBoard";
    }


    @GetMapping("/listBoard.do")
    public @ResponseBody List<ExamGridDto>listBoard(Model model,
                               @ModelAttribute PageDto pageDto){
       List<ExamGridDto>list=examGridService.list(pageDto);
        model.addAttribute("exam",list);
        System.out.println("출력문"+list);
        return list;
    }


	  @GetMapping("/{eId}/userListBoard.do") 
	  public @ResponseBody ExamGridDto userListBoard(Model model,									@PathVariable String eId, 
									@ModelAttribute ExamGridDto examGridDto){ 
		  ExamGridDto examGrid=examGridService.detail(Integer.parseInt(eId)); 
		  return examGrid; 
		  }
	  
	  
	  @GetMapping("/register.do")
    public void registerForm(){
    }

    @PostMapping("/register.do")
    public String registerAction(@ModelAttribute ExamGridDto examGridDto,
    							@RequestParam("city") List<String> citys,
                                 RedirectAttributes redirectAttributes){
    	System.out.println("등록값"+examGridDto);
        int insert;
        String cityValue="";
        for(String city:citys) {
        	cityValue+=city+",";	
        }
        if (!cityValue.isEmpty()) {
            cityValue = cityValue.substring(0, cityValue.length() - 1);
        }//마지막 쉼표 제외
        examGridDto.setCity(cityValue);
        insert=examGridService.register(examGridDto);
        return "redirect:/examgrid/list.do";
    }


    @PostMapping("/remove.do")
    public String deleteAction(
            @RequestParam("selectedIds") List<String> selectedIds)
      {
        int remove=0;
        for(String id:selectedIds){
            int eId=Integer.parseInt(id);
            remove=examGridService.remove(eId);
        }
        return "redirect:/examgrid/list.do";
    }

    @GetMapping("/excelDown.do")
    public void Excel(HttpServletResponse response,
                      @ModelAttribute PageDto pageDto)throws IOException {
        List<ExamGridDto> list=examGridService.list(pageDto);
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("examSheet");
        Row row = null;
        Cell cell = null;
        int rowNum = 1;

        // Header
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue("번호");
        cell = row.createCell(1);
        cell.setCellValue("아이디");
        cell = row.createCell(2);
        cell.setCellValue("이름");
        cell = row.createCell(3);
        cell.setCellValue("성별");
        cell = row.createCell(4);
        cell.setCellValue("국가");
        cell = row.createCell(5);
        cell.setCellValue("도시");

        // Body
        for (ExamGridDto exam:list) {
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(list.indexOf(exam));
            cell = row.createCell(1);
            cell.setCellValue(exam.getUId());
            cell = row.createCell(2);
            cell.setCellValue(exam.getName());
            cell = row.createCell(3);
            cell.setCellValue(exam.getGender());
            cell = row.createCell(4);
            cell.setCellValue(exam.getNation());
            cell = row.createCell(5);
            cell.setCellValue(exam.getCity());
        }

        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=example.xlsx");

        // Excel File Output
        wb.write(response.getOutputStream());
        wb.close();


    }

  
    static class HandlerDto{
        private int handler;
        public int getHandler() {
        	return handler;
        }
        public int setHandler(int handler) {
        	this.handler=handler;
        	return handler;
        }
        
    }

    @GetMapping("/{eId}/detail.do")
    public @ResponseBody ExamGridDto detail(
                         @PathVariable int eId){
        ExamGridDto examGridDto=examGridService.detail(eId);
        return examGridDto;
    }//모달창 정보 받아오기.

    @PutMapping("/handler.do")
    public @ResponseBody HandlerDto modify(
            @ModelAttribute ExamGridDto examGridDto
    ){
        HandlerDto handlerDto= new HandlerDto();
        int modify=0;
        modify=examGridService.modify(examGridDto);
        handlerDto.setHandler(modify);
        return handlerDto;
    }//모달창 수정.

    @PostMapping("/handler.do")
    public @ResponseBody HandlerDto register(
            @ModelAttribute ExamGridDto examGridDto
    ){
        HandlerDto handlerDto= new HandlerDto();
        int rowRegister=0;
        rowRegister+=examGridService.register(examGridDto);
        handlerDto.setHandler(rowRegister);
        return handlerDto;
    }//폼추가 등록 출력

    @GetMapping("/{uId}/checkId.do")
    public @ResponseBody int idCheck(@PathVariable String uId){
        ExamGridDto result=examGridService.idCheck(uId);
        if(result!=null) {
            return 1;
        }else{
            return 0;
        }
    }
}


