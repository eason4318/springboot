package com.csh.springboot.service;

import com.csh.springboot.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 洪哥哥
 * @since 2022-04-18
 */
public interface IMenuService extends IService<Menu> {
    List<Menu> findMenus(String name);
}
