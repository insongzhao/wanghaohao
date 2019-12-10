package cn.insozhao.dao;

import cn.insozhao.beans.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int login(User user);
}
