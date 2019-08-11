package com.baizhi.dao;

import com.baizhi.entity.City;
import com.baizhi.entity.Month;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    public List<User> showAllUsers(@Param("page") Integer page, @Param("rows")Integer rows);

    public Integer totalcount();

    public void update(User user);

    public List<User> queryAll();

    public List<Month> querySexAndDate(String sex);

    public  List<City> querySexAndcity(String sex);



}
