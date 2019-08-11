package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;

public interface AlbumService {
    public List<Album> selectAllAlbums(Integer page, Integer rows);

    public Integer totalcount();

    public String save(Album album);

    public void update(Album album);

    public String updateAll(Album album);

    public void delete(String id);

}
