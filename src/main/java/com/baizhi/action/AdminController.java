package com.baizhi.action;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.HashMap;

@Controller
@RequestMapping("/admin")

public class AdminController {
    @Autowired
    private AdminService adminService;
  /* @RequestMapping("/login")
    public String Login1(Admin admin, HttpSession session, String enCode,String message) {
        Admin admin1 = adminService.Login1(admin);
        System.out.println(admin1);
        String code1 = (String) session.getAttribute("code");
       try {
           if (!code1.equals(enCode))  throw new RuntimeException("验证码错误");
           if (admin1 == null) throw new RuntimeException("该用户还不是管理员");
           if (!admin1.getPassword().equals(admin.getPassword())) throw new RuntimeException("密码错误");
           session.setAttribute("loginAdmin", admin1);

           return "redirect:/main/main.jsp";

       }catch (Exception e){
        message = e.getMessage();
           session.setAttribute("message",message);
        return "redirect:/login/login.jsp";
       }

    }*/

       @RequestMapping("/login")
       @ResponseBody
       public HashMap<String, Object> login (String enCode, Admin admin, HttpServletRequest request)
       {
           HashMap<String, Object> map = adminService.login1(enCode, admin, request);
           return map;
       }



    @RequestMapping("/code")
    public void code(HttpServletResponse response, HttpSession session) throws Exception {
        String code = ImageCodeUtil.getSecurityCode();
        session.setAttribute("code", code);
        BufferedImage image = ImageCodeUtil.createImage(code);

        ServletOutputStream stream = response.getOutputStream();
        ImageIO.write(image, "png", stream);

    }
    @RequestMapping("/loginout")
    public String loginout(HttpSession session){
        session.removeAttribute("loginAdmin");
        return "redirect:/login/login.jsp";
    }
}


