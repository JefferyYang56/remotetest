package com.jeffery.spzx.manager.service.impl;

import com.jeffery.spzx.manager.mapper.ProductUnitMapper;
import com.jeffery.spzx.manager.service.ProductUnitService;
import com.jeffery.spzx.model.entity.base.ProductUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductUnitServiceImpl implements ProductUnitService {

    @Autowired
    private ProductUnitMapper productUnitMapper ;

    @Override
    public List<ProductUnit> findAll() {
        return productUnitMapper.findAll();
    }
}
