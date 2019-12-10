package cn.insozhao.Controller;

import cn.insozhao.beans.LoginVo;
import cn.insozhao.beans.ResultVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class LoginController {
    public ResultVo login(LoginVo loginVo){
        //先取token，如果token没有过时则直接放行
        return null;
    }
}
