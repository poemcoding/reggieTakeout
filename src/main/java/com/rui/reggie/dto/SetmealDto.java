package com.rui.reggie.dto;

import com.rui.reggie.entity.Setmeal;
import com.rui.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
