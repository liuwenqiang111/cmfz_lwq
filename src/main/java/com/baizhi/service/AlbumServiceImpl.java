package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.baizhi.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
@Transactional
public class AlbumServiceImpl implements AlbumService{
@Autowired
private AlbumDao albumDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Album> selectAllAlbums(Integer page, Integer rows) {
        List<Album> albums = albumDao.selectAllAlbums(page, rows);
        return albums;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer totalcount() {
        Integer totalcount = albumDao.totalcount();
        return totalcount;
    }

    @Override
    public String save(Album album) {
        String uuid = UUIDUtil.getUUID();
        album.setId(uuid);
        album.setPub_date(new Date());
        albumDao.save(album);
        return uuid;
    }

    @Override
    public void update(Album album) {
    albumDao.update(album);
    }

    @Override
    public String updateAll(Album album) {
        String uuid = UUIDUtil.getUUID();
        albumDao.updateAll(album);
        return uuid;
    }

    @Override
    public void delete(String id) {
        albumDao.delete(id);
    }
}