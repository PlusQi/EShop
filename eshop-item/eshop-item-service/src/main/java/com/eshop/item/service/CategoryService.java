package com.eshop.item.service;

import com.eshop.common.enums.ExceptionEnum;
import com.eshop.common.exception.EShopException;
import com.eshop.item.mapper.CategoryMapper;
import com.eshop.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    /*
    * 根据父节点查询子节点
    * */
    public List<Category> queryCategoryListByPid(Long pid) {
        //查询条件，mapper会把对象中的非空属性作为查询条件
        Category t = new Category();
        t.setParentId(pid);
        List<Category> list = categoryMapper.select(t);
        if (CollectionUtils.isEmpty(list)) {
            throw new EShopException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return list;
    }

    public List<Category> queryByIds(List<Long> ids) {
        List<Category> list = categoryMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(list)) {
            throw new EShopException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return list;
    }
}
