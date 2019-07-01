package com.zwp.service.order;

import com.zwp.comm.vo.SkOrderVo;
import com.zwp.repo.datasourceconfig.DataSourceType;
import com.zwp.repo.datasourceconfig.UseDatasource;
import com.zwp.repo.mybatis.mappers.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @program: seckiller
 * @description: 订单相关服务
 * @author: zwp-flyz
 * @create: 2019-06-29 20:24
 * @version: v1.0
 **/
@Service
public class OrderService {
    private final static Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    private final static DateTimeFormatter fmt = DateTimeFormatter
            .ofPattern("yyyy/MM/dd HH:mm:ss");

    @Autowired
    OrderMapper orderMapper;

    /**
     * 创建订单，并返回订单ID
     * @param order
     * @return 如果创建成功则返回true，否则返回false
     */
    @UseDatasource(DataSourceType.WRITE_DATASOURCE)
    @Transactional
    public boolean createOrder(SkOrderVo order){
        LOGGER.debug("create order: {}",order);
        order.setCreateTime(fmt.format(LocalDateTime.now()));
        order.setGoodsCot(1);
        order.setOrderStatus(0);
        Integer res = orderMapper.insertSkOrder(order);
        if(res==0) return false;// 存在
        res = orderMapper.insertOrderInfo(order);
        if(res==0) return false;// 在order_info中存在
        SkOrderVo neworder = orderMapper.selectOrderByOrderId(order.getOrderId());
        order.setGoodsName(neworder.getGoodsName());
        order.setGoodsPrice(neworder.getGoodsPrice());
        return true;
    }

    /**
     * 返回订单Id
     * @param orderId
     * @return
     */
    public SkOrderVo getSkOrderByOrderId(Long orderId){
        return orderMapper.selectOrderByOrderId(orderId);
    }


    public SkOrderVo getSkOrderByUIdAndGoodsId(Long userId,Long goodsId){
        return orderMapper.selectSkOrderByUIdAndGoodsId(userId,goodsId);
    }



}
