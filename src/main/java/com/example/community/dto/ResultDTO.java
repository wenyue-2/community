package com.example.community.dto;


import com.example.community.Exception.CustomizeErrorCode;
import com.example.community.Exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO<T>{
    private Integer code;
    private String message;
    public T data;

    public static ResultDTO resultOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(message);
        resultDTO.setCode(code);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode noLogin) {
        return resultOf(noLogin.getCode(),noLogin.getMessage());
    }

    public static ResultDTO okOf(){
        return resultOf(200,"请求成功");
    }

    public static <T> ResultDTO okOf(T t){

        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage("请求成功");
        resultDTO.setCode(200);
        resultDTO.setData(t);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeException ex) {
        return resultOf(ex.getCode(),ex.getMessage());
    }
}
