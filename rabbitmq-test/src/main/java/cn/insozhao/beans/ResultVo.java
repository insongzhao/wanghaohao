package cn.insozhao.beans;

import lombok.Data;

@Data
public class ResultVo<T> {
    private int code;
    private String message;
    private T data;
}
