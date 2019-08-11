package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao {
    public List<Chapter> selectAllChapters(@Param("albumId") String albumId, @Param("page") Integer page, @Param("rows") Integer rows );

    public  Integer queryByTotal(String albumId);

    public void save(Chapter chapter);

    public void update(Chapter chapter);

    public void updateAll(Chapter chapter);

    public void delete(String id);
}
