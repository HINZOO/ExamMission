package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PageDto {
    private int pageNum=1;
    private int pageSize=5;
    
    enum ExamsType {
       e_id,u_id,post_time,to_time,from_time,name,nation,city
    }
    
    enum DirectType{
        DESC,ASC,
    }
    private ExamsType order= ExamsType.post_time;
    private DirectType direct=DirectType.DESC;

    private ExamsType searchField;
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
    
    private String searchValue;
    private String orderBy;
    public String getOrderBy() {//쿼리로 넘어간 값이 없는 경우 공백으로 지정.
        if(this.order!=null && this.direct!=null) {
            return this.order+" "+this.direct;
        }else if (this.order!=null){
            this.direct=DirectType.ASC;
            return this.order+" "+this.direct;
        }
        return ExamsType.post_time+" " +DirectType.DESC;//SELECT * FORM boards ORDER BY  기본값.
    }

}
