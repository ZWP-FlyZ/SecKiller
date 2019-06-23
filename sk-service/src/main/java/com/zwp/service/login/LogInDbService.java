package com.zwp.service.login;

import com.zwp.comm.utils.UserIdUtils;
import com.zwp.comm.vo.UserAccountVo;
import com.zwp.repo.datasourceconfig.DataSourceContextHolder;
import com.zwp.repo.datasourceconfig.DataSourceType;
import com.zwp.repo.datasourceconfig.TxManagers;
import com.zwp.repo.datasourceconfig.UseDatasource;
import com.zwp.repo.mybatis.mappers.LoginMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @program: seckiller
 * @description: 用户登录时与数据库关联的义务逻辑
 * @author: zwp-flyz
 * @create: 2019-06-20 20:02
 * @version: v1.0
 **/
@Service
public class LogInDbService {

    private  final static Logger LOGGER = LoggerFactory.getLogger(LogInDbService.class);

    // 时间格式化器，线程安全
    private final static DateTimeFormatter fmt = DateTimeFormatter.
                                            ofPattern("yyyy/MM/dd HH:mm:ss");

    @Autowired
    LoginMapper loginMapper;

    /**
     * 登录从数据库中获取数据，若获取用户
     * @param username 用户名
     * @return UserAccountVo 当用户不存在时返回null
     */
    @UseDatasource(DataSourceType.ACCOUNT_DATASOURCE)
    public UserAccountVo getUserAccountByUsername(String username){
        Assert.notNull(username,"username is null!");
        LOGGER.debug("search user info for {}",username);
        return loginMapper.getUserAccountVoByUserId(UserIdUtils.getUserId(username));
    }

    /**
     * 登录成功后更新数据
     * @param username
     * @return
     */
    @UseDatasource(DataSourceType.ACCOUNT_DATASOURCE)
    // @Transactional(TxManagers.ACCOUNT_TX) 当需要事务时
    public int updateLogInStatus(String username){
        Assert.notNull(username,"username is null!");
        LOGGER.debug("update user info for {}",username);
        UserAccountVo vo = new UserAccountVo();
        vo.setUserId(UserIdUtils.getUserId(username));
        vo.setLastLogTime(fmt.format(LocalDateTime.now()));
        return loginMapper.updateLogInStatus(vo);
    }


}
