package cn.insozhao.beans;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ResultVo<T> {
    private int code;
    private String message;
    private T data;
}
