package com.eshop.common.advice;

import com.eshop.common.enums.ExceptionEnum;
import com.eshop.common.exception.EShopException;
import com.eshop.common.vo.ExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(EShopException.class)
    public ResponseEntity<ExceptionResult> handleException(EShopException e) {
       return ResponseEntity.status(e.getExceptionEnum().getCode())
               .body(new ExceptionResult(e.getExceptionEnum()));
    }
}
