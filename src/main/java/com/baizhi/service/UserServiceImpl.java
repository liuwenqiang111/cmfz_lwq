package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.City;
import com.baizhi.entity.Month;
import com.baizhi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public HashMap<String, Object> showAllUsers( Integer page, Integer rows) {
        List<User> users = userDao.showAllUsers((page-1)*rows, rows);
        Integer totalcount = userDao.totalcount();
        HashMap<String, Object> maps = new HashMap<String, Object>();
        Integer total = totalcount % rows ==0?totalcount/rows:totalcount/rows+1;

        maps.put("total",total);
        maps.put("records",totalcount);
        maps.put("page",page);
        maps.put("rows",users);
        return maps;
    }


    @Override
    public HashMap<String, Object> updateStatus(User user) {
        HashMap<String, Object> map=new HashMap<String, Object>();
        try {
            userDao.update(user);
            map.put("success","200");
            map.put("message","修改成功");
        }catch (Exception e){
            e.getStackTrace();
            map.put("success","400");
            map.put("message","修改失败");
        }
        return map;

    }

    @Override
    public List<User> queryAll() {

        List<User> users = userDao.queryAll();
        return users;
    }


    @Override
    public List<Month> querySexAndDate(String sex) {
        List<Month> months = userDao.querySexAndDate(sex);
        return months;
    }

 @Override
    public List<City> querySexAndcity(String sex) {
      List<City> citys = userDao.querySexAndcity(sex);
     return citys;
    }

}
