package com.zhfvkq.dyshop.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime time;
    private int status;
    private String errorMessage;

    public CustomErrorResponse(String errorMessage, int status)
    {
        this.time = LocalDateTime.now();
        this.errorMessage = errorMessage;
        this.status = status;
    }


}