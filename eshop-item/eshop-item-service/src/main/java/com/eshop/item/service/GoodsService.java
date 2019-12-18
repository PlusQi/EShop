package com.eshop.item.service;

import com.eshop.common.enums.ExceptionEnum;
import com.eshop.common.exception.EShopException;
import com.eshop.common.vo.PageResult;
import com.eshop.item.mapper.SkuMapper;
import com.eshop.item.mapper.SpuDetailMapper;
import com.eshop.item.mapper.SpuMapper;
import com.eshop.item.mapper.StockMapper;
import com.eshop.item.pojo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpuDetailMapper detailMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private StockMapper stockMapper;

    public PageResult<Spu> querySpuByPage(Integer page, Integer rows, Boolean saleable, String key) {
        //分页
        PageHelper.startPage(page, rows);
        //过滤
        Example example = new Example(Spu.class);
        //搜索字段过滤
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        //上下架过滤
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }
        //默认排序
        example.setOrderByClause("last_update_time DESC");

        //查询
        List<Spu> spus = spuMapper.selectByExample(example);

        //判断
        if (CollectionUtils.isEmpty(spus)) {
            throw new EShopException(ExceptionEnum.GOODS_NOT_FOUND);
        }

        //解析分类和品牌的名称
        loadCategoryAndBranName(spus);

        //解析分页结果
        PageInfo<Spu> info = new PageInfo<>(spus);

        return new PageResult<>(info.getTotal(), spus);
    }


    private void loadCategoryAndBranName(List<Spu> spus) {
        for (Spu spu : spus) {
            //处理分类名称
            List<String> names = categoryService.queryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream().map(Category::getName).collect(Collectors.toList());
            spu.setCname(StringUtils.join(names, "/"));
            //处理品牌名称
            spu.setBname(brandService.queryById(spu.getBrandId()).getName());
        }
    }

    @Transactional
    public void saveGoods(Spu spu) {
        //新增spu
        spu.setId(null);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        spu.setValid(false);

        int count = spuMapper.insert(spu);
        if (count != 1) {
            throw new EShopException(ExceptionEnum.GOODS_SAVE_ERROR);
        }

        //新增detail
        SpuDetail detail = spu.getSpuDetail();
        detail.setSpuId(spu.getId());
        detailMapper.insert(detail);

        //定义库存集合
        List<Stock> stocksList = new ArrayList<>();

        //新增sku
        List<Sku> skus = spu.getSkus();
        for (Sku sku : skus) {
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            sku.setSpuId(spu.getId());

            count = skuMapper.insert(sku);
            if (count != 1) {
                throw new EShopException(ExceptionEnum.GOODS_SAVE_ERROR);
            }

            //新增库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());

            stocksList.add(stock);
        }

        //批量新增库存
        stockMapper.insertList(stocksList);
    }
}
