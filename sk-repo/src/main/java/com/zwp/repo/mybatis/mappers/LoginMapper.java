package com.zwp.repo.mybatis.mappers;

import com.zwp.comm.vo.UserAccountVo;
import org.apache.ibatis.annotations.*;

/**
 * @program: seckiller
 * @description: 登录时Mapper接口
 * @author: zwp-flyz
 * @create: 2019-06-22 12:06
 * @version: v1.0
 **/
@Mapper
public interface LoginMapper {


    /**
     * 通过userId查找用户
     * @param userId
     * @return
     */
    UserAccountVo getUserAccountVoByUserId(@Param("userId") Long userId);

    /**
     * 更新用户的登录信息
     * @param user
     * @return
     */

    Integer updateLogInStatus(UserAccountVo user);
}
