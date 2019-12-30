package com.eshop.common.vo;

import com.eshop.common.enums.ExceptionEnum;
import com.eshop.common.utils.JsonUtils;
import lombok.Data;

@Data
public class ExceptionResult {
    private int status;
    private String message;
    private Long timestamp;

    public ExceptionResult(ExceptionEnum em) {
        this.status = em.getCode();
        this.message = em.getMsg();
        this.timestamp = System.currentTimeMillis();
    }
}
