package com.csh.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csh.springboot.entity.Files;
import com.csh.springboot.mapper.InstrumentMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csh.springboot.common.Result;

import com.csh.springboot.service.IInstrumentService;
import com.csh.springboot.entity.Instrument;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 洪哥哥
 * @since 2022-04-23
 */
@RestController
@RequestMapping("/instrument")
public class InstrumentController {

    @Resource
    private IInstrumentService instrumentService;
    @Resource
    private InstrumentMapper instrumentMapper;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Instrument instrument) {
        instrumentService.saveOrUpdate(instrument);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        instrumentService.removeById(id);
        return Result.success();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Instrument instrument) {
        return Result.success(instrumentMapper.updateById(instrument));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        instrumentService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(instrumentService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(instrumentService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Instrument> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(instrumentService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

