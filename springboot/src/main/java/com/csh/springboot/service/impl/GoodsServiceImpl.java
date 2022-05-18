package com.csh.springboot.service.impl;

import com.csh.springboot.entity.Goods;
import com.csh.springboot.mapper.GoodsMapper;
import com.csh.springboot.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 洪哥哥
 * @since 2022-04-28
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

}
