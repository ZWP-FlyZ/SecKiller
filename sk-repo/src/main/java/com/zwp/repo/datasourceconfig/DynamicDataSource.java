package com.zwp.repo.datasourceconfig;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @program: seckiller
 * @description: 动态数据源
 * @author: zwp-flyz
 * @create: 2019-06-22 12:30
 * @version: v1.0
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }
}
