package com.zwp.service.goods;

import com.zwp.comm.vo.SkGoodsVo;
import com.zwp.service.seckill.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @program: seckiller
 * @description: 将秒杀货物的数量定时加入到cache和从cache中清除
 * @author: zwp-flyz
 * @create: 2019-07-01 08:36
 * @version: v1.0
 **/
@Service
@ConfigurationProperties("sk.goods.cache")
public class SkGoodsCacheService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SkGoodsCacheService.class);
    // 时间格式化器，线程安全
    private final static DateTimeFormatter fmt = DateTimeFormatter.
            ofPattern("yyyy/MM/dd HH:mm:ss");

    // 秒杀开始前若干时间开始添加秒杀货物计数
    private int beforeStartDelta = 10;

    private int afterEndDelta = 10;
    // 时间任务调度器
    private  ScheduledExecutorService poolExecutor = null;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    @Qualifier("overFlag")
    Map<Long,Boolean> overMap;

    @PostConstruct
    private void init(){
        poolExecutor = Executors.newSingleThreadScheduledExecutor();
    }

    @PreDestroy
    private void destroy(){
        if(poolExecutor!=null&&!poolExecutor.isShutdown()){
            poolExecutor.shutdown();
        }
    }

    /**
     * 创建添加和删除货物计数的任务
     * @param goods
     * @return
     */
    public boolean createSkGoodsStockTask(final SkGoodsVo goods){
        try{
            Runnable addTask = ()->seckillService.addGoodsCotToCache(goods);
            Runnable deleteTask = ()->{
                seckillService.deleteGoodsCotToCache(goods);
                overMap.remove(goods.getGoodsId());
            };
            LocalDateTime startTime = LocalDateTime.parse(goods.getStartTime(),fmt);
            LocalDateTime endTime = LocalDateTime.parse(goods.getEndTime(),fmt);
            LocalDateTime now =LocalDateTime.now();

            long delay = startTime.toEpochSecond(ZoneOffset.UTC)
                    -now.toEpochSecond(ZoneOffset.UTC) - beforeStartDelta;
            if(delay<0) return false;//为满足添加要求
            poolExecutor.schedule(addTask,delay, TimeUnit.SECONDS);
            LOGGER.info("Add StockCot Task Created!Goods:{} delay:{}",goods,delay);
            delay = endTime.toEpochSecond(ZoneOffset.UTC) - now.toEpochSecond(ZoneOffset.UTC)+afterEndDelta;
            poolExecutor.schedule(deleteTask,delay,TimeUnit.SECONDS);
            LOGGER.info("Delete StockCot Task Created!delay:{}",delay);
            return true;
        }catch (Exception e){
            LOGGER.error("add skgoods to cache error! goods:{} error:{}",goods,e.getMessage());
            return false;
        }
    }
}
