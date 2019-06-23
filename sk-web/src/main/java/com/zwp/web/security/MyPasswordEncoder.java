package com.zwp.web.security;

import com.zwp.comm.utils.PassEncUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @program: seckiller
 * @description: 用于将来自前端的密码编码并和来自数据库的密码比较
 * @author: zwp-flyz
 * @create: 2019-06-23 14:12
 * @version: v1.0
 **/
public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        String tmp = rawPassword.toString();
        String[] passSalt = tmp.split(PassEncUtils.SALT_SPILTER);
        if(passSalt.length<2)
            return PassEncUtils.encodePassword(tmp,"HEI,WHO ARE YOU?");
        else // 只有满足salt结构的输入才进行加密
            return PassEncUtils.encodePassword(passSalt[0],passSalt[1]);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String[] passSalt = encodedPassword.split(PassEncUtils.SALT_SPILTER);
        if(passSalt.length!=2)
            return rawPassword.toString().equals(encodedPassword);
        else{
            String ec = PassEncUtils.encodePassword(rawPassword.toString(),passSalt[1]);
            return ec.equals(passSalt[0]);
        }
    }

}
