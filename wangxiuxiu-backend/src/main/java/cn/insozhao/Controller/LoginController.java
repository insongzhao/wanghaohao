package cn.insozhao.Controller;

import cn.insozhao.beans.LoginVo;
import cn.insozhao.beans.ResultVo;
import cn.insozhao.service.UserService;
import cn.insozhao.token.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class LoginController {
    @Autowired
    ResultVo<String> resultVo1;
    @Autowired
    UserService userService;
    @PostMapping("login")
    public ResultVo<String> login(LoginVo loginVo){
        String token = loginVo.getToken();
        String username = loginVo.getUser().getUsername();
        String password = loginVo.getUser().getPassword();
        if (StringUtils.isEmpty(token)){
            resultVo1.setCode(1);
            resultVo1.setMessage("生成token");
            resultVo1.setData(TokenUtil.createJwtToken(username,password));
        }else{
            resultVo1.setCode(1);
            resultVo1.setMessage("解析后的token");
            resultVo1.setData(TokenUtil.parseJWT(username).toString());
        }
        return resultVo1;

    }
}
