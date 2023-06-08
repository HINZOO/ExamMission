package com.example.demo.dto;

import lombok.Data;

@Data
public class PageDto {
    private int pageNum=1;
    private int pageSize=5;
    private String order="e_id DESC";

}
