package com.baizhi.action;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/showAllArticles")
    public HashMap<String, Object> showAllArticles(Integer page, Integer rows) {

        HashMap<String, Object> map = articleService.showAllArticles(page, rows);

        return map;
    }

    @RequestMapping("/updateStatus")
    public HashMap<String, Object> updateStatus(String id, String status) {
        Article article = new Article();
        article.setId(id);
        article.setStatus(status);
        HashMap<String, Object> maps = articleService.update(article);
        return maps;
    }

    @RequestMapping("/edit")
    public String edit(Article article, String oper) {
        String uid = null;
        if (oper.equals("add")) {
            uid = articleService.save(article);

        }
        if (oper.equals("edit")) {
            if (article.getInsert_img().contains(".")) {
                uid = article.getId();
                articleService.update(article);
            }

            articleService.update(article);

        }
        if (oper.equals("del")) {
            articleService.delete(article.getId());
        }
        return uid;
    }
    @ResponseBody
    @RequestMapping("add")
    public HashMap<String, Object> add(Article article) {

        System.out.println(article);
        articleService.save(article);

        return null;

    }
    @ResponseBody
    @RequestMapping("update")
    public HashMap<String, Object> update(Article article, String ArticleId){
        article.setId(ArticleId);
        articleService.update(article);

        return null;
    }
    @ResponseBody
    @RequestMapping("/delete")
    public void delete(Article article){
        System.out.println(article.getId());

    }



    @RequestMapping("/uploadArticle")
    public void uploadBanner(MultipartFile insert_img, String id, HttpServletRequest request) {

        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filename = insert_img.getOriginalFilename();
        String name = new Date().getTime() + "-" + filename;

        try {
            insert_img.transferTo(new File(realPath, name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Article article = new Article();
        article.setId(id);
        article.setInsert_img(name);
        articleService.update(article);
    }
}


