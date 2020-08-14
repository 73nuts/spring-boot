package com.heyu.goods.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*
页面静态化
 */
@Service
public class GoodsHtmlService {

    @Autowired
    private TemplateEngine engine;

    @Autowired
    private GoodsService goodsService;

    /**
     * @Description: 生成静态页面
     * @Param: [spuId]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/27
     */
    public void creatHtml(Long spuId) {

        //初始化运行上下文
        Context context = new Context();

        //设置数据模型
        context.setVariables(this.goodsService.loadData(spuId));
        PrintWriter printWriter = null;
        try {
            // 把静态文件生成到服务器本地
            File file = new File("E:\\GraduationProject\\172021139\\tools\\nginx-1.14.0\\html\\item\\" + spuId + ".html");
            printWriter = new PrintWriter(file);

            // 执行页面静态化方法
            this.engine.process("item", context, printWriter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }


    /**
     * @Description: 删除静态页面
     * @Param: [id]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/28
     */
    public void deleteHtml(Long id) {
        File file = new File("E:\\GraduationProject\\172021139\\tools\\nginx-1.14.0\\html\\item\\" + id + ".html");
        file.deleteOnExit();
    }
}
