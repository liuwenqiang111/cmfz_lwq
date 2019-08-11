package com.baizhi.dao;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDao {
    public List<Album> selectAllAlbums(@Param("page") Integer page, @Param("rows")Integer rows);

    public Integer totalcount();

    public void save(Album album);

    public void update(Album album);

    public void delete(String id);

    public void updateAll(Album album);

}
