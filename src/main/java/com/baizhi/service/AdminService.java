package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface AdminService {

  /*  public Admin Login1(Admin admin);*/

     public HashMap<String,Object> login1(String enCode, Admin admin, HttpServletRequest request);

}
