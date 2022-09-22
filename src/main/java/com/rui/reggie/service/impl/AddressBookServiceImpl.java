package com.rui.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.reggie.entity.AddressBook;
import com.rui.reggie.mapper.AddressBookMapper;
import com.rui.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper,AddressBook> implements AddressBookService {
}
