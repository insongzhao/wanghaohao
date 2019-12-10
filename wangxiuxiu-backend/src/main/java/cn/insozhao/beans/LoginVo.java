package cn.insozhao.beans;

import lombok.Data;

@Data
public class LoginVo {
    private User user;
    private String logintime;
    private String loginip;
    private String token;
}
