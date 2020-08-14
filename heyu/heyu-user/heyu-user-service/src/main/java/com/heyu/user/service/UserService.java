package com.heyu.user.service;


import com.heyu.commin.utils.NumberUtils;
import com.heyu.user.mapper.UserMapper;
import com.heyu.user.pojo.User;
import com.heyu.user.utils.CodecUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "user:verify:";

    /**
     * @Description: 校验数据是否可用
     * @Param: [data, type]
     * @return: java.lang.Boolean
     * @Author: Big Brother
     * @Date: 2020/7/28
     */
    public Boolean checkUser(String data, Integer type) {
        User record = new User();
        if (type == 1) {
            record.setUsername(data);
        } else if (type == 2) {
            record.setPhone(data);
        } else {
            return null;
        }
        return this.userMapper.selectCount(record) == 0;
    }


    /**
     * @Description: 发送验证码
     * @Param: [phone]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/28
     */
    public void sendVerifyCode(String phone) {

        if (StringUtils.isBlank(phone)) {
            return;
        }

        //生成验证码
        String code = NumberUtils.generateCode(6);

        //发送消息到rabbitMQ
        Map<String, String> msg = new HashMap<>();
        msg.put("phone", phone);
        msg.put("code", code);
        this.amqpTemplate.convertAndSend("HEYU.ITEM.EXCHANGE", "verifycode.sms", msg);

        //将验证码保存到redis
        this.redisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
    }

    /**
     * @Description: 用户注册
     * @Param: [user, code]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/28
     */
    public void register(User user, String code) {

        //查询redis中的验证码
        String redisCode = this.redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());

        //校验验证码
        if (!StringUtils.equals(code, redisCode)) {
            return;
        }
        //生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);

        //加盐加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));

        //新增用户
        user.setId(null);
        user.setCreated(new Date());
        this.userMapper.insertSelective(user);
    }

    /**
     * @Description: 根据用户名和密码查询用户
     * @Param: [username, password]
     * @return: com.heyu.user.pojo.User
     * @Author: Big Brother
     * @Date: 2020/7/28
     */
    public User queryUser(String username, String password) {
        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);

        //判断user是否为空
        if (user == null) {
            return null;
        }

        //获取盐，对用户输入密码加盐加密
        password = CodecUtils.md5Hex(password, user.getSalt());


        //和数据库中的密码进行比对
        if (StringUtils.equals(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
