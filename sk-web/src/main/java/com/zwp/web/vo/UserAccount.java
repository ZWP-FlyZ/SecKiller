package com.zwp.web.vo;

import com.zwp.comm.vo.LogInAccountVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * @program: seckiller
 * @description: SpringSecurity中表明用户认证信息的对象
 * @author: zwp-flyz
 * @create: 2019-06-20 17:55
 * @version: v1.0
 **/
public class UserAccount implements Serializable, UserDetails {


    private static final long serialVersionUID = -6666197406654734596L;
    private String username;
    private String password;
    private Integer status=0;
    private List<GrantedAuthority> roles;// 角色，以ROLE_开头
    private final static Function<String,String> cxg = f->"{noop}"+f;


    public UserAccount(String username,String password,String... roles){
        Assert.notNull(username,"the username is null");
        Assert.notNull(password,"the password is null");
        Assert.notNull(roles,"the roles is null");

        this.username = username;
        this.password = cxg.apply(password);
        this.roles= new ArrayList<>();

        Arrays.stream(roles).forEach(r->
                this.roles.add(new SimpleGrantedAuthority("ROLE_"+r)));
    }

    /**
     * 由 LogInAccountVo转成UserAccount
     * @param user
     * @return 转换后的UserAccount
     *
     */
    public static UserAccount from(LogInAccountVo user){
        UserAccount ua = new UserAccount(user.getUsername(),
                                        user.getPassword(),
                                        user.getRole().split(","));
        ua.status = user.getStatus();
        return ua;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
