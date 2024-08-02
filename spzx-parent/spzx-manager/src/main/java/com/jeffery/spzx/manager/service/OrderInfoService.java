package com.jeffery.spzx.manager.service;

import com.jeffery.spzx.model.dto.order.OrderStatisticsDto;
import com.jeffery.spzx.model.vo.order.OrderStatisticsVo;

public interface OrderInfoService {

    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
