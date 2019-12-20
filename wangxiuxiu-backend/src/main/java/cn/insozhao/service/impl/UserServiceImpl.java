package cn.insozhao.service.impl;

import cn.insozhao.beans.RegisterVo;
import cn.insozhao.beans.ResultVo;
import cn.insozhao.beans.User;
import cn.insozhao.mapper.UserMapper;
import cn.insozhao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean login(User user) {
        if (userMapper.login(user) != null && !"".equals(userMapper.login(user))) {
            log.info("登录状态true");
            return true;
        } else {
            log.info("登录状态false");
            return false;
        }
    }

    @Override
    public ResultVo<String> register(RegisterVo registerVo) {
        int result = userMapper.register(registerVo);
        ResultVo<String> resultVo = new ResultVo<>();
        if (result > 0) {
            resultVo.setCode(0);
            resultVo.setMessage("注册成功");
            resultVo.setData("");
        } else {
            resultVo.setCode(1);
            resultVo.setMessage("注册失败");
            resultVo.setData("");
        }
        return resultVo;
    }
}
