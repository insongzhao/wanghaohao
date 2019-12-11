package cn.insozhao.controller;

import cn.insozhao.beans.LoginVo;
import cn.insozhao.beans.ResultVo;
import cn.insozhao.beans.User;
import cn.insozhao.service.UserService;
import cn.insozhao.util.RedisUtil;
import cn.insozhao.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("api/user")
@Api(tags = "用户登录controller")
@Slf4j
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    RedisUtil redisUtil;

    @ApiOperation(value = "用户登录",notes = "示例数据" +
            "{\n" +
            "  \"user\": {\n" +
            "    \"username\": \"zhangsan\",\n" +
            "    \"password\": \"123\"\n" +
            "  },\n" +
            "  \"logintime\": \"2019-12-11 10:00:00\",\n" +
            "  \"loginip\": \"192.168.101.101\",\n" +
            "  \"token\": \"xxxxxxxxxxxxxxxxxxxxxxxxxxx\"\n" +
            "}" )

    @PostMapping("login")
    public ResultVo<String> login(LoginVo loginVo){
        String token = loginVo.getToken();
        ResultVo<String> resultVo = new ResultVo<>();
        User user = loginVo.getUser();
//      如果没有token，默认为用户是第一次登录或者token已经过期
        if (StringUtils.isEmpty(token)){
            boolean loginflag = userService.login(user);
            if (loginflag){
                resultVo.setCode(0);
                resultVo.setMessage("登陆成功");
                String tokenStr = TokenUtil.createJwtToken(user.getUsername(),user.getPassword());
                resultVo.setData(tokenStr);
                HashMap<String, Object> userMap = new HashMap<>();
                userMap.put(user.getUsername(), tokenStr);
                redisUtil.hmset("userLoginToken",userMap,3600*24*3);
            }else {
               resultVo.setCode(3);
               resultVo.setMessage("登录失败，请检查用户名或密码");
               resultVo.setData("");
            }
        }else{
//            免密登录
            if (redisUtil.hget("userLoginToken",user.getUsername()).equals(token)){
                resultVo.setCode(1);
                resultVo.setMessage("登陆成功");
                resultVo.setData("");
            }else {
//                更新redis token
                if(userService.login(user)){
                    resultVo.setCode(0);
                    resultVo.setMessage("登陆成功");
                    String tokenStr = TokenUtil.createJwtToken(user.getUsername(),user.getPassword());
                    resultVo.setData(tokenStr);
                    HashMap<String, Object> userMap = new HashMap<>();
                    userMap.put(user.getUsername(), tokenStr);
                    redisUtil.hmset("userLoginToken",userMap,3600*24*3);
                }

            }

        }
        return resultVo;

    }
}
