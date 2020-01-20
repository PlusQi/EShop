package com.eshop.search.client;

import com.eshop.item.api.GoodsAPI;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "item-service")
public interface GoodsClient extends GoodsAPI {

}
