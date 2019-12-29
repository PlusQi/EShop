package com.eshop.item.controller;

import com.eshop.common.vo.PageResult;
import com.eshop.item.pojo.Sku;
import com.eshop.item.pojo.Spu;
import com.eshop.item.pojo.SpuDetail;
import com.eshop.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.SpinnerUI;
import java.util.List;

@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /*
    * 分页查询spu
    * */
    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<Spu>> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable", required = false)  Boolean saleable,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ) {
            return ResponseEntity.ok(goodsService.querySpuByPage(page, rows, saleable, key));
    }

    /*
    * 商品新增
    * */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody Spu spu) {
        goodsService.saveGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /*
    * 根据spu的id查询detail
    * */
    @GetMapping("/spu/detail/{id}")
    public ResponseEntity<SpuDetail> queryDetailById(@PathVariable("id") Long spuId) {
        return ResponseEntity.ok(goodsService.queryDetailById(spuId));
    }

    /*
    * 根据spu查询下面的所有sku
    * */
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long spuId) {
        return ResponseEntity.ok(goodsService.querySkuById(spuId));
    }
}
