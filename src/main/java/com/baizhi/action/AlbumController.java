package com.baizhi.action;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
@RequestMapping("/selectAllAlbums")
public Map<String,Object> showAllAlbums(Integer page, Integer rows)throws Exception{
    List<Album> albums = albumService.selectAllAlbums(page, rows);
    Integer totalcount = albumService.totalcount();
    Map<String, Object> maps = new HashMap<String, Object>();
    maps.put("page",page);
    maps.put("records",totalcount);
    Integer pageCount = 0;
    if(totalcount%rows==0){
        pageCount = totalcount/rows;
    }else{
        pageCount = totalcount/rows+1;
    }
    maps.put("total",pageCount);
    maps.put("rows",albums);
    return maps;
}

    @RequestMapping("/delete")
    public void delete(String id){
       albumService.delete(id);
    }
    @RequestMapping("/updateAll")
    public void updateAll(MultipartFile cover_img, String id, HttpServletRequest request){

        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String filename = cover_img.getOriginalFilename();
        String name = new Date().getTime() + "-" + filename;

        try {
            cover_img.transferTo(new File(realPath,name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Album album = new Album();
        album.setId(id);
        album.setCover_img(name);
        albumService.update(album);

        album.setTitle(album.getTitle());
        album.setAuthor(album.getAuthor());
        album.setCover_img(album.getCover_img());
        albumService.updateAll(album);

    }

    @RequestMapping("/edit")
    public String edit(Album album,String oper){
        String uid = null;
        if(oper.equals("add")){
            uid = albumService.save(album);

        }
        if(oper.equals("edit")){
            if(album.getCover_img().contains(".")){
                uid = album.getId();
                albumService.updateAll(album);
            }

            albumService.updateAll(album);

        }
        if(oper.equals("del")){
            albumService.delete(album.getId());
        }
        return uid;
    }
    @RequestMapping("/uploadAlubm")
    public void uploadAlubm(MultipartFile cover_img, String id, HttpServletRequest request){

        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String filename = cover_img.getOriginalFilename();
        String name = new Date().getTime() + "-" + filename;

        try {
            cover_img.transferTo(new File(realPath,name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Album album = new Album();
        album.setId(id);
        album.setCover_img(name);
        albumService.update(album);
    }



}
