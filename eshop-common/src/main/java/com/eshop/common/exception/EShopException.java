package com.eshop.common.exception;

import com.eshop.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EShopException extends RuntimeException {
    private ExceptionEnum exceptionEnum;

}
