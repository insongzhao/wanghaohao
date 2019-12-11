package cn.insozhao.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "登录实体")
public class LoginVo {
    @ApiModelProperty(value = "见User")
    private User user;
    @ApiModelProperty(value = "登录时间",example = "2019-12-11 12:20:01")
    private String logintime;
    @ApiModelProperty(value = "登录ip",example = "192.168.101.201")
    private String loginip;
    @ApiModelProperty(value = "用户本地token")
    private String token;
}
