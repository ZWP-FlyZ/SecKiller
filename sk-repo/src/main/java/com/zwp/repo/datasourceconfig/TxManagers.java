package com.zwp.repo.datasourceconfig;

/**
 * @program: seckiller
 * @description: 事务管理器名
 * @author: zwp-flyz
 * @create: 2019-06-23 17:03
 * @version: v1.0
 **/
public class TxManagers {
    // 事务管理器与DatasourceType对应
    public final static String READ_TX = "readTxManager";
    public final static String WRITE_TX = "writeTxManager";
    public final static String ACCOUNT_TX = "accountTxManager";
    public final static String DEFAULT_TX = "defaultTxManager";
}
