package com.baizhi.service;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Banner> showAllBanners(Integer page, Integer rows) {
        List<Banner> banners = bannerDao.showAllBanners(page, rows);
        return banners;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer totalcount() {
        Integer totalcount = bannerDao.totalcount();
        return totalcount;
    }

    @Override
    public String save(Banner banner) {
        String uuid = UUIDUtil.getUUID();
        banner.setId(uuid);
        banner.setStatus("1");
        banner.setUp_date(new Date());
        bannerDao.save(banner);
        System.out.println("service添加轮播图："+banner);
        return uuid;
    }

    @Override
    public void update(Banner banner) {
        bannerDao.update(banner);
    }

    @Override
    public HashMap<String, Object> updateStatus(Banner banner) {

        HashMap<String, Object> map=new HashMap<String, Object>();
        try {
            bannerDao.updateAll(banner);

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
    public void updateAll(Banner banner) {
        String uuid = UUIDUtil.getUUID();
        bannerDao.updateAll(banner);

    }

    @Override
    public void delete(String id) {
        bannerDao.delete(id);
    }


}
