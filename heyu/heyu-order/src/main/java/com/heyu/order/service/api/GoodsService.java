package com.heyu.order.service.api;

import com.heyu.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "heyu-gateway", path = "/api/item")
public interface GoodsService extends GoodsApi {
}
