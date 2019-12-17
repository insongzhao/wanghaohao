package cn.insozhao.mapper;

import cn.insozhao.beans.RegisterVo;
import cn.insozhao.beans.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    String login(User user);

    int register(RegisterVo registerVo);
}
