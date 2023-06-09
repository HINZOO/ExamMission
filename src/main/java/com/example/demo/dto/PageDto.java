package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PageDto {
    private int pageNum=1;
    private int pageSize=5;
    
    private String order ="post_time";
    private String direct="DESC";

    @JsonProperty("eId")
    private int eId;
    @JsonProperty("uId")
    private String uId;
    private String name;
    private String nation;
    private String city;
    private String postTime;
    private String gender;
    private String toTime;
    private String fromTime;

    
    private String orderBy;
    public String getOrderBy() {//쿼리로 넘어간 값이 없는 경우 공백으로 지정.     
        return "e_id DESC";//SELECT * FORM boards ORDER BY  기본값.
    }

}
