package com.heyu.search.listener;


import com.heyu.search.service.SearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GoodsListener {

    @Autowired
    private SearchService searchService;

    /**
     * @Description: 处理insert和update的消息
     * @Param: [id]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/28
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "HEYU.SEARCH.SAVE.QUEUE", durable = "true"),
            exchange = @Exchange(value = "HEYU.ITEM.EXCHANGE", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"item.insert", "item.update"}
    ))
    public void save(Long id) throws IOException {
        if (id == null) {
            return;
        }
        this.searchService.save(id);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "HEYU.SEARCH.DELETE.QUEUE", durable = "true"),
            exchange = @Exchange(value = "HEYU.ITEM.EXCHANGE", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
    ))
    public void delete(Long id) throws IOException {
        if (id == null) {
            return;
        }
        this.searchService.delete(id);
    }
}
