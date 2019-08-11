package com.baizhi.action;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
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
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
     @RequestMapping("/selectAllBanners")

    public Map<String,Object> showAllBanners(Integer page,Integer rows)throws Exception{
         List<Banner> banners = bannerService.showAllBanners(page, rows);
         Integer totalcount = bannerService.totalcount();
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
         maps.put("rows",banners);
         return maps;
     }
     @RequestMapping("/updateStatus")
     public HashMap<String,Object> updateStatus (String id,String status){
         Banner banner = new Banner();
         banner.setId(id);
         banner.setStatus(status);
         HashMap<String, Object> maps = bannerService.updateStatus(banner);
         return maps;
     }
     @RequestMapping("/delete")
     public void delete(String id){
         bannerService.delete(id);
     }
     @RequestMapping("/updateAll")
     public void updateAll(MultipartFile img_path, String id, HttpServletRequest request){

         String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");
         File file = new File(realPath);
         if(!file.exists()){
             file.mkdirs();
         }
         String filename = img_path.getOriginalFilename();
         String name = new Date().getTime() + "-" + filename;

         try {
             img_path.transferTo(new File(realPath,name));
         } catch (IOException e) {
             e.printStackTrace();
         }
         Banner banner = new Banner();
         banner.setId(id);
         banner.setImg_path(name);
         bannerService.update(banner);

         banner.setTitle(banner.getTitle());
         banner.setDescription(banner.getDescription());
         banner.setImg_path(banner.getImg_path());
         bannerService.updateAll(banner);



     }

     @RequestMapping("/edit")
    public String edit(Banner banner,String oper){
         String uid = null;
         if(oper.equals("add")){
           uid = bannerService.save(banner);

         }
         if(oper.equals("edit")){
             if(banner.getImg_path().contains(".")){
                 uid = banner.getId();
             bannerService.updateAll(banner);
             }

             bannerService.updateAll(banner);

         }
         if(oper.equals("del")){
             bannerService.delete(banner.getId());
         }
         return uid;
     }
     @RequestMapping("/uploadBanner")
    public void uploadBanner(MultipartFile img_path, String id, HttpServletRequest request){

        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String filename = img_path.getOriginalFilename();
        String name = new Date().getTime() + "-" + filename;

        try {
            img_path.transferTo(new File(realPath,name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Banner banner = new Banner();
        banner.setId(id);
        banner.setImg_path(name);
        bannerService.update(banner);
    }


}
