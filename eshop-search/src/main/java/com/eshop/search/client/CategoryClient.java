package com.eshop.search.client;

import com.eshop.item.api.CategoryAPI;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient("item-service")
public interface CategoryClient extends CategoryAPI {

}
