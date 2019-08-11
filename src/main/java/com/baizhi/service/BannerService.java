package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.HashMap;
import java.util.List;

public interface BannerService {

    public List<Banner> showAllBanners(Integer page,Integer rows);

    public Integer totalcount();

    public String save(Banner banner);

    public void update(Banner banner);

    public HashMap<String,Object> updateStatus(Banner banner);

    public void updateAll(Banner banner);

    public void delete(String id);

}
