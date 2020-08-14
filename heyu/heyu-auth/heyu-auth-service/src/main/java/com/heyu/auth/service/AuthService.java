package com.heyu.auth.service;

import com.heyu.auth.client.UserClient;
import com.heyu.auth.config.JwtProperties;
import com.heyu.common.pojo.UserInfo;
import com.heyu.common.utils.JwtUtils;
import com.heyu.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties jwtProperties;


    /**
     * @Description:登录授权
     * @Param: [username, password]
     * @return: java.lang.String
     * @Author: Big Brother
     * @Date: 2020/7/29
     */
    public String accredit(String username, String password) {

        //根据用户名和密码查询
        User user = this.userClient.queryUser(username, password);

        //判断user,如果查询结果为null，则直接返回null
        if (user == null) {
            return null;
        }

        try {
            //如果有查询结果，则通过jwtUtils生成jwt类型的token
            UserInfo userInfo = new UserInfo();
            userInfo.setId(user.getId());
            userInfo.setUsername(user.getUsername());
            return JwtUtils.generateToken(userInfo, this.jwtProperties.getPrivateKey(), this.jwtProperties.getExpire());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
