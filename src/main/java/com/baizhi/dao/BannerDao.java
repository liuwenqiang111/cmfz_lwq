package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerDao {
    public List<Banner> showAllBanners(@Param("page") Integer page,@Param("rows")Integer rows);

    public Integer totalcount();

    public void save(Banner banner);

    public void update(Banner banner);

    public void updateAll(Banner banner);

    public void delete(String id);



}
