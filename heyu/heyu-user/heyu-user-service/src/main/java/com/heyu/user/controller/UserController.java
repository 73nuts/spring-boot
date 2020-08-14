package com.heyu.user.controller;


import com.heyu.user.pojo.User;
import com.heyu.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @Description: 校验数据是否可用
     * @Param: [data, type]
     * @return: org.springframework.http.ResponseEntity<java.lang.Boolean>
     * @Author: Big Brother
     * @Date: 2020/7/28
     */
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkUser(@PathVariable("data") String data, @PathVariable("type") Integer type) {
        Boolean bool = this.userService.checkUser(data, type);
        if (bool == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(bool);
    }

    /**
     * @Description: 发送验证码
     * @Param: [phone]
     * @return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: Big Brother
     * @Date: 2020/7/28
     */
    @PostMapping("code")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone") String phone) {
        this.userService.sendVerifyCode(phone);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @Description: 注册
     * @Param: [user, code]
     * @return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: Big Brother
     * @Date: 2020/7/28
     */
    @PostMapping("register")
    public ResponseEntity<Void> register(@Valid User user, @RequestParam("code") String code) {
        this.userService.register(user, code);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
    * @Description: 根据用户名和密码查询用户
    * @Param: [username, password]
    * @return: org.springframework.http.ResponseEntity<com.heyu.user.pojo.User>
    * @Author: Big Brother
    * @Date: 2020/7/28
    */
    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = this.userService.queryUser(username, password);

        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }
}
