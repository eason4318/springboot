package com.csh.springboot.service.impl;

import com.csh.springboot.entity.Instrument;
import com.csh.springboot.mapper.InstrumentMapper;
import com.csh.springboot.service.IInstrumentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 洪哥哥
 * @since 2022-04-23
 */
@Service
public class InstrumentServiceImpl extends ServiceImpl<InstrumentMapper, Instrument> implements IInstrumentService {

}
