package com.sise.wangzhan.generator.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.sise.wangzhan.generator.entity.User;
import com.sise.wangzhan.generator.service.UserService;
import com.sise.wangzhan.generator.service.impl.UserServiceImpl;
import com.sise.wangzhan.generator.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wangzhan
 * @since 2020-10-19
 */
@RestController
@RequestMapping("/generator/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @PostMapping("login")
    public Map<String, Object> login(@RequestBody User user){

        Map<String, Object> map = new HashMap<>();
        try {
            if ((user != null) &&
                    (!StringUtils.isEmpty(user.getUsername())) &&
                    (!StringUtils.isEmpty(user.getPassword()))){

                User loginUser = userService.verifyLogin(user);
                if (loginUser != null){
                    // 返回成功
                    map.put("code", 20000);
                    // 获取token
                    String token = JwtUtils.getJwtToken(loginUser.getId(), loginUser.getUsername());

                    map.put("token", token);
                }else {
                    // 返回失败
                    map.put("code", 20001);
                }
            }else {
                // 返回失败
                map.put("code", 99999);
            }
        }catch (Exception e){
            if (logger.isErrorEnabled()){
                logger.error("抛异常了，朋友？？？？", e);
            }
        }
        return map;
    }

    // 根据token查询并返回用户信息
    @GetMapping(value = "getInfo")
    public Map<String, Object> getInfo(HttpServletRequest request){

        Map<String, Object> map = new HashMap<>();
        // 判断token是否有效
        boolean checkToken = JwtUtils.checkToken(request);

        if (checkToken){
            map.put("code", 20000);
            map.put("name", "teacher");
            map.put("avatar", "http://m.imeitou.com/uploads/allimg/2016072417/pefzp42dp4l.gif");
            return map;
        }
        return null;
    }

    @PostMapping("saveUser")
    public String saveUser(@RequestBody User user) throws Exception {

        try {
            String saveUser = null;
            if (user != null){
                saveUser = userService.saveUser(user);
            }

            return saveUser;
        }catch (Exception e){
            e.printStackTrace();
            return ">>>>抛异常了，朋友？？？>>>>";
        }
    }

    @GetMapping("saveUserBatch")
    public String saveUserBatch() throws Exception{
        try {
            List<User> userList = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                User user = new User();
                user.setId(100 + i);
                user.setUsername("wang" + i);
                user.setPassword("zhan" + i);

                userList.add(user);
            }

            String saveBatchUser = null;
            if (!CollectionUtils.isEmpty(userList)){
                saveBatchUser = userService.saveBatchUser(userList);
            }

            return saveBatchUser;
        }catch (Exception e){
            e.printStackTrace();
            return ">>>>抛异常了，朋友？？？>>>>";
        }
    }
}

