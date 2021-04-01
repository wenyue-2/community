package com.example.community.advice;

import com.alibaba.fastjson.JSON;
import com.example.community.Exception.CustomizeErrorCode;
import com.example.community.Exception.CustomizeException;
import com.example.community.dto.ResultDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    ModelAndView handle(HttpServletRequest request, Throwable ex, Model model,HttpServletResponse response){
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            ResultDTO resultDTO = null;
            if(ex instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException) ex);
            }else{
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }
            try {
                //使之以JSON返回
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }else{
            //错误页面跳转
            if(ex instanceof CustomizeException){
                model.addAttribute("message",ex.getMessage());
            }else{
                model.addAttribute("message",CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)
                request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
