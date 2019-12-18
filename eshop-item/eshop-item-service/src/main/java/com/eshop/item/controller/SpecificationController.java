package com.eshop.item.controller;

import com.eshop.item.pojo.SpecGroup;
import com.eshop.item.pojo.SpecParam;
import com.eshop.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specService;

    /*
    * 根据分类id查询规格
    * */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid") Long cid) {
        return ResponseEntity.ok(specService.queryGroupByCid(cid));
    }

    /*
    * 查询参数集合
    * */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParamList(
            @RequestParam(value = "gid", required = false) Long gid, //组id
            @RequestParam(value = "cid", required = false) Long cid, //分类id
            @RequestParam(value = "searching" , required = false) Boolean searching //是否搜索
    ) {
        return ResponseEntity.ok(specService.queryParamList(gid, cid, searching));
    }
}
