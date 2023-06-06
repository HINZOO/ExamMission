package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ExamGridDto {
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
}
