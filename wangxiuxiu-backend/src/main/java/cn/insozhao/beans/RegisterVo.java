package cn.insozhao.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "注册业务类")
public class RegisterVo {
    @ApiModelProperty(value = "用户id,时间戳（毫秒）",example = "1576568820")
    private String userid;
    private User user;
    @ApiModelProperty(value = "手机号(11位)",example = "18866669999")
    private String telephone;
    @ApiModelProperty(value = "验证码(6位数字)",example = "123456")
    private String verificode;
    @ApiModelProperty(value = "注册时间",example = "2019-12-11 14:24:03")
    private String registerTime;
    @ApiModelProperty(value = "注册ip地址",example = "192.168.10.12")
    private String registerIp;

}
