package com.zwp.web.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


/**
 * @program: seckiller
 * @description: 注册时用户上传数据
 * @author: zwp-flyz
 * @create: 2019-06-21 14:48
 * @version: v1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogonVo {

    @NotNull(message="username is null")
    @Length(min=6,max=25,
            message="username'length  is not in range(6,25)")
    @Pattern(regexp="^(_|[0-9a-zA-Z])+$", // 大小写字母数字和下划线
            message="Illegal username ")
    private String username;

    @NotNull(message="password is null")
    @Length(min=6,max=25,
            message="password'length is not in range(6,25)")
    private String password;

    @NotNull(message="verifyCode is null")
    private String verifyCode;

}
