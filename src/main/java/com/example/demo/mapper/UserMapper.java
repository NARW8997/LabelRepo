package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where username = #{username} and password = #{password};")
    User getByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Select("select * from user where username = #{username};")
    User getByUsername(String username);
}
