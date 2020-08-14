package com.heyu.goods.listener;

import com.heyu.goods.service.GoodsHtmlService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
消息监听类
 */
@Component
public class GoodsListener {

    @Autowired
    private GoodsHtmlService goodsHtmlService;

    /**
     * 处理insert和update的消息
     *
     * @param id
     * @throws Exception
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "HEYU.ITEM.SAVE.QUEUE", durable = "true"),
            exchange = @Exchange(value = "HEYU.ITEM.EXCHANGE", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"item.insert", "item.update"}
    ))
    public void save(Long id) {
        if (id == null) {
            return;
        }
        this.goodsHtmlService.creatHtml(id);
    }


    /**
     * @Description: 处理删除消息
     * @Param: [id]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/28
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "HEYU.ITEM.DELETE.QUEUE", durable = "true"),
            exchange = @Exchange(value = "HEYU.ITEM.EXCHANGE", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
    ))
    public void delete(Long id) {
        if (id == null) {
            return;
        }
        this.goodsHtmlService.deleteHtml(id);
    }
}
