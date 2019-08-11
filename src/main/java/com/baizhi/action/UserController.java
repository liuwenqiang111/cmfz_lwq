package com.baizhi.action;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.City;
import com.baizhi.entity.Month;
import com.baizhi.entity.Pro;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("showAllUsers")
    public HashMap<String, Object> showAllUsers(Integer page, Integer rows) {

        HashMap<String, Object> map = userService.showAllUsers(page, rows);

        return map;
    }

    @RequestMapping("/updateStatus")
    public HashMap<String, Object> updateStatus(String id, String status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        HashMap<String, Object> maps = userService.updateStatus(user);
        return maps;
    }

    @RequestMapping("/export")
    public HashMap<String, Object> export() {
        HashMap<String, Object> map = new HashMap<String, Object>();

        List<User> users = userService.queryAll();
        for (User user : users) {
            String pic_img = user.getPic_img();
            user.setPic_img("D:/Users/Administrator/IdeaProjects/IDEA/cmfz_lwq/src/main/webapp/upload/photo/" + pic_img);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息", "用户"), User.class, users);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("F://TestEasyPoi.xls"));
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();
            map.put("success", "200");
            map.put("message", "导出成功");
        } catch (Exception e) {
            e.getStackTrace();
            map.put("success", "400");
            map.put("message", "导出失败");
        }
        return map;
    }

    @RequestMapping("/queryUser")
    public HashMap<String, Object> querySexAndDate(String sex) {
        List<Month> months = userService.querySexAndDate("男");
        List<String> list = new ArrayList<>();
        List<Integer> boys = new ArrayList<>();
        for (Month month : months) {
            list.add(month.getMonth());
            boys.add(month.getCount());
        }

        List<Month> months1 = userService.querySexAndDate("女");
        List<Integer> girls = new ArrayList<>();
        for (Month month : months) {
            list.add(month.getMonth());
            girls.add(month.getCount());
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("month", list);
        map.put("boys", boys);
        map.put("girls", girls);

        return map;
    }

    @RequestMapping("/queryUserChina")
    public List<Pro> queryUserChina(String sex) {
        List<City> boys = userService.querySexAndcity("男");
        List<City> girls = userService.querySexAndcity("女");


        List<Pro> pros = new ArrayList<Pro>();
        pros.add(new Pro("男性",boys));
        pros.add(new Pro("女性",girls));

        return pros;
    }

    }

