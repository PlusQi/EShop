package com.eshop.item.service;

import com.eshop.common.enums.ExceptionEnum;
import com.eshop.common.exception.EShopException;
import com.eshop.item.mapper.SpecGroupMapper;
import com.eshop.item.mapper.SpecParamMapper;
import com.eshop.item.pojo.SpecGroup;
import com.eshop.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper groupMapper;

    @Autowired
    private SpecParamMapper paramMapper;

    public List<SpecGroup> queryGroupByCid(Long cid) {
        //查询条件
        SpecGroup group = new SpecGroup();
        group.setCid(cid);
        //查询
        List<SpecGroup> list = groupMapper.select(group);
        if (CollectionUtils.isEmpty(list)) {
            //没有找到
            throw new EShopException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return list;
    }

    public List<SpecParam> queryParamList(Long gid, Long cid, Boolean searching) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);
        List<SpecParam> list = paramMapper.select(param);
        if (CollectionUtils.isEmpty(list)) {
            //没有查到
            throw new EShopException(ExceptionEnum.SPEC_PARAM_NOT_FOUND);
        }
        return list;
    }
}
