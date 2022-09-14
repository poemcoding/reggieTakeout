package com.rui.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.reggie.common.CustomException;
import com.rui.reggie.entity.Category;
import com.rui.reggie.entity.Dish;
import com.rui.reggie.entity.Setmeal;
import com.rui.reggie.mapper.CategoryMapper;
import com.rui.reggie.service.CategoryService;
import com.rui.reggie.service.DishService;
import com.rui.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除分类 删前要判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件 根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(dishLambdaQueryWrapper);
        //查询是否关联菜品 是则抛出异常
        if (count>0){
            //已关联 抛业务异常
            throw new CustomException("当前分类关联菜品 不能删除");
        }
        //查询是否关联套餐 是则抛出异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件 根据分类id进行查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2>0){
            throw new CustomException("当前分类关联套餐 不能删除");
        }
        //正常删除分类
        super.removeById(id);
    }
}
