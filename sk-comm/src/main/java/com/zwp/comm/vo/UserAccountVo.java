package com.zwp.comm.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @program: seckiller
 * @description: 保存用户登录信息的对象
 * @author: zwp-flyz
 * @create: 2019-06-20 17:38
 * @version: v1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountVo implements Serializable {

    private static final long serialVersionUID = -8071355785654822432L;
    private Long userId;//用户id
    private String username;
    private String password;
    private String salt;
    private String role;// 角色，多角色用逗号隔开
    private Integer status;// 当前账号状态
    private String regTime;
    private String lastLogTime;
    private Integer logCot;

}
