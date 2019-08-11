package com.baizhi.cmfz_lwq;

import com.baizhi.CmfzLwqApplication;
import com.baizhi.dao.BannerDao;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.Banner;
import com.baizhi.util.UUIDUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzLwqApplication.class)
public class Test1 {
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private UserDao userDao;
    @Test
    public  void test2(){
        Banner banner = new Banner();
        banner.setId(UUIDUtil.getUUID());
        banner.setTitle("grtsd");
        banner.setImg_path("6.png");
        banner.setStatus("0");
        banner.setDescription("bhrtfg");
        banner.setUp_date(new Date());
        bannerDao.save(banner);
    }


}
