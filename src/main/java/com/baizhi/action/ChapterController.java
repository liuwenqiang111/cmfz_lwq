package com.baizhi.action;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.HashMap;

@RestController
@RequestMapping("chapter")
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    @RequestMapping("queryByPage")
    public HashMap<String, Object> queryByPage(String albumId,Integer page,Integer rows){

        HashMap<String, Object> map = chapterService.queryByPage(albumId, page, rows);

        return map;
    }

    @RequestMapping("edit")
    public String edit(Chapter chapter, String oper, String albumId){

        String uid=null;
        if(oper.equals("add")){
            System.out.println("添加音频");
            chapter.setAlbum_id(albumId);
            System.out.println("===controller==chapter=== "+chapter);
            uid = chapterService.save(chapter);
        }

        if(oper.equals("del")){
            chapterService.delete(chapter.getId());
            System.out.println("删除音频");
        }
        return uid;
    }
    @RequestMapping("uploadChpater")
    public HashMap<String,Object> uploadChpater(MultipartFile url, String id, HttpServletRequest request){

        System.out.println("=+++id  "+id);
        HashMap<String, Object> map = chapterService.uploadChapter(url, id, request);

        return map;
    }

    //文件下载 downloadChapter
    @RequestMapping("downloadChapter")
    public void downloadChapter(String fileName, HttpServletRequest request, HttpServletResponse response){

        //获取文件所在路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/music");

        //根据路径读取文件
        try {
            FileInputStream inputStream = new FileInputStream(new File(realPath, fileName));

            //设置文件响应格式   响应头   attachment:以附件的形式下载，   inline:在线打开
            response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(fileName,"utf-8"));

            ServletOutputStream outputStream = response.getOutputStream();
            //文件下载
            IOUtils.copy(inputStream,outputStream);

            //关闭资源
            inputStream.close();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}