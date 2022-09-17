package com.rui.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.reggie.dto.SetmealDto;
import com.rui.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐 同时删除套餐与菜品的关联数据
     * @param ids
     */
    public void removeWithDish(List<Long> ids);

}
