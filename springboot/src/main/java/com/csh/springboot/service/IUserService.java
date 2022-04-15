package com.csh.springboot.service;

import com.csh.springboot.controller.dto.UserDTO;
import com.csh.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 洪哥哥
 * @since 2022-04-03
 */
public interface IUserService extends IService<User> {
    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);
}
