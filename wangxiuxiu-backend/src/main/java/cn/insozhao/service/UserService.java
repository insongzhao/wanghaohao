package cn.insozhao.service;

import cn.insozhao.beans.ResultVo;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
      ResultVo  login(String username,String password);
}
