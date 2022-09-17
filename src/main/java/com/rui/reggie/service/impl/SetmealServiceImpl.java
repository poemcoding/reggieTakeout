package com.rui.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.reggie.common.CustomException;
import com.rui.reggie.dto.SetmealDto;
import com.rui.reggie.entity.Setmeal;
import com.rui.reggie.entity.SetmealDish;
import com.rui.reggie.mapper.SetmealMapper;
import com.rui.reggie.service.SetmealDishService;
import com.rui.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;
    /**
     * 新增套餐同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐基本信息 setmeal insert
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        //保存套餐和菜品关联信息 操作setmeal_dish 执行insert
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除套餐 同时删除套餐与菜品的关联数据
     * @param ids
     */
    public void removeWithDish(List<Long> ids) {
        //查询套餐状态 是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);

        int count = this.count(queryWrapper);
        //如果不可删 抛出异常
        if (count>0){
            throw new CustomException("套餐正在售卖中 不能删除");
        }

        //如果可删除 先删除套餐表数据
        this.removeByIds(ids);
        //删除关系表数据
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
//        setmealDishService.re
    }
}
