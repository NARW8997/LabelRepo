package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.Label;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LabelMapper extends BaseMapper<Label> {
    @Select("select label.* from user_label, label where user_id = #{userId} and label.id = user_label.label_id")
    List<Label> selectLabelsByUserId(@Param("userId") int userId);
}
