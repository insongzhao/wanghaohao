package cn.insozhao.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@ApiModel(description = "返回结果数据")
public class ResultVo<T> {
    @ApiModelProperty(value = "返回状态码")
    private int code;
    @ApiModelProperty(value = "额外提示信息")
    private String message;
    @ApiModelProperty(value = "返回的数据")
    private T data;
}
