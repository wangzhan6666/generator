package com.sise.wangzhan.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sise.wangzhan.generator.entity.User;
import com.sise.wangzhan.generator.entity.bo.IUserBO;
import com.sise.wangzhan.generator.mapper.UserMapper;
import com.sise.wangzhan.generator.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangzhan
 * @since 2020-10-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserService userService;

    @Override
    public User verifyLogin(User user) throws Exception {
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq(IUserBO.USERNAME, user.getUsername());
            queryWrapper.eq(IUserBO.PASSWORD, user.getPassword());

            User loginUser = baseMapper.selectOne(queryWrapper);

            return loginUser;
        }catch (Exception e){
            if (logger.isErrorEnabled()){
                logger.error("抛异常了，朋友？？？", e);
            }
        }
        return null;
    }

    @Override
    public String saveUser(User user) throws Exception {

        try {
            if (logger.isInfoEnabled()){
                logger.info(">>>>入参数据为：>>>{}", user);
            }
            if (user != null){
                int insert = baseMapper.insert(user);
                // boolean save = userService.save(user);
                if (insert <= 0){
                    if (logger.isInfoEnabled()){
                        logger.info(">>>>>>添加数据失败>>>>>>{}", insert);
                    }
                    return ">>>>>>添加数据失败>>>>>>";
                }

            }else {
                if (logger.isErrorEnabled()){
                    logger.error(">>>>>>>传过来的数据为空呀，小盆友>>>>>>");
                }
                return ">>>>>>>传过来的数据为空呀，小盆友>>>>>>";
            }

        }catch (Exception e){
            if (logger.isErrorEnabled()){
                logger.error(">>>>>>>抛异常了，朋友？？？>>>>>>>>>", e);
            }
        }
        return null;
    }

    @Override
    public String saveBatchUser(List<User> userList) throws Exception {
        try {

            if (!CollectionUtils.isEmpty(userList)){
                boolean saveBatch = userService.saveBatch(userList);
                if (saveBatch){
                    return ">>>>>>批量添加数据成功>>>>>>>";
                }
            }

            return ">>>>>>>>>传过来的数据为空?????>>>>>>>>>";
        }catch (Exception e){
            e.printStackTrace();
            return ">>>>>批量保存抛异常了，朋友？？？？？>>>>>>>";
        }
    }
}
