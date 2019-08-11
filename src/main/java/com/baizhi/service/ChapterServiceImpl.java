package com.baizhi.service;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.util.UUIDUtil;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public HashMap<String, Object> queryByPage(String albumId, Integer page, Integer rows) {
        List<Chapter> chapters = chapterDao.selectAllChapters(albumId, (page-1)*rows, rows);
        Integer count = chapterDao.queryByTotal(albumId);
        HashMap<String, Object> maps = new HashMap<String, Object>();
        Integer total = count % rows ==0?count/rows:count/rows+1;

        maps.put("total",total);
        maps.put("records",count);
        maps.put("page",page);
        maps.put("rows",chapters);
        return maps;

    }

    @Override
    public String save(Chapter chapter) {
        String uuid = UUIDUtil.getUUID();
        chapter.setId(uuid);
        chapter.setUp_date(new Date());

        System.out.println("===service=添加音频信息=chapter"+chapter);
        chapterDao.save(chapter);

        return uuid;
    }

    @Override
    public String upadte(Chapter chapter) {
        chapterDao.update(chapter);
        return null;
    }

    @Override
    public HashMap<String, Object> updateAll(Chapter chapter) {
        chapterDao.updateAll(chapter);

        return null;
    }

    @Override
    public HashMap<String, Object> uploadChapter(MultipartFile url, String id, HttpServletRequest request) {
        HashMap<String, Object> maps = new HashMap<String, Object>();
        String realPath = request.getSession().getServletContext().getRealPath("/upload/music");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String filename = url.getOriginalFilename();
        String name = new Date().getTime() + "-" + filename;
        try {
            url.transferTo(new File(realPath,name));
            long size = url.getSize();
            String sizes = size/1024/1024 + "MB";
            DecimalFormat format = new DecimalFormat("0.00");
            String s = String.valueOf(size);
            double dd = Double.valueOf(s) / 1024 / 1024;
            String newsizess = format.format(dd) + "MB";
            AudioFileIO fileIO = new AudioFileIO();
            AudioFile audio = fileIO.readFile(new File(realPath, name));
            AudioHeader audioHeader = audio.getAudioHeader();
            //获取时长    分钟
            int length = audioHeader.getTrackLength();
            System.out.println("=时长==length "+length);
            String duration=length/60+"分"+length%60+"秒";

            Chapter chapter = new Chapter();
            chapter.setId(id);
            chapter.setUrl(name);
            chapter.setSize(newsizess);
            chapter.setDuration(duration);

            System.out.println("===service=修改音频信息=chapter"+chapter);
            chapterDao.updateAll(chapter);

            maps.put("success","200");
            maps.put("message","上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            maps.put("success","400");
            maps.put("message","上传失败");
        }

        return maps;
    }

    @Override
    public void delete(String id) {

        chapterDao.delete(id);
    }
}
