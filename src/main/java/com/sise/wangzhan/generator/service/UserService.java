package com.sise.wangzhan.generator.service;

import com.sise.wangzhan.generator.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangzhan
 * @since 2020-10-19
 */
public interface UserService extends IService<User> {

    // 查询数据
    User verifyLogin(User user) throws Exception;

    // 添加一条数据
    String saveUser(User user) throws Exception;

    // 批量保存
    String saveBatchUser(List<User> userList) throws Exception;

}
