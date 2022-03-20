package com.example.wendaoneversion.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DateWrap {
    private int id;
    private String title;
    private String Content;
    private Date createdDate;
    private int commentCount;
    private int userId;
    private String name;
    private String headUrl;
}
