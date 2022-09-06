package com.rui.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.reggie.entity.Employee;
import com.rui.reggie.mapper.EmployeeMapper;
import com.rui.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmloyeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
