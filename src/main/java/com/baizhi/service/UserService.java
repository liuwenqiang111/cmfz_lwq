package com.baizhi.service;

import com.baizhi.entity.City;
import com.baizhi.entity.Month;
import com.baizhi.entity.User;

import java.util.HashMap;
import java.util.List;

public interface UserService {

    public HashMap<String, Object> showAllUsers( Integer page, Integer rows);

    public HashMap<String,Object> updateStatus(User user);

    public List<User> queryAll();

    public List<Month> querySexAndDate(String sex);

    public List<City> querySexAndcity(String sex);

}
