package cn.insozhao.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "用户实体")
public class User implements Serializable {
    @ApiModelProperty(value = "用户名",example = "张三")
    private String username;
    @ApiModelProperty(value = "密码",example = "xaklshgkhahgehaskehtgasghkge")
    private String password;
}
