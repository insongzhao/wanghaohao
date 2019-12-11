package cn.insozhao.controller;

import cn.insozhao.beans.LoginVo;
import cn.insozhao.beans.RegisterVo;
import cn.insozhao.beans.ResultVo;
import cn.insozhao.beans.User;
import cn.insozhao.service.UserService;
import cn.insozhao.util.RedisUtil;
import cn.insozhao.util.TokenUtil;
import com.cloopen.rest.sdk.CCPRestSDK;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Random;

@RestController
@RequestMapping("api/user")
@Api(tags = "用户操作接口")
@Slf4j
public class UserController {
    @Value("${yuntongxun.accountSid}")
    String accountSid;
    @Value("${yuntongxun.accountToken}")
    String accountToken;
    @Value("${yuntongxun.AppId}")
    String AppId;
    @Value("${yuntongxun.expireTime}")
    String expireTime;

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
                redisUtil.set(user.getUsername(),tokenStr,3600*24*3);
            }else {
               resultVo.setCode(3);
               resultVo.setMessage("登录失败，请检查用户名或密码");
               resultVo.setData("");
            }
        }else{
//            免密登录
            if (redisUtil.get(user.getUsername()).equals(token)){
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
                    redisUtil.set(user.getUsername(),tokenStr,3600*24*3);
                }

            }

        }
        return resultVo;

    }

    @ApiOperation(value = "获取短信验证码")
    @GetMapping("getVerCode")
    public ResultVo<String> getVerifiCode(String telephone){
        ResultVo<String> resultVo = new ResultVo<>();
        if (redisUtil.get("verifycode"+telephone)!=null){
            resultVo.setCode(0);
            resultVo.setMessage("请勿重复发送");
        }else if (redisUtil.get("verifycodenumber"+telephone)!=null && (int)redisUtil.get("verifycodenumber"+telephone)>3){
            resultVo.setCode(0);
            resultVo.setMessage("只有三次注册机会哦");
        }else{

            CCPRestSDK restSDK = new CCPRestSDK();
            restSDK.init("app.cloopen.com","8883");
            restSDK.setAccount(accountSid,accountToken);
            restSDK.setAppId(AppId);
            String verifyCode = String
                    .valueOf(new Random().nextInt(899999) + 100000);
            HashMap<String, Object> result = restSDK.sendTemplateSMS(telephone, "1", new String[]{verifyCode, expireTime});
            if("000000".equals(result.get("statusCode"))){
                redisUtil.set("verifycode"+telephone,verifyCode,60*Integer.parseInt(expireTime));
                log.info("发送验证码成功===========>"+verifyCode);
                resultVo.setCode(1);
                resultVo.setMessage("发送验证码成功，请注意查收");
                if (redisUtil.get("verifycodenumber"+telephone)!=null){
                    redisUtil.incr("verifycodenumber"+telephone,1);

                }else{
                    redisUtil.set("verifycodenumber"+telephone,1,60*60*24);
                }
            }else{
                //异常返回输出错误码和错误信息
                resultVo.setCode(3);
                resultVo.setMessage("系统错误");
                log.info("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
            }
        }
        return resultVo;


    }

    @ApiOperation(value = "用户注册接口")
    @ResponseBody
    @PostMapping("register")
    public ResultVo<RegisterVo> register(RegisterVo registerVo){
        ResultVo<RegisterVo> resultVo = new ResultVo<>();
        resultVo.setCode(0);
        resultVo.setMessage("注册成功");
        resultVo.setData(registerVo);
        return resultVo;
    }
}
