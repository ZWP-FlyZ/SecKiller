package com.zwp.repo;

import com.zwp.comm.vo.SkOrderVo;
import com.zwp.repo.datasourceconfig.DataSourceContextHolder;
import com.zwp.repo.datasourceconfig.DataSourceType;
import com.zwp.repo.mybatis.mappers.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: seckiller
 * @description: 订单数据库测试
 * @author: zwp-flyz
 * @create: 2019-06-29 20:08
 * @version: v1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {

    @Autowired
    OrderMapper orderMapper;

    @Test
    public void selectOder(){
        DataSourceContextHolder.setDataSource(DataSourceType.WRITE_DATASOURCE);
        SkOrderVo order = orderMapper.selectOrderByOrderId(1L);
        if(order!=null) System.err.println(order.toString());
    }

//    @Test
    public void insertOrder(){
        DataSourceContextHolder.setDataSource(DataSourceType.WRITE_DATASOURCE);
        SkOrderVo orderVo = new SkOrderVo();
        orderVo.setUserId(2L);
        orderVo.setGoodsId(3L);
        int res =orderMapper.insertSkOrder(orderVo);
        System.err.println(res);
        System.err.println(orderVo.toString());
    }

//    @Test
    public void insertOrderInfo(){
        DataSourceContextHolder.setDataSource(DataSourceType.WRITE_DATASOURCE);
        SkOrderVo orderVo = new SkOrderVo();
        orderVo.setOrderId(7L);
        orderVo.setUserId(2L);
        orderVo.setGoodsId(2L);
        int res =orderMapper.insertOrderInfo(orderVo);
        System.err.println(res);
        System.err.println(orderVo.toString());
    }




}
