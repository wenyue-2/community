package com.example.community.dto;

import com.example.community.model.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageDTO{
    private List<QuestionDTO> questionDTOList;
    private boolean hasPrevious;
    private boolean hasFirstPage;
    private boolean hasNext;
    private boolean hasEndPage;
    private Integer totalPages;
    private Integer currentPage;
    private List<Integer> pages;

    public void setPage(Integer totalCount, Integer currentPage, Integer size) {
        this.pages = new ArrayList<>();
        this.currentPage = currentPage;
       if(totalCount % size == 0){
           this.totalPages = totalCount / size;
       }else{
           this.totalPages = totalCount / size + 1;
       }
       if(currentPage > totalPages) currentPage = totalPages;
       pages.add(currentPage);
       for(int i = 1;i <= 3;i++){
           if(currentPage-i>0){
               pages.add(0,currentPage-i);
           }
           if(currentPage+i <= totalPages){
               pages.add(currentPage+i);
           }
       }

       //是否展示上一页
       if(currentPage == 1){
           hasPrevious = false;
       }else {
           hasPrevious = true;
       }
       //是否展示下一页
       if(currentPage == totalPages){
           hasNext = false;
       }else{
           hasNext = true;
       }
       //是否展示第一页
       if(pages.contains(1)){
           hasFirstPage = false;
       }else{
           hasFirstPage = true;
       }
       //是否展示最后一页
       if(pages.contains(totalPages)){
           hasEndPage = false;
       }else{
           hasEndPage = true;
       }

    }
}
