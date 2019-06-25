package com.zwp.service.goods;

import com.zwp.comm.resulttype.ResultStatus;
import com.zwp.comm.vo.SkGoodsVo;
import com.zwp.repo.datasourceconfig.DataSourceType;
import com.zwp.repo.datasourceconfig.UseDatasource;
import com.zwp.repo.mybatis.mappers.GoodsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: seckiller
 * @description: 货物相关服务
 * @author: zwp-flyz
 * @create: 2019-06-24 09:42
 * @version: v1.0
 **/
@Service
public class GoodsService {


    private final static Logger LOGGER = LoggerFactory.getLogger(GoodsService.class);

    @Autowired
    GoodsMapper goodsMapper;

    /**
     * 获得货物列表
     * @return
     */
    @UseDatasource(DataSourceType.READ_DATASOURCE)// 注意更改
    public List<SkGoodsVo> getSkGoodsList(){
        return goodsMapper.selectSkGoodsList();
    }

    /**
     * 注册货物
     * @param goods
     * @return
     */
    @UseDatasource(DataSourceType.WRITE_DATASOURCE)
    @Transactional
    public ResultStatus regSkGoods(SkGoodsVo goods){
        int v = goodsMapper.insertSkGoods(goods);
        LOGGER.debug(goods.toString());
//        throw new IllegalArgumentException("test");
        v+= goodsMapper.insertSkGoodsDetail(goods);
        if(v==2) // 两表同时插入成功
            return ResultStatus.SUCCESS;
        else    // 否则报异常，事务回滚
            throw new IllegalStateException("Add goods error! Something wrong with inserting goods ito Database ");
    }

//    @UseDatasource(DataSourceType.READ_DATASOURCE)
    @UseDatasource(DataSourceType.WRITE_DATASOURCE) // 注意
    public SkGoodsVo getSkGoodsDetailByGoodsId(Long goodsId){
        return goodsMapper.selectSkGoodsDetailByGoodsId(goodsId);
    }


}
