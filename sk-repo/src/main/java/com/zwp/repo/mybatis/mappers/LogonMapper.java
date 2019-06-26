package com.zwp.repo.mybatis.mappers;

import com.zwp.comm.vo.UserAccountVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: seckiller
 * @description: 注册时Mapper接口
 * @author: zwp-flyz
 * @create: 2019-06-22 12:27
 * @version: v1.0
 **/
@Mapper
public interface LogonMapper {

    /**
     * 存储注册的用户信息
     * @param user
     * @return 若插入成功返回1，若出现重复数据返回0，
     *          若出现其他错误则报DataAccess异常
     */
    Integer saveUserAccount(UserAccountVo user);

}
