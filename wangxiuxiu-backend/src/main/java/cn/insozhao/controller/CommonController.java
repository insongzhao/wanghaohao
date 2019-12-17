package cn.insozhao.controller;

import cn.insozhao.beans.ResultVo;
import cn.insozhao.util.RedisUtil;
import com.cloopen.rest.sdk.CCPRestSDK;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Random;


@Slf4j
@Api(tags = "通用接口")
@RestController
@RequestMapping("api/common")
public class CommonController {
    @Value("${yuntongxun.accountSid}")
    String accountSid;
    @Value("${yuntongxun.accountToken}")
    String accountToken;
    @Value("${yuntongxun.AppId}")
    String AppId;
    @Value("${yuntongxun.expireTime}")
    String expireTime;

    @Autowired
    RedisUtil redisUtil;

    @ApiOperation(value = "获取短信验证码")
    @RequestMapping(value = "getVerCode",method = RequestMethod.GET)
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
}
