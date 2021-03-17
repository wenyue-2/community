package com.example.community.mapper;

import com.example.community.model.Question;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tags) values (#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tags})")
    void create(Question question);

    @Select("select * from question limit #{offset}, #{size}")
    List<Question> list(@Param("offset")Integer offset, @Param("size") Integer size);

    @Select("select count(*) from question")
    Integer count();
}
