package com.csh.springboot.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csh.springboot.common.RoleEnum;
import com.csh.springboot.entity.User;
import com.csh.springboot.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csh.springboot.common.Result;

import com.csh.springboot.service.IGoodsService;
import com.csh.springboot.entity.Goods;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 洪哥哥
 * @since 2022-04-28
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private IGoodsService goodsService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Goods goods){
        if (goods.getId() == null){
            goods.setUser(TokenUtils.getCurrentUser().getUsername());
        }
        goodsService.saveOrUpdate(goods);
            return Result.success();
            }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        goodsService.removeById(id);
            return Result.success();
            }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        goodsService.removeByIds(ids);
            return Result.success();
            }

    @GetMapping
    public Result findAll(){
            return Result.success(goodsService.list());
            }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id){
            return Result.success(goodsService.getById(id));
            }

    @GetMapping("/page")
    public Result findPage(@RequestParam String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize){
            QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
            queryWrapper.orderByDesc("id");
        User currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.ROLE_USER.toString().equals(currentUser.getRole())){
            queryWrapper.eq("user",currentUser.getUsername());
        }

        return Result.success(goodsService.page(new Page<>(pageNum,pageSize),queryWrapper));
            }

            }

