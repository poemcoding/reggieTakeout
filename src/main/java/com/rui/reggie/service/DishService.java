package com.rui.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.reggie.dto.DishDto;
import com.rui.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品 同时插入对应的口味数据 需要操作两张表 dish dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和口味信息
    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}
