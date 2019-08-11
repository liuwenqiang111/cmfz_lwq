package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface ChapterService {

    public HashMap<String, Object> queryByPage(String albumId, Integer page, Integer rows);

    public String save(Chapter chapter);

    public String upadte(Chapter chapter);

    public HashMap<String, Object> updateAll(Chapter chapter);

    public HashMap<String,Object> uploadChapter(MultipartFile url, String id, HttpServletRequest request);

    public void delete (String id);


}
