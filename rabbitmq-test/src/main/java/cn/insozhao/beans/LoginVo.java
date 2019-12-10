package cn.insozhao.beans;

import lombok.Data;

import java.util.Date;
@Data
public class LoginVo {
    private String username;
    private String password;
    private Date logintime;
    private String loginip;
    private String token;
}
