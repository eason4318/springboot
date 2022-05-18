package com.csh.springboot.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csh.springboot.common.Constants;
import com.csh.springboot.common.Result;
import com.csh.springboot.controller.dto.UserDTO;
import com.csh.springboot.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.csh.springboot.service.IUserService;
import com.csh.springboot.entity.User;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 洪哥哥
 * @since 2022-04-03
 */
@RestController
@RequestMapping("/user")
public class UserController {



            @Resource
            private IUserService userService;

            // 新增或者更新
            @PostMapping
            public Result save(@RequestBody User user) {
                    return Result.success(userService.saveOrUpdate(user));
                    }

            @PostMapping("/register")
            public Result register(@RequestBody UserDTO userDTO) {
                String username = userDTO.getUsername();
                String password = userDTO.getPassword();
                if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
                    return Result.error(Constants.CODE_400,"参数错误");
                }
                return Result.success(userService.register(userDTO));
            }


            @PostMapping("/login")
                    public Result login(@RequestBody UserDTO userDTO) {
                        String username = userDTO.getUsername();
                        String password = userDTO.getPassword();
                        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
                            return Result.error(Constants.CODE_400,"参数错误");
                        }
                UserDTO dto = userService.login(userDTO);

                return Result.success(dto);
                    }

            @DeleteMapping("/{id}")
            public Result delete(@PathVariable Integer id) {
                    return Result.success(userService.removeById(id));
                    }



            @PostMapping("/del/batch")
            public Result deleteBatch(@RequestBody List<Integer> ids) {
                    return Result.success(userService.removeByIds(ids));
                    }

            @GetMapping
            public Result findAll() {
                    return Result.success(userService.list());
                    }

            @GetMapping("/username/{username}")
            public Result findOne(@PathVariable String username) {
                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("username",username);
                return Result.success(userService.getOne(queryWrapper));
            }


            @GetMapping("/{id}")
            public Result findOne(@PathVariable Integer id) {
                    return Result.success(userService.getById(id));
                    }

            @GetMapping("/page")
            public Result findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(defaultValue = "") String username,
                                        @RequestParam(defaultValue = "") String email,
                                        @RequestParam(defaultValue = "") String address) {
            IPage<User> page = new Page<>(pageNum, pageSize);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            if (!"".equals(username)) {
                queryWrapper.like("username", username);
            }
            if (!"".equals(email)) {
                queryWrapper.like("email", email);
            }
            if (!"".equals(address)) {
                queryWrapper.like("address", address);
            }
            return Result.success(userService.page(page, queryWrapper));
    }

}
