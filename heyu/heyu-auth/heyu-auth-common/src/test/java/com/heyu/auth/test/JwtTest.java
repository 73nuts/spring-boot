package com.heyu.auth.test;

import com.heyu.common.pojo.UserInfo;
import com.heyu.common.utils.JwtUtils;
import com.heyu.common.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {

    //公钥路径
    private static final String pubKeyPath = "E:\\GraduationProject\\172021139\\tmp\\rsa\\rsa.pub";
    //私钥路径
    private static final String priKeyPath = "E:\\GraduationProject\\172021139\\tmp\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    //生成公钥和私钥
    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    //读取公钥和私钥
    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU5NjYzNzkyNn0.hzT2o0L0iPUP0i-IgRXflLDtw8jtJwBbBwAwN3hkLnX4XeBlOzq3jmGDYCQzEZ5PkQtf_O0Jlxz4VAxBaUSjjsSpYgMDaPe95uM2PvqhE_d1hvKri0cX2EwORuNbdWUXC0jwN-I-4fi31Lu5cwWtsxcAIYXIEk_vlJKRxbWWQxg";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}