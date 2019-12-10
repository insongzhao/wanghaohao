package cn.insozhao.service;

import cn.insozhao.beans.LoginVo;
import cn.insozhao.beans.ResultVo;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
      ResultVo  login(LoginVo loginVo);
}
