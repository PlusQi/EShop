package com.eshop.item.mapper;

import com.eshop.common.mapper.BaseMapper;
import com.eshop.item.pojo.Stock;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface StockMapper extends BaseMapper<Stock> {
}
