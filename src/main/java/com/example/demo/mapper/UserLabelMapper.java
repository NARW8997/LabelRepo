package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.UserLabel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserLabelMapper extends BaseMapper<UserLabel> {
    @Delete("delete from user_label where user_id = #{uid} and " +
            "label_id = #{lid};")
    Integer deleteByUserIdAndLabelId(@Param("uid") Integer uid, @Param("lid") Integer lid);
}
