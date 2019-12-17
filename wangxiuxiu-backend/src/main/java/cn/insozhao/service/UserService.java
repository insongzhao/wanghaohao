package cn.insozhao.service;

import cn.insozhao.beans.RegisterVo;
import cn.insozhao.beans.ResultVo;
import cn.insozhao.beans.User;

public interface UserService {
      boolean  login(User user);

      ResultVo<String> register(RegisterVo registerVo);
}
