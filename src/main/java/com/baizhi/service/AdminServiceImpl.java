package com.baizhi.service;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Service
@Transactional


public class AdminServiceImpl implements AdminService {
   @Autowired AdminDao adminDao;

  /*@Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly =true )
    public Admin Login1(Admin admin) {
        Admin a = adminDao.login(admin.getUsername());
        return a;

    }*/

   @Override
    public HashMap<String, Object> login1(String enCode, Admin admin, HttpServletRequest request) {
        HttpSession session = request.getSession();
       String code = (String)session.getAttribute("code");
        HashMap<String, Object> maps = new HashMap<String, Object>();
       Admin a = adminDao.login(admin.getUsername());
        if(enCode.equals(code)){
            if(admin.getUsername().equals(a.getUsername())){
                if(admin.getPassword().equals(a.getPassword())){
                    session.setAttribute("loginAdmin", admin);
                    maps.put("success", "200");
                    maps.put("message","登陆成功");
                }else {
                    maps.put("success", "400");
                    maps.put("message","密码错误");
                }
            }else {
                maps.put("success", "400");
                maps.put("message","用户不存在");
            }
        }else {
            maps.put("success", "400");
            maps.put("message","验证码错误");
        }

        return maps;
    }


}

